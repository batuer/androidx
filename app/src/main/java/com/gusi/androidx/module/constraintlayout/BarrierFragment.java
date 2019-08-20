package com.gusi.androidx.module.constraintlayout;

import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseFragment;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass. create an instance of this fragment.
 */
public class BarrierFragment extends BaseFragment {

    public BarrierFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment ConstraintFragment.
     */
    public static BarrierFragment newInstance() {
        BarrierFragment fragment = new BarrierFragment();
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_constraint_barrier;
    }

    @Override
    protected void initInject() {}

    @Override
    protected void initView() {
        super.initView();
    }

    @OnClick(R.id.tv)
    public void clickTv(TextView tv) {
        tv.setText("A" + tv.getText());
    }
}
