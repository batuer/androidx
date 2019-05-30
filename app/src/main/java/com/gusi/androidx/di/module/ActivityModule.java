package com.gusi.androidx.di.module;


import android.app.Activity;

import com.gusi.androidx.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @Author ylw  2018/6/21 17:25
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
