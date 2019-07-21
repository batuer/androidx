package com.gusi.androidx.module.areastate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author Ylw
 * @since 2019/7/21 21:07
 */
public class AreaFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<Pair<Fragment, String>> mPairList;

    public AreaFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mPairList.get(position).first;
    }

    @Override
    public int getCount() {
        return mPairList == null ? 0 : mPairList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mPairList.get(position).second;
    }

    public void setData(List<Pair<Fragment, String>> list) {
        this.mPairList = list;
        notifyDataSetChanged();
    }
}
