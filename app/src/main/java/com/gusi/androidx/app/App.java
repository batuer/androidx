package com.gusi.androidx.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Process;
import android.os.StrictMode;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.MultiDex;
import com.blankj.utilcode.util.Utils;
import com.gusi.androidx.di.component.AppComponent;
import com.gusi.androidx.di.component.DaggerAppComponent;
import com.gusi.androidx.di.module.AppModule;
import com.gusi.androidx.di.module.HttpModule;
import com.gusi.androidx.module.db.DBManger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import me.weishu.reflection.Reflection;

/**
 * @Author ylw  2018/6/20 17:55
 */
public class App extends Application {
  private static final String TAG = "Fire_App";
  private AppComponent mAppComponent;
  private static App sApp;

  public App() {
    Log.i(TAG, Log.getStackTraceString(new Throwable("")));
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
    Reflection.unseal(base);
    Log.w(TAG, "attachBaseContext: " + getBaseContext() + " : " + base);
  }


  @Override
  @SuppressLint("SoonBlockedPrivateApi")
  public void onCreate() {
    super.onCreate();
    Log.w(TAG, "onCreate: " + getBaseContext() + " : " + this);
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
    Utils.init(this);
    sApp = this;
    initComponent();
    initBugly();
    DBManger.getInstance();
    FragmentManager.enableDebugLogging(true);
//    Looper.getMainLooper().setMessageLogging(new LogPrinter(Log.INFO, "Ylw_Androidx"));
//        register();


//        BaseFragment.DEBUG = true;
//        getMainLooper().setMessageLogging(new LogPrinter(4, "Ylw"));

//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectAll()//开启所有的detectXX系列方法
//                .penaltyDialog()//弹出违规提示框
//                .penaltyLog()//在Logcat中打印违规日志
//                .build());
//        requestDataFromNet();
    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectActivityLeaks()//检测Activity泄露
        .penaltyLog()//在Logcat中打印违规日志
        .build());

    Log.i(TAG, "initView: " + Process.myUid() + " : " + Thread.currentThread().getName());

    try {
      Looper looper = Looper.getMainLooper();
      Class<? extends Looper> aClass = looper.getClass();

      Method method = aClass.getDeclaredMethod("setSlowLogThresholdMs", long.class, long.class);
      Object invoke = method.invoke(looper, 100, 100);
      Log.i(TAG, "initView: " + method + " : " + invoke);
    } catch (Exception e) {
      Log.e(TAG, "initView: ", e);
    }

  }

  @Override
  public void onConfigurationChanged(@NonNull Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    Log.w(TAG + "_Ylw", Log.getStackTraceString(new Throwable("")));
    Log.e(TAG, newConfig + "");
  }

  /**
   * 请求数据
   */
  private void requestDataFromNet() {
    URL url;
    try {
      url = new URL("http://www.baidu.com");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.connect();
      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String lines;
      StringBuilder sb = new StringBuilder();
      while ((lines = reader.readLine()) != null) {
        sb.append(lines);
      }

      Log.d("response", sb.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private int mCount = 0;

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  private void register() {
    registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
      @Override
      public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.w(TAG, (mCount++) + " :onActivityCreated: " + activity);
      }

      @Override
      public void onActivityStarted(Activity activity) {

      }

      @Override
      public void onActivityResumed(Activity activity) {

      }

      @Override
      public void onActivityPaused(Activity activity) {

      }

      @Override
      public void onActivityStopped(Activity activity) {

      }

      @Override
      public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

      }

      @Override
      public void onActivityDestroyed(Activity activity) {

      }
    });
  }

  private void initComponent() {
    mAppComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .httpModule(new HttpModule())
        .build();
  }

  public AppComponent getAppComponent() {
    return mAppComponent;
  }

  @SuppressLint("MissingPermission")
  private void initBugly() {
    Beta.autoInit = true;
    Beta.autoCheckUpgrade = true;
    Beta.autoDownloadOnWifi = false;
    Beta.canShowApkInfo = true;
    Bugly.setIsDevelopmentDevice(getApplicationContext(), false);
    Bugly.init(getApplicationContext(), "0000000", true);
    //主动检查更新
    Beta.upgradeStateListener = new UpgradeStateListener() {
      @Override
      public void onUpgradeFailed(boolean b) {

      }

      @Override
      public void onUpgradeSuccess(boolean b) {

      }

      @Override
      public void onUpgradeNoVersion(boolean b) {

      }

      @Override
      public void onUpgrading(boolean b) {

      }

      @Override
      public void onDownloadCompleted(boolean b) {

      }
    };
    Beta.checkUpgrade();
  }


  public static App getInstance() {
    if (sApp == null) {
      sApp = (App) Utils.getApp();
    }
    return sApp;
  }
}
