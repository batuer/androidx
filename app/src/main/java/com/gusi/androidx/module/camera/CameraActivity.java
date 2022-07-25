package com.gusi.androidx.module.camera;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.gusi.androidx.R;

import java.io.File;
import java.util.Map;

public class CameraActivity extends Activity {
    private static final String TAG = "Ylw_Camera";
    private boolean isTakePhotoing = false;
    private Camera2BackgroundUtil mBackgroundUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
//        mBackgroundUtil = new Camera2BackgroundUtil(this, new CameraCallBack() {
//            @Override
//            public void onCameraInited() {
//
//            }
//
//            @Override
//            public void onErr(String message) {
//                isTakePhotoing = false;
//                Log.i(TAG, "onErr: " + message);
//            }
//
//            @Override
//            public void onTakePhotoOk(String path) {
//                isTakePhotoing = false;
//                Log.d(TAG, "onTakePhotoOk: " + path);
//            }
//
//            @Override
//            public void onStartRecord() {
//
//            }
//
//            @Override
//            public void onStartPreview() {
//
//            }
//
//            @Override
//            public void onRecordComplete(String path) {
//                Log.i(TAG, "onRecordComplete: " + path);
//            }
//        });
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

    public void gson(View view) {
        String json = "{\"lastAccessTime\":1552491006960,\"batchManagerSetting\":{\"26008\":{\"managerAutoAnswer" +
                "\":\"1\"},\"26009\":{\"managerAutoAnswer\":\"0\"}},\"resultCode\":0,\"traceId\":\"960065541515231\"," +
                "\"unreadMessage\":true}";
        Gson gson = new Gson();
        JsonRootBean jsonRootBean = gson.fromJson(json, JsonRootBean.class);
        Log.i(TAG, "gson: " + jsonRootBean);
        Map<String, ManagerBean> batchManagerSetting = jsonRootBean.batchManagerSetting;
        Log.i(TAG, "gson: " + batchManagerSetting.getClass());
        for (Map.Entry<String, ManagerBean> entry : batchManagerSetting.entrySet()) {
            Log.i(TAG, "gson: " + entry.getKey() + ":" + entry.getValue());
        }
    }


    private class ManagerBean {
        private String managerAutoAnswer;

        public String getManagerAutoAnswer() {
            return managerAutoAnswer;
        }

        public void setManagerAutoAnswer(String managerAutoAnswer) {
            this.managerAutoAnswer = managerAutoAnswer;
        }

        @Override
        public String toString() {
            return "ManagerBean{" +
                    "managerAutoAnswer='" + managerAutoAnswer + '\'' +
                    '}';
        }
    }

    public class JsonRootBean {

        private long lastAccessTime;
        private Map<String, ManagerBean> batchManagerSetting;
        private int resultCode;
        private String traceId;
        private boolean unreadMessage;

        public void setLastAccessTime(long lastAccessTime) {
            this.lastAccessTime = lastAccessTime;
        }

        public long getLastAccessTime() {
            return lastAccessTime;
        }

        public Map<String, ManagerBean> getBatchManagerSetting() {
            return batchManagerSetting;
        }

        public void setBatchManagerSetting(Map<String, ManagerBean> batchManagerSetting) {
            this.batchManagerSetting = batchManagerSetting;
        }

        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }

        public int getResultCode() {
            return resultCode;
        }

        public void setTraceId(String traceId) {
            this.traceId = traceId;
        }

        public String getTraceId() {
            return traceId;
        }

        public void setUnreadMessage(boolean unreadMessage) {
            this.unreadMessage = unreadMessage;
        }

        public boolean getUnreadMessage() {
            return unreadMessage;
        }

        @Override
        public String toString() {
            return "JsonRootBean{" +
                    "lastAccessTime=" + lastAccessTime +
                    ", batchManagerSetting='" + batchManagerSetting + '\'' +
                    ", resultCode=" + resultCode +
                    ", traceId='" + traceId + '\'' +
                    ", unreadMessage=" + unreadMessage +
                    '}';
        }
    }
}