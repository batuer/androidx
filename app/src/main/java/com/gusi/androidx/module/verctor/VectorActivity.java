package com.gusi.androidx.module.verctor;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.gusi.androidx.R;

public class VectorActivity extends AppCompatActivity {

    private ImageView mIv, mIv1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);
        mIv = findViewById(R.id.iv);
        mIv1 = findViewById(R.id.iv1);
        mIv.setOnClickListener(v -> {
            Drawable background = mIv.getDrawable();
            Drawable background1 = mIv1.getDrawable();
            if (background != null && background instanceof AnimatedVectorDrawable) {
                AnimatedVectorDrawable animationDrawable = (AnimatedVectorDrawable)background;
                AnimatedVectorDrawable animationDrawable1 = (AnimatedVectorDrawable)background1;
                if (animationDrawable.isRunning()) {
                    animationDrawable.stop();
                }
                animationDrawable1.start();
                if (animationDrawable1.isRunning()) {
                    animationDrawable1.stop();
                }
                animationDrawable1.start();
            }
        });

    }
}
