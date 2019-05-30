package com.gusi.androidx.module;

import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatDelegate;

import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseActivity;

/**
 * @author Ylw
 * @since 2019-05-31
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        super.initView();
        initToolBar(mToolbar, false, "Main");
        findViewById(R.id.iv_test).setOnClickListener(v -> {
            boolean isNight =
                    AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
            AppCompatDelegate.setDefaultNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_NO :
                    AppCompatDelegate.MODE_NIGHT_YES);
            recreate();
            showInfo(isNight ? "白天" : "夜间");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
