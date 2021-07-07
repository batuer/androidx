package com.gusi.androidx.module.flag;

import android.view.View;
import android.widget.TextView;

import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseActivity;

import butterknife.BindView;

/**
 * @author Ylw
 * @since 2019/8/19 22:16
 */
public class FlagActivity extends BaseActivity {
    private static final int FLAG = 0x1;
    private int mFlags = 0;
    @BindView(R.id.tv_flag)
    TextView mTvFlag;

    @Override
    protected int getLayout() {
        return R.layout.activity_flag;
    }

    @Override
    protected void initInject() {}

    @Override
    protected void initView() {
        super.initView();
        initToolBar(mToolbar, true, "Flag");
        mTvFlag.setText("" + mFlags);
    }

    public void addFlag(View view) {
        if (containsFlag(view)) {
            showInfo("已经包含FLAG!");
        } else {
            mFlags |= FLAG;
        }
        mTvFlag.setText("" + mFlags);
    }

    public void clearFlag(View view) {
        if (containsFlag(view)) {
            mFlags &= ~FLAG;
        } else {
            showInfo("没有包含FlAG!");
        }
        mTvFlag.setText("" + mFlags);
    }

    public boolean containsFlag(View view) {
        return (mFlags & FLAG) == FLAG;
    }

    public boolean notContainsFlag(View view) {
        return (mFlags & FLAG) != FLAG;
    }
}
