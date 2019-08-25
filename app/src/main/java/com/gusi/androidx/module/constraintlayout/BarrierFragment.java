package com.gusi.androidx.module.constraintlayout;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseFragment;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass. create an instance of this fragment.
 */
public class BarrierFragment extends BaseFragment {

    private ConstraintLayout mCl;

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
        mCl = getView().findViewById(R.id.cl_parent);
    }

    private int mInt = 0;

    @OnClick(R.id.tv2)
    public void clickTv(TextView tv) {
        tv.setText("" + (mInt++) + tv.getText());
        // View view3 = getView().findViewById(R.id.tv3);
        // test(tv);
        // test(view3);
        tv.post(() -> test(null));
    }

    private void test(View view) {
        // float f = view.getTop() + ((view.getBottom() - view.getTop()) / 2.0f);
        // Log.w("Fire", "BarrierFragment:56行:" + f + " : " + view);
        int sumWidth = 0;
        for (int i = 0; i < mCl.getChildCount(); i++) {
            View child = mCl.getChildAt(i);
            sumWidth += child.getWidth();
            Log.w("Fire",
                child.getContentDescription() + " :width:" + child.getWidth() + " :height: " + child.getHeight()
                    + " :left: " + child.getLeft() + " :right: " + child.getRight() + " :top: " + child.getTop()
                    + " :bottom: " + child.getBottom());
        }
        Log.e("Fire",
            "width:" + mCl.getWidth() + ":height:" + mCl.getHeight() + " :left: " + mCl.getLeft() + " :right: "
                + mCl.getRight() + " :top: " + mCl.getTop() + " :bottom: " + mCl.getBottom() + " :sumWidth: "
                + sumWidth);
    }
}
