package com.gusi.sio_server;


import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;

/**
 * @author Ylw
 * @since 2019/9/26 23:06
 */
public class App extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
