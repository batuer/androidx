package com.gusi.androidx.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Looper;
import android.util.Log;
import android.util.LogPrinter;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.gu.toolargetool.TooLargeTool;
import com.gusi.androidx.di.component.AppComponent;
import com.gusi.androidx.di.component.DaggerAppComponent;
import com.gusi.androidx.di.module.AppModule;
import com.gusi.androidx.di.module.HttpModule;
import com.gusi.androidx.module.db.DBManger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;

/**
 * @Author ylw  2018/6/20 17:55
 */
public class App extends Application {
    private static final String TAG = "App";
    private AppComponent mAppComponent;
    private static App sApp;

    public App() {
        Log.e(TAG, Log.getStackTraceString(new Throwable("")));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
        Utils.init(this);
        sApp = this;
        initComponent();
        initBugly();
        DBManger.getInstance();
        TooLargeTool.startLogging(this);
        FragmentManager.enableDebugLogging(true);
        Looper.getMainLooper().setMessageLogging(new LogPrinter(Log.INFO, "Ylw_Androidx"));
        register();


//        BaseFragment.DEBUG = true;
//        getMainLooper().setMessageLogging(new LogPrinter(4, "Ylw"));
        Debug.startMethodTracing("Ylw");
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
