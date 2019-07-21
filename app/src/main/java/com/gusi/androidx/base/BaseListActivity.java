package com.gusi.androidx.base;

import android.app.ListActivity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ToastUtils;
import com.gusi.androidx.R;
import com.gusi.androidx.app.App;
import com.gusi.androidx.di.component.ActivityComponent;
import com.gusi.androidx.di.component.DaggerActivityComponent;
import com.gusi.androidx.di.module.ActivityModule;
import com.gusi.loadingdialog.LoadingDialog;

import javax.inject.Inject;

/**
 * @author Ylw
 * @since 2019/7/21 16:29
 */
public abstract class BaseListActivity<P extends BasePresenter> extends ListActivity implements IBaseView {

    @Inject
    protected P mPresenter;
    private LoadingDialog mLoadingDialog;
    private Toolbar mToolBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected abstract void initInject();

    /**
     * 初始化 Toolbar
     */
    public void initToolBar(boolean homeAsUpEnabled, String title) {
        View content = findViewById(android.R.id.content);
        ViewStub viewStub = (ViewStub)((ViewGroup)content.getParent()).getChildAt(0);
        if (viewStub != null) {
            if (mToolBar == null) {
                viewStub.setLayoutResource(R.layout.layout_toolbar);
                mToolBar = (Toolbar)viewStub.inflate();
            }
            mToolBar.setTitle(title);
            Drawable navigationIcon = mToolBar.getNavigationIcon();
            Log.w("Fire", "BaseListActivity:59行:" + navigationIcon);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showInfo(CharSequence info) {
        ToastUtils.showShort(info);
    }

    @Override
    public void showError(CharSequence error) {
        ToastUtils.showShort(error);
    }

    @Override
    public void showLoading(CharSequence msg, boolean canCancel) {
        if (isFinishing())
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (isDestroyed())
                return;
        }
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show((String)msg, canCancel);
    }

    @Override
    public void hideLoading() {
        if (isFinishing())
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (isDestroyed())
                return;
        }
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        mLoadingDialog = null;
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder().appComponent(App.getInstance().getAppComponent())
            .activityModule(getActivityModule()).build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
