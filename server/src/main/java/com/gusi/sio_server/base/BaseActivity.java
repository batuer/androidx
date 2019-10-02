package com.gusi.sio_server.base;


import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ToastUtils;
import com.gusi.loadingdialog.LoadingDialog;
import com.gusi.sio_server.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author ylw  2018/6/21 17:31
 */
public abstract class BaseActivity extends AppCompatActivity  {

    private Unbinder mBind;
    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    private LoadingDialog mLoadingDialog;

    public BaseActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mBind = ButterKnife.bind(this);
        initView();
        initData();
    }

    /**
     * get layout
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayout();


    protected void initView() {
    }

    protected void initData() {
    }

    /**
     * 初始化 Toolbar
     */
    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showInfo(CharSequence info) {
        ToastUtils.showShort(info);
    }


    public void showError(CharSequence error) {
        ToastUtils.showShort(error);
    }

    public void showLoading(CharSequence msg, boolean canCancel) {
        if (isFinishing()) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (isDestroyed()) return;
        }
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show((String) msg, canCancel);
    }

    public void hideLoading() {
        if (isFinishing()) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (isDestroyed()) return;
        }
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        mLoadingDialog = null;
        if (mBind != null) {
            mBind.unbind();
        }
        super.onDestroy();
    }
}
