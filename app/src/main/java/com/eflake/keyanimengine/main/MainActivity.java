package com.eflake.keyanimengine.main;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.eflake.keyanimengine.utils.ScreenDimenUtils;
import com.eflake.keyanimengine.view.TransparentSurfaceView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Dexter.checkPermissions(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            transparentAnimView.addAnimDemo();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {

                        }
                    }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest
                            .permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    transparentAnimView.addAnimDemo();
                }
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
