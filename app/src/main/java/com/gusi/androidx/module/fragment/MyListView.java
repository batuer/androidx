package com.gusi.androidx.module.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

/**
 * @author Ylw
 * @since 2020/7/7 22:36
 */
public class MyListView extends ListView {
    private static final String TAG = "Fire_ListView";

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void layoutChildren() {
        try {
            super.layoutChildren();
        } catch (Exception e) {
            Log.e(TAG, "layoutChildren: " + e.toString());
        }
    }
}
