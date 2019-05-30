package com.gusi.androidx.di.module;


import com.gusi.androidx.app.App;
import com.gusi.androidx.model.DataManager;
import com.gusi.androidx.model.http.Api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @Author ylw  2018/6/21 14:41
 */
@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }



    @Provides
    @Singleton
    DataManager provideDataManager(Api api) {
        return new DataManager(api);
    }
}
