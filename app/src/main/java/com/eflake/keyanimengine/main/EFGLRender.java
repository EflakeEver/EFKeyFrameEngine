package com.eflake.keyanimengine.main;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.eflake.keyanimengine.opengl.ShaderHelper;
import com.eflake.keyanimengine.opengl.TextResReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

public class EFGLRender implements GLSurfaceView.Renderer {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private Context mContext;
    private int program;
    public static final String U_COLOR = "u_Color";
    public static final String A_POSITION = "a_Position";
    public int uColorLocation;
    public int aPositionLocation;
    //X、Y坐标，永远在[-1,1]之间
    private float[] tableVerticesWithTriangles = {
            // Triangle 1
            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f,
            // Triangle 2
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,
            // Line 1
            -0.5f, 0.0f,
            0.5f, 0.0f,
            // Mallets
            0.0f, -0.25f,
            0.0f, 0.25f,
    };
    public static final int BYTES_PER_FLOAT = 4;
    private FloatBuffer vertexData;

    public EFGLRender(Context context) {
        mContext = context;
        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        String vertexShaderSource = TextResReader.readTextFileFromRes(mContext, R.raw.ef_vertex_shader);
        String fragmentShaderSource = TextResReader.readTextFileFromRes(mContext, R.raw.ef_fragment_shader);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        ShaderHelper.validateProgram(program);
        glUseProgram(program);
        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        //确保指针位置为开始
        vertexData.position(0);
        //关联属性a_Position的数据
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
        //允许使用位置属性数组
        glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);//设置视口
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);//清空缓存
        //绘制矩形
        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        //绘制直线
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_LINES, 6, 2);
        //绘制点
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 8, 1);
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 9, 1);
    }
}
