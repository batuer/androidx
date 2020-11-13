package com.gusi.androidx.module.lv;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

/**
 * @author Ylw
 * @since 2020/5/29 23:49
 */
public class MyDialog extends Dialog {
    private static final String TAG = "Fire_MyDialog";
    Boolean mBoolean;

    @SuppressLint("WrongConstant")
    public MyDialog(@NonNull Context context) {
        super(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("",
                Context.MODE_APPEND);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.commit();
        edit.apply();

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
        Log.d(TAG, Log.getStackTraceString(new Throwable("Ylw")));
        Log.w("Fire", "MyDialog:27行:" + getCurrentFocus() + " ," + mBoolean);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        Log.d(TAG, Log.getStackTraceString(new Throwable("Ylw")));
        return super.onTouchEvent(event);
    }
}
