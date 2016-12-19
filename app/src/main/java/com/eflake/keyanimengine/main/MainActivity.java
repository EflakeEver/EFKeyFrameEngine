package com.eflake.keyanimengine.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eflake.keyanimengine.utils.ScreenDimenUtils;
import com.eflake.keyanimengine.view.TransparentSurfaceView;

public class MainActivity extends AppCompatActivity {

    private TransparentSurfaceView transparentAnimView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScreenDimenUtils.init(this);
        transparentAnimView = (TransparentSurfaceView) findViewById(R.id.transparentAnimView);
    }
}
