package com.eflake.keyanimengine.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eflake.keyanimengine.keyframe.EFAnimManager;
import com.eflake.keyanimengine.utils.ScreenDimenUtils;
import com.eflake.keyanimengine.view.TransparentSurfaceView;

public class MainActivity extends AppCompatActivity {

    private TransparentSurfaceView transparentAnimView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Real_Splash);
        setContentView(R.layout.activity_main);
        ScreenDimenUtils.init(this);
        transparentAnimView = (TransparentSurfaceView) findViewById(R.id.transparentAnimView);
        Button bt_start = (Button) findViewById(R.id.bt_start);
        Button bt_end = (Button) findViewById(R.id.bt_end);
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transparentAnimView.addAnimDemo();
            }
        });

        bt_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                transparentAnimView.getAnimDemo().setIsRunning(false);
//                EFAnimManager.getInstance().removeAnimByKey("red");
            }
        });
    }
}
