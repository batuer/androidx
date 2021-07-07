package com.gusi.androidx.base;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
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

    private String TAG = this + "";

    public static boolean DEBUG = false;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (DEBUG) {
            Log.w(TAG, "onAttach: " + getContext());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) {
            Log.w(TAG, "onCreate: " + getContext());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (DEBUG) {
            Log.w(TAG, "onActivityCreated: " + getContext());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (DEBUG) {
            Log.w(TAG, "onStart: " + getContext());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (DEBUG) {
            Log.w(TAG, "onResume: " + getContext());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (DEBUG) {
            Log.w(TAG, "onPause: " + getContext());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (DEBUG) {
            Log.w(TAG, "onStop: " + getContext());
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (DEBUG) {
            Log.w(TAG, "onDetach: " + getContext());
        }
    }

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
        if (DEBUG) {
            Log.w(TAG, "onCreateView: " + getContext());
        }
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
        if (DEBUG) {
            Log.w(TAG, "onDestroyView: " + getContext());
        }
    }

    @Override
    public void onDestroy() {
        if (mBind != null) {
            mBind.unbind();
        }
        super.onDestroy();
        if (DEBUG) {
            Log.w(TAG, "onDestroy: " + getContext());
        }
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
