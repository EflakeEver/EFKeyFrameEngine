package com.eflake.keyanimengine.opengl;


import android.util.Log;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

public class ShaderHelper {
    private static final String TAG = ShaderHelper.class.getSimpleName();

    /*
    * 编译顶点着色器代码
    * */
    public static int compileVertexShader(String shadeCode) {
        return compileShader(GL_VERTEX_SHADER, shadeCode);
    }

    /*
    * 编译片段着色器代码
    * */
    public static int compileFragmentShader(String shadeCode) {
        return compileShader(GL_FRAGMENT_SHADER, shadeCode);
    }

    /*
    * 编译着色器代码
    * */
    private static int compileShader(int type, String shaderCode) {
        //创建着色器
        final int shaderObjectId = glCreateShader(type);
        if (shaderObjectId == 0) {
            Log.w(TAG, "could not create new shader");
            return 0;
        }
        //先上传Source
        glShaderSource(shaderObjectId, shaderCode);
        //编译
        glCompileShader(shaderObjectId);
        //获取编译结果
        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
        Log.v(TAG, "Result of compiling source:" + "\n" + shaderCode + "\n" + glGetShaderInfoLog(shaderObjectId));
        if (compileStatus[0] == 0) {
            glDeleteShader(shaderObjectId);
            Log.w(TAG, "Compilation of shader failed");
            return 0;
        }
        return shaderObjectId;
    }

    /*
    * 关联着色器与OpenGL程序
    * */
    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        //创建一个OpenGL程序对象，获取程序ID
        final int programObjectId = glCreateProgram();
        if (programObjectId == 0) {
            Log.w(TAG, "could not create new program");
            return 0;
        }
        //附上着色器
        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);
        //链接程序
        glLinkProgram(programObjectId);
        //获取链接结果
        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
        Log.v(TAG, "Result of linking program:\n" + glGetProgramInfoLog(programObjectId));
        if (linkStatus[0] == 0) {
            glDeleteProgram(programObjectId);
            Log.w(TAG, "Linking of program failed");
            return 0;
        }
        return programObjectId;
    }

    /*
    * 验证程序
    * */
    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);
        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.v(TAG, "Result of validating program: " + validateStatus[0] + "\nLog:" + glGetProgramInfoLog(programObjectId));
        return validateStatus[0] != 0;
    }
}
