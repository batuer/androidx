package com.gusi.androidx.module.constraintlayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

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
    protected void initInject() {
    }

    @Override
    protected void initView() {
        super.initView();
        initToolBar(mToolbar, true, "Constraint");
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        titles.add("相对定位");
        titles.add("角度定位");
        titles.add("边距");
        titles.add("居中和偏移");
        titles.add("尺寸约束");
        titles.add("链");
        titles.add("Optimizer");
        titles.add("Barrier");
        titles.add("Group");
        titles.add("Placeholder");
        titles.add("Guideline");
        titles.add("Layer ");
        fragments.add(ConstraintFragment.newInstance(R.layout.fragment_constraint_relative));
        fragments.add(ConstraintFragment.newInstance(R.layout.fragment_constraint_angle));
        fragments.add(ConstraintFragment.newInstance(R.layout.fragment_constraint_margin));
        fragments.add(ConstraintFragment.newInstance(R.layout.fragment_constraint_center_offset));
        fragments.add(ConstraintFragment.newInstance(R.layout.fragment_constraint_measure));
        fragments.add(ConstraintFragment.newInstance(R.layout.fragment_constraint_chain));
        fragments.add(ConstraintFragment.newInstance(R.layout.fragment_constraint_optimzer));
        fragments.add(ConstraintFragment.newInstance(R.layout.fragment_constraint_barrier));
        fragments.add(ConstraintFragment.newInstance(R.layout.fragment_constraint_group));
        fragments.add(ConstraintPlacrholdrFragment.newInstance(R.layout.fragment_constraint_placeholder));
        fragments.add(ConstraintFragment.newInstance(R.layout.fragment_constraint_guide));
        fragments.add(ConstraintLayerFragment.newInstance(R.layout.fragment_constraint_layer));
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
