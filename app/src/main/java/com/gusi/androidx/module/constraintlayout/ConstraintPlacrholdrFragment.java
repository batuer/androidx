package com.gusi.androidx.module.constraintlayout;

import android.os.Bundle;

import androidx.constraintlayout.widget.Placeholder;
import androidx.fragment.app.Fragment;

import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ConstraintPlacrholdrFragment extends BaseFragment {
    public static final String LAYOUT_RES = "LayoutRes";

    @BindView(R.id.place_holder)
    Placeholder mPlaceholder;

    public ConstraintPlacrholdrFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ConstraintFragment.
     */
    public static ConstraintPlacrholdrFragment newInstance(int layout) {
        ConstraintPlacrholdrFragment fragment = new ConstraintPlacrholdrFragment();
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

    }

    private boolean mBoolean = false;

    @OnClick(R.id.btn_place_Holder)
    public void placeHolder() {
        mPlaceholder.setContentId(mBoolean ? -1 : R.id.view);
        mBoolean = !mBoolean;
    }
}
