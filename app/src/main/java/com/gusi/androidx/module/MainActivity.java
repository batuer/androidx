package com.gusi.androidx.module;

import android.view.Gravity;
import android.widget.RadioGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseActivity;
import com.gusi.androidx.module.areastate.AreaFragmentPagerAdapter;
import com.gusi.androidx.module.areastate.AreaStateFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Ylw
 * @since 2019-05-31
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private AreaFragmentPagerAdapter mAreaStateAdapter;
    private RadioGroup mRgSelectArea;

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
        initToolBar(mToolbar, false, "");
        mTabLayout.setupWithViewPager(mViewPager);
        mAreaStateAdapter = new AreaFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAreaStateAdapter);
        addSelectArea();
    }

    @Override
    protected void initData() {
        mPresenter.getRegistration("http://zfyxdj.xa.gov.cn/zfrgdjpt/xmgs.aspx");
    }

    private void addSelectArea() {
        mRgSelectArea = (RadioGroup)getLayoutInflater().inflate(R.layout.layout_select_area, mToolbar, false);
        Toolbar.LayoutParams params = (Toolbar.LayoutParams)mRgSelectArea.getLayoutParams();
        params.gravity = Gravity.CENTER;
        mToolbar.addView(mRgSelectArea, params);
        mRgSelectArea.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rg_city_six) {
                mPresenter.getRegistration("http://zfyxdj.xa.gov.cn/zfrgdjpt/xmgs.aspx");
            } else if (checkedId == R.id.rg_changan) {
                mPresenter.getRegistration("http://zfyxdj.xa.gov.cn/zfrgdjpt/xmgsca.aspx");
            }
        });
    }

    @Override
    public void showRegistration(List<Pair<String, String>> pairList) {
        String areaUrl = "";
        if (mRgSelectArea.getCheckedRadioButtonId() == R.id.rg_city_six) {
            areaUrl = "http://zfyxdj.xa.gov.cn/zfrgdjpt/xmgs.aspx";
        } else {
            areaUrl = "http://zfyxdj.xa.gov.cn/zfrgdjpt/xmgsca.aspx";
        }
        List<Pair<Fragment, String>> fragmentList = new ArrayList<>(pairList.size());
        for (Pair<String, String> pair : pairList) {
            String preUrl = areaUrl + "?state=" + pair.second + "&page=";
            AreaStateFragment areaStateFragment = AreaStateFragment.newInstance(preUrl);
            fragmentList.add(new Pair<>(areaStateFragment, pair.first));
        }
        mAreaStateAdapter.setData(fragmentList);
    }
}
