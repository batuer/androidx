package com.gusi.androidx.module;

import android.view.Gravity;
import android.widget.RadioGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gusi.androidx.R;
import com.gusi.androidx.app.Constans;
import com.gusi.androidx.base.BaseActivity;
import com.gusi.androidx.module.areastate.AreaFragment;

/**
 * @author Ylw
 * @since 2019-05-31
 */
public class MainActivity extends BaseActivity {

    private RadioGroup mRgSelectArea;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() { }

    @Override
    protected void initView() {
        super.initView();
        initToolBar(mToolbar, false, "");
        addSelectArea();
    }

    private void addSelectArea() {
        mRgSelectArea = (RadioGroup)getLayoutInflater().inflate(R.layout.layout_select_area, mToolbar, false);
        Toolbar.LayoutParams params = (Toolbar.LayoutParams)mRgSelectArea.getLayoutParams();
        params.gravity = Gravity.CENTER;
        mToolbar.addView(mRgSelectArea, params);

        Fragment sixFragment = AreaFragment.newInstance("http://zfyxdj.xa.gov.cn/zfrgdjpt/xmgs.aspx");
        Fragment changanFragment = AreaFragment.newInstance("http://zfyxdj.xa.gov.cn/zfrgdjpt/xmgsca.aspx");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_content, sixFragment, Constans.FragmentTag.CITY_SIX_AREA);
        transaction.add(R.id.fl_content, changanFragment, Constans.FragmentTag.CA_AREA);
        transaction.hide(changanFragment);
        transaction.commit();

        mRgSelectArea.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rg_city_six) {
                showFragment(Constans.FragmentTag.CITY_SIX_AREA);
            } else if (checkedId == R.id.rg_changan) {
                showFragment(Constans.FragmentTag.CA_AREA);
            }
        });
    }

    private void showFragment(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fragment : fragmentManager.getFragments()) {
            transaction.hide(fragment);
        }
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            transaction.show(fragment);
        }
        transaction.commit();
    }
}