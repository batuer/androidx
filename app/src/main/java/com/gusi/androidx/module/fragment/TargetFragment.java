package com.gusi.androidx.module.fragment;

import android.os.Bundle;

import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseFragment;

/**
 * @author Ylw
 * @since 2020/7/22 22:17
 */
public class TargetFragment extends BaseFragment {
    public static TargetFragment newInstance() {
        Bundle args = new Bundle();
        TargetFragment fragment = new TargetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_target;
    }

    @Override
    protected void initInject() {

    }

}
