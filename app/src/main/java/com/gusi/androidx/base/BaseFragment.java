package com.gusi.androidx.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gusi.androidx.app.App;
import com.gusi.androidx.di.component.DaggerFragmentComponent;
import com.gusi.androidx.di.component.FragmentComponent;
import com.gusi.androidx.di.module.FragmentModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author ylw   2018/6/21 18:13
 * @Des
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {

    /**
     * ButterKnife
     */
    private Unbinder mBind;
    /**
     * inject mPresenter
     */
    @Inject
    protected P mPresenter;
    protected BaseActivity mAct;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        mBind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mAct = (BaseActivity) getActivity();
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (mBind != null) {
            mBind.unbind();
        }
        super.onDestroy();
    }

    /**
     * layout res
     *
     * @return layout res
     */
    @LayoutRes
    protected abstract int getLayout();

    /**
     * inject
     */
    protected abstract void initInject();

    /**
     * init mView
     */
    protected void initView() {
    }

    /**
     * init data
     */
    protected void initData() {
    }

    /**
     * back
     *
     * @return
     */
    public boolean onBackPressed() {
        return false;
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getInstance()
                        .getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Override
    public void showInfo(CharSequence info) {
        mAct.showInfo(info);
    }

    @Override
    public void showError(CharSequence error) {
        mAct.showError(error);
    }

    @Override
    public void showLoading(CharSequence msg, boolean canCancel) {
        mAct.showLoading(msg.toString(), canCancel);
    }

    @Override
    public void hideLoading() {
        mAct.hideLoading();
    }
}
