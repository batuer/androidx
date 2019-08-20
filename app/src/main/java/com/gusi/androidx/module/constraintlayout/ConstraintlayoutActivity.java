package com.gusi.androidx.module.constraintlayout;

import androidx.collection.ArrayMap;
import androidx.constraintlayout.widget.Barrier;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseActivity;

import butterknife.BindView;

public class ConstraintlayoutActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected int getLayout() {
        return R.layout.activity_constraintlayout;
    }

    @Override
    protected void initInject() {}

    @Override
    protected void initView() {
        super.initView();
        initToolBar(mToolbar, true, "Constraint");
        ArrayMap<String, Fragment> fragmentMap = new ArrayMap<>();
        fragmentMap.put("相对定位", ConstraintFragment.newInstance(R.layout.fragment_constraint_relative));
        fragmentMap.put("角度定位", ConstraintFragment.newInstance(R.layout.fragment_constraint_angle));
        fragmentMap.put("边距", ConstraintFragment.newInstance(R.layout.fragment_constraint_margin));
        fragmentMap.put("居中和偏移", ConstraintFragment.newInstance(R.layout.fragment_constraint_center_offset));
        fragmentMap.put("尺寸约束", ConstraintFragment.newInstance(R.layout.fragment_constraint_measure));
        fragmentMap.put("链", ConstraintFragment.newInstance(R.layout.fragment_constraint_chain));
        fragmentMap.put("Optimizer", ConstraintFragment.newInstance(R.layout.fragment_constraint_optimzer));
        fragmentMap.put("Barrier", BarrierFragment.newInstance());
        fragmentMap.put("Group", ConstraintFragment.newInstance(R.layout.fragment_constraint_group));
        fragmentMap.put("Placeholder", ConstraintPlacrholdrFragment.newInstance(R.layout.fragment_constraint_placeholder));
        fragmentMap.put("Guideline", ConstraintFragment.newInstance(R.layout.fragment_constraint_guide));
        fragmentMap.put("Layer", ConstraintLayerFragment.newInstance(R.layout.fragment_constraint_layer));
        fragmentMap.put("CircularReveal", CirclularRevealFragment.Companion.newInstance());
        fragmentMap.put("Flow", FlowFragment.Companion.newInstance());
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentMap.valueAt(position);
            }

            @Override
            public int getCount() {
                return fragmentMap.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return fragmentMap.keyAt(position);
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
