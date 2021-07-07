package com.gusi.androidx.module.anim;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.gusi.androidx.R;

public class AnimActivity extends AppCompatActivity {
    private static final String TAG = "Fire_Anim";
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        mView = findViewById(R.id.view);
    }

    public void startValueAnim(View view) {
        int[] windowLocation = new int[2];
        mView.getLocationInWindow(windowLocation);
        ViewGroup parent = (ViewGroup) mView.getParent();



        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(5000);
        valueAnimator.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                Log.i(TAG, "getInterpolation: " + input);
                return 0;
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.w(TAG, "onAnimationUpdate: " + animation.getAnimatedValue());
            }
        });
        valueAnimator.setIntValues(0, 1, 2, 3, 4, 5);
        valueAnimator.start();
    }
}