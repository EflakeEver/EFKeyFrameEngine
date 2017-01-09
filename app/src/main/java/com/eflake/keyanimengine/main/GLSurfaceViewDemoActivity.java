package com.eflake.keyanimengine.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eflake.keyanimengine.view.EFGLSurfaceView;

public class GLSurfaceViewDemoActivity extends AppCompatActivity {

    private EFGLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glsurface_view_demo);
        mGLSurfaceView = (EFGLSurfaceView)findViewById(R.id.gl_surfaceView);
        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(new EFGLRender(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }
}
