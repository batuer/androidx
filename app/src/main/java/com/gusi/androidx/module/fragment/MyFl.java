package com.gusi.androidx.module.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Ylw
 * @since 2020/8/1 19:26
 */
public class MyFl extends FrameLayout {

    private static final String TAG = "Fire_My";

    public MyFl(@NonNull Context context) {
        super(context);
    }

    public MyFl(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFl(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        Log.d(TAG, Log.getStackTraceString(new Throwable(child.toString())));
    }
}
