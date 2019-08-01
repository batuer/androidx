package com.gusi.androidx.module.areastate;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseFragment;
import com.gusi.androidx.module.project.ProjectFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AreaFragment extends BaseFragment<AreaPresenter> implements AreaContract.View {
    public static final String PRE_URL = "PreUrl";
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private StateFragmentPagerAdapter mAreaStateAdapter;
    private String mPreUrl;

    private AreaFragment() {
    }

    public static AreaFragment newInstance(String preUrl) {
        Bundle args = new Bundle();
        AreaFragment fragment = new AreaFragment();
        args.putString(PRE_URL, preUrl);
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
    protected void initView() {
        mTabLayout.setupWithViewPager(mViewPager);
        mAreaStateAdapter = new StateFragmentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAreaStateAdapter);
    }

    @Override
    protected void initData() {
        mPreUrl = getArguments().getString(PRE_URL);
        if (!TextUtils.isEmpty(mPreUrl)) {
            mPresenter.getRegistration(mPreUrl);
        }
    }

    @Override
    public void showRegistration(List<Pair<String, String>> pairList) {
        Log.e("Fire", "AreaFragment:67行:" + mPreUrl);
        List<Pair<Fragment, String>> fragmentList = new ArrayList<>(pairList.size());
        for (Pair<String, String> pair : pairList) {
            String preUrl = mPreUrl + "?state=" + pair.second + "&page=";
            ProjectFragment areaStateFragment = ProjectFragment.newInstance(preUrl);
            fragmentList.add(new Pair<>(areaStateFragment, pair.first));
        }
        mAreaStateAdapter.setData(fragmentList);
    }
}
