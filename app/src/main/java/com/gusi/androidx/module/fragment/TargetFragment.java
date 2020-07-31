package com.gusi.androidx.module.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.gusi.androidx.R;

/**
 * @author Ylw
 * @since 2020/7/22 22:17
 */
public class TargetFragment extends Fragment {
    public static TargetFragment newInstance() {
        Bundle args = new Bundle();
        TargetFragment fragment = new TargetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_target, container, false);
    }

}
