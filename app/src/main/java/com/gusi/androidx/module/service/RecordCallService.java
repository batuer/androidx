package com.gusi.androidx.module.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ylw
 * @since 2021/11/15 21:55
 */
public class RecordCallService extends Service {
    private static final String TAG = "Ylw_RecordCallService";
    private MediaRecorder recorder; //录音的一个实例

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //获得电话管理器
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        //启动监听.传入一个listener和监听的事件,
        MyListener listener = new MyListener();
        Log.i(TAG, "onCreate: " + listener);
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);


        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int callState = tm.getCallState();
                Log.i(TAG, "onReceive: " + callState);
                if (callState == TelephonyManager.CALL_STATE_RINGING) {
                    prepareRecord();
                }
                if (callState == TelephonyManager.CALL_STATE_OFFHOOK) {
                    startRecord();
                }
                if (callState == TelephonyManager.CALL_STATE_IDLE) {
                    stopRecord();
                }
            }
        }, new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED));
    }

    private class MyListener extends PhoneStateListener {

        //在电话状态改变的时候调用
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            Log.i(TAG, "onCallStateChanged: " + state);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    //空闲状态
                    stopRecord();
                    break;

                case TelephonyManager.CALL_STATE_RINGING:
                    //响铃状态  需要在响铃状态的时候初始化录音服务
                    prepareRecord();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    //摘机状态（接听）
                    startRecord();
                    break;
            }
        }

    }

    private void startRecord() {
        if (recorder != null) {
            recorder.start(); //接听的时候开始录音
        }
    }

    private void stopRecord() {
        if (recorder != null) {
            recorder.stop();//停止录音
            recorder.release();//释放资源
            recorder = null;
        }
    }

    private void prepareRecord() {
        if (recorder == null) {
            recorder = new MediaRecorder();//初始化录音对象
            recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);//设置录音的输入源(麦克)
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//设置音频格式(3gp)
            createRecorderFile();//创建保存录音的文件夹
            try {
                String path = "sdcard/recorder" + "/" + getCurrentTime() + ".3gp";
                File file = new File(path);
                if (!file.exists()) {
                    file.createNewFile();
                }
                recorder.setOutputFile(path); //设置录音保存的文件
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//设置音频编码
                recorder.prepare();//准备录音
            } catch (IOException e) {
                Log.e(TAG, "onCallStateChanged: ", e);
            }
        }
    }


    //创建保存录音的目录
    private void createRecorderFile() {
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filePath = absolutePath + "/recorder";
        File file = new File(filePath);
        Log.i(TAG, "createRecorderFile: " + file);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    //获取当前时间，以其为名来保存录音
    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String str = format.format(date);
        return str;

    }
}
