package com.gusi.androidx.module.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AidlService extends Service {
  private static final String TAG = "Fire_Aidl";

  public AidlService() {
    Log.d(TAG, Log.getStackTraceString(new Throwable()));
  }

  @Override
  public IBinder onBind(Intent intent) {
    Log.w(TAG, "onBind: " + intent);
    return null;
  }

  @Override
  public void onCreate() {
    Log.w(TAG, "onCreate: ");
    super.onCreate();
  }

  //     class Service1 extends IMyAidlInterface.Stub {}
}
