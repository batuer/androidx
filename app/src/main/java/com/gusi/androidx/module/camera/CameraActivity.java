package com.gusi.androidx.module.camera;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gusi.androidx.R;

import java.io.File;

public class CameraActivity extends Activity {
    private static final String TAG = "Ylw_Camera";
    private boolean isTakePhotoing = false;
    private Camera2BackgroundUtil mBackgroundUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mBackgroundUtil = new Camera2BackgroundUtil(this, new CameraCallBack() {
            @Override
            public void onCameraInited() {

            }

            @Override
            public void onErr(String message) {
                isTakePhotoing = false;
                Log.i(TAG, "onErr: " + message);
            }

            @Override
            public void onTakePhotoOk(String path) {
                isTakePhotoing = false;
                Log.d(TAG, "onTakePhotoOk: " + path);
            }

            @Override
            public void onStartRecord() {

            }

            @Override
            public void onStartPreview() {

            }

            @Override
            public void onRecordComplete(String path) {
                Log.i(TAG, "onRecordComplete: " + path);
            }
        });
    }

    public void takePhoto(View view) {
        if (!isTakePhotoing) {
            isTakePhotoing = true;
            File file = new File(getExternalCacheDir(), "picture_" + System.currentTimeMillis() + ".jpg");
            Log.i(TAG, "takePhoto: " + file);
            mBackgroundUtil.startTakePicture(file.getPath());
        } else {
            Log.i(TAG, "takePhoto: 正在拍照中");
        }

    }
}