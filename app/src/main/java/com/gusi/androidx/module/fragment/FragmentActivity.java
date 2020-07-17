package com.gusi.androidx.module.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gusi.androidx.R;

public class FragmentActivity extends androidx.fragment.app.FragmentActivity {

    private static final String TAG = "Fire_FragmentActivity";
    private BlankFragment mBlankFragment;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
//        window.addOnFrameMetricsAvailableListener(new Window.OnFrameMetricsAvailableListener() {
//            @Override
//            public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics,
//                                                int dropCountSinceLastInvocation) {
//
////                get((ViewGroup) decorView);
//                Log.e(TAG,
//                        decorView.isInLayout() + " : " + decorView.isLayoutRequested() + " : " + decorView
//                                .getDrawingTime());
//            }
//        }, new Handler());

        View view = getWindow().getDecorView();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                post(null);
            }
        }, 2000);
    }


    private void get(ViewGroup viewGroup) {

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup && !(view instanceof ListView)) {
                get((ViewGroup) view);
            } else {

                if (view.isInLayout() || view.isLayoutRequested()) {
                    Log.i(TAG, "isInLayout:" + view.isInLayout() + " :mDrawingTime =  " + view.getDrawingTime() +
                            " : " + view.toString());
                } else {
                    Log.d(TAG, " :mDrawingTime =  " + view.getDrawingTime() + " : " + view.toString());
                }
            }
        }
    }

    public void addFragment(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mBlankFragment = BlankFragment.newInstance("Add");
        transaction.add(R.id.fl_add, mBlankFragment);
        transaction.commit();
    }

    public void replaceFragment(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_add, BlankFragment.newInstance("Replace"));
        transaction.commit();
    }

    public void removeFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fragment : fragmentManager.getFragments()) {
            transaction.remove(fragment);
        }
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, Log.getStackTraceString(new Throwable()));
        Log.w(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public void onDetachedFromWindow() {
        Log.d(TAG, Log.getStackTraceString(new Throwable()));
        super.onDetachedFromWindow();
        Log.w(TAG, "onDetachedFromWindow: ");
    }

    public void post(View v) {
        View view = getWindow().getDecorView();
        view.post(new Runnable() {
            @Override
            public void run() {
                Log.w(TAG, "run: post()");
            }
        });
        view.postOnAnimation(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: postOnAnimation()");
            }
        });
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.w(TAG, isFinishing() + " : " + isDestroyed() + " : " + ev.toString());
//        return super.dispatchTouchEvent(ev);
//    }
}