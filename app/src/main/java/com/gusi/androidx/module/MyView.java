package com.gusi.androidx.module;

import android.content.Context;
import android.util.Log;
import android.view.View;

/**
 * @author Ylw
 * @since 2019/7/28 18:57
 */
public class MyView extends View {
    private Context mContext = test();

    public MyView(Context context) {
        super(context);
        Log.e("Fire", "MyView:16行:" + context);
    }

    private Context test() {
        Context context = getContext();
        Log.w("Fire", "MyView:19行:" + context);
        return context;
    }
}
