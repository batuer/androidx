package com.gusi.androidx.module.constraintlayout;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.constraintlayout.helper.widget.Layer;
import androidx.fragment.app.Fragment;

import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ConstraintLayerFragment extends BaseFragment {
    public static final String LAYOUT_RES = "LayoutRes";

    @BindView(R.id.layer)
    Layer mLayer;

    public ConstraintLayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ConstraintFragment.
     */
    public static ConstraintLayerFragment newInstance(int layout) {
        ConstraintLayerFragment fragment = new ConstraintLayerFragment();
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

    @OnClick(R.id.button8)
    public void btn8Layer() {
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 360f);
        anim.addUpdateListener(animation -> {
            float angle = (float) animation.getAnimatedValue();
            mLayer.setRotation(angle);
            mLayer.setScaleX(1 + (180 - Math.abs(angle - 180)) / 20f);
            mLayer.setScaleY(1 + (180 - Math.abs(angle - 180)) / 20f);
            float shift_x = (float) (500 * Math.sin(Math.toRadians((angle * 5))));
            float shift_y = (float) (500 * Math.sin(Math.toRadians((angle * 7))));
            mLayer.setTranslationX(shift_x);
            mLayer.setTranslationY(shift_y);
        });
        anim.setDuration(4000);
        anim.start();
    }
}
