package com.gusi.androidx.di.component;

import android.app.Activity;

import com.gusi.androidx.di.module.ActivityModule;
import com.gusi.androidx.di.scope.ActivityScope;

import dagger.Component;

/**
 * @Author ylw  2018/6/21 14:23
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
}
