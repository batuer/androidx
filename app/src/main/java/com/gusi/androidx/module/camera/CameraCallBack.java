package com.gusi.androidx.module.camera;

/**
 * @author Ylw
 * @since 2022/6/17 21:20
 */
public interface CameraCallBack {
    void onCameraInited();

    void onErr(String message);

    void onTakePhotoOk(String path);

    void onStartRecord();

    void onStartPreview();

    void onRecordComplete(String path);
}
