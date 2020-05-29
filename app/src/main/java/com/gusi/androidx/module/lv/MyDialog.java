package com.gusi.androidx.module.lv;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

/**
 * @author Ylw
 * @since 2020/5/29 23:49
 */
public class MyDialog extends Dialog {
    Boolean mBoolean;

    public MyDialog(@NonNull Context context) {
        super(context);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mBoolean = hasFocus;
        Log.e("Fire", "MyDialog:onWindowFocusChanged =:" + hasFocus);
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        Log.w("Fire", "MyDialog:27行:" + getCurrentFocus() + " ," + mBoolean);
        return false;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        return false;
    }
}
