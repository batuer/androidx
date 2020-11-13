package com.gusi.androidx.module.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentServiceAdapter;

/**
 * @author Ylw
 * @since 2020/11/13 21:43
 */
public class MyService extends JobIntentServiceAdapter {
    private static final int JOB_ID = 1000;
    private static final String TAG = "MyService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.w(TAG, "onStartCommand: " + intent.toString());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(@NonNull Intent intent) {
        IBinder iBinder = super.onBind(intent);
        Log.w(TAG, "onBind: " + iBinder);
        return iBinder;
    }

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, MyService.class, JOB_ID, work);
    }


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.w(TAG, "onHandleWork: " + intent.toString());
    }
}
