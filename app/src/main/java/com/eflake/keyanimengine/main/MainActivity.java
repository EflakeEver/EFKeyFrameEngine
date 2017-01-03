package com.eflake.keyanimengine.main;

import android.Manifest;
import android.annotation.TargetApi;
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
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TransparentSurfaceView transparentAnimView;
    private boolean mPermissionGranted;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Real_Splash);
        setContentView(R.layout.activity_main);
        //获取屏幕参数
        ScreenDimenUtils.init(this);
        initWidget();
        //Android 5.0以上，需要获取SD卡读写权限
        getPermission();
    }

    private void initWidget() {
        transparentAnimView = (TransparentSurfaceView) findViewById(R.id.transparentAnimView);
        Button bt_start = (Button) findViewById(R.id.bt_start);
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mPermissionGranted) {
                    transparentAnimView.addAnimDemo();
                } else {
                    transparentAnimView.addAnimDemo();
                }
            }
        });
    }

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Dexter.checkPermissions(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    mPermissionGranted = true;
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                }
            }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest
                    .permission.WRITE_EXTERNAL_STORAGE);
        }
    }
}
