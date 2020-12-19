package com.gusi.androidx.module.constraintlayout;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseActivity;

import butterknife.BindView;

public class ConstraintlayoutActivity extends BaseActivity {
    private static final String TAG = "ConstraintlayoutActivity";
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    int count = 0;
    long longCount = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Debug.startMethodTracing("Ylw");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Debug.stopMethodTracing();
    }

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
        fragmentMap.put("Placeholder",
                ConstraintPlacrholdrFragment.newInstance(R.layout.fragment_constraint_placeholder));
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

    /**
     * 模拟一个自身占用时间不长，但调用却非常频繁的函数
     */
    private void print() {
        count = count++;
    }

    private void printNum() {
        long start = System.currentTimeMillis();
        while ((System.currentTimeMillis() - start) < 200000) {
            System.out.println("-----");
        }
        Log.w(TAG, "printNum: ");
    }

    /**
     * 模拟一个调用次数不多，但每次调用却需要花费很长时间的函数
     */
    private void calculate() {
        long total = 0;
        for (int i = 0; i < 100000; i++) {
            total += i;
        }
        Log.e(TAG, "calculate_thread:" + total);
    }

}
