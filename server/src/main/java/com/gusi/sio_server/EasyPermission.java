package com.gusi.sio_server;

import android.Manifest;
import android.annotation.SuppressLint;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * @Author ylw 2019/3/30 16:16
 */
public class EasyPermission {
    @SuppressLint({"WrongConstant", "MissingPermission"})
    public static boolean hasAllPermission() {
        String[] permissions = {Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.READ_PHONE_STATE};
        if (!PermissionUtils.isGranted(permissions)) {
            PermissionUtils.permission(permissions).callback(new PermissionUtils.SimpleCallback() {
                @Override
                public void onGranted() {

                }

                @Override
                public void onDenied() {
                    PermissionUtils.launchAppDetailsSettings();
                    ToastUtils.showShort("请授予App相应权限!");
                }
            }).request();
            return false;
        } else {

        }
        return true;
    }
}
