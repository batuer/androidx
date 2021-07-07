package com.gusi.androidx.module.constraintlayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseFragment;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ConstraintFragment extends BaseFragment {
    public static final String LAYOUT_RES = "LayoutRes";

    @Nullable
    @BindView(R.id.btn_gone)
    Button mBtnGone;

    public ConstraintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ConstraintFragment.
     */
    public static ConstraintFragment newInstance(int layout) {
        ConstraintFragment fragment = new ConstraintFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES, layout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return getArguments().getInt(LAYOUT_RES);
    }

    @Override
    protected void initInject() {
    }

    @Override
    protected void initView() {
        super.initView();
        if (mBtnGone != null) {
            mBtnGone.setOnClickListener(v -> {
                mBtnGone.setVisibility(View.GONE);
                mBtnGone.postDelayed(() -> mBtnGone.setVisibility(View.VISIBLE), 3000);
            });

        }
    }
}
