package com.gusi.androidx.module;

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
    }

}
