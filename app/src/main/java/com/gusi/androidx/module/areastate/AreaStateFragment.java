package com.gusi.androidx.module.areastate;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseFragment;

import butterknife.BindView;

/**
 * @author ylw 2019/7/21 22:01
 * @Des
 */
public class AreaStateFragment extends BaseFragment<AreaStatePresenter> implements AreaStateContract.View {
    public static final String PRE_URL = "PreUrl";

    @BindView(R.id.rcv_project)
    RecyclerView mRcvProject;
    private int mPage = 1;
    private String mPreUrl;

    private AreaStateFragment() {}

    public static AreaStateFragment newInstance(String preUrl) {
        Bundle args = new Bundle();
        args.putString(PRE_URL, preUrl);
        AreaStateFragment fragment = new AreaStateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_area;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mPreUrl = getArguments().getString(PRE_URL);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getProjects(mPreUrl + mPage);
    }
}
