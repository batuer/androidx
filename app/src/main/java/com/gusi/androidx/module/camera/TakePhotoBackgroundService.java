package com.gusi.androidx.module.camera;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;

/**
 * @author Liushihua
 * @date 2022-3-30 16:34
 * @desc
 */
public class TakePhotoBackgroundService extends Service {
    private static final String TAG = "Ylw_TakePhotoBackgroundService";
    private Camera2BackgroundUtil camera2Util;

    private boolean isTakePhotoing = false;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate() {
        super.onCreate();
        camera2Util = new Camera2BackgroundUtil(this, new CameraCallBack() {
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
                sendBroadcast(new Intent("com.desaysv.camera.complete"));
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

    /**
     * 执行拍照
     */
    private void takePhoto() {
        handler.post(() -> {
            if (!isTakePhotoing) {
                isTakePhotoing = true;
                File file = new File(getExternalCacheDir(), "picture_" + System.currentTimeMillis() + ".jpg");
                Log.i(TAG, "takePhoto: " + file);
                camera2Util.startTakePicture(file.getPath());
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        takePhoto();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

