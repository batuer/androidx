package com.gusi.androidx.module.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.gusi.androidx.R;

/**
 * @author Ylw
 * @since 2019/8/24 23:17
 */
public class ViewActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
    }

    public void clickTest(View view) {
        view.requestLayout();
    }
}
