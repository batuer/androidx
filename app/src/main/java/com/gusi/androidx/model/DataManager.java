package com.gusi.androidx.model;


import com.gusi.androidx.model.http.Api;

/**
 * @Author ylw  2018/6/21 14:20
 */
public final class DataManager implements IData, Api {
    private Api mApi;

    public DataManager() {
//        FileUtils.copyDir()
    }

    public DataManager(Api api) {
        mApi = api;
    }
}
