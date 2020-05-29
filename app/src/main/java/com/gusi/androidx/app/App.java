package com.gusi.androidx.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.Utils;
import com.gusi.androidx.di.component.AppComponent;
import com.gusi.androidx.di.component.DaggerAppComponent;
import com.gusi.androidx.di.module.AppModule;
import com.gusi.androidx.di.module.HttpModule;
import com.gusi.androidx.module.db.DBManger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * @Author ylw  2018/6/20 17:55
 */
public class App extends Application {
    private AppComponent mAppComponent;
    private static App sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        sApp = this;
        initComponent();
        initBugly();
        DBManger.getInstance();
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
        if (PermissionUtils.isGranted(Manifest.permission.READ_PHONE_STATE)) {
            CrashReport.setUserId("IMEI:" + PhoneUtils.getDeviceId());
        }
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
