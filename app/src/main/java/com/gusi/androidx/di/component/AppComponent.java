package com.gusi.androidx.di.component;

import com.gusi.androidx.app.App;
import com.gusi.androidx.di.module.AppModule;
import com.gusi.androidx.di.module.HttpModule;
import com.gusi.androidx.model.DataManager;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * @Author ylw  2018/6/21 14:39
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    App getContext();  // 提供App的Context

    DataManager getDataManager(); //数据中心

    /**
     * OkHttpClientBuilder
     *
     * @return
     */
    OkHttpClient.Builder getOkHttpClientBuilder();

}
