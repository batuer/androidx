package com.gusi.androidx.module.qqlogin;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseActivity;
import com.gusi.androidx.module.EasyPermission;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class QQLoginActivity extends BaseActivity {
    private static final String TAG = "Fire";
    private static final String APPID = "1109728521";

    @BindView(R.id.iv_figure)
    ImageView mIvFigure;
    @BindView(R.id.tv_nickname)
    TextView mTvNick;

    private Tencent mTencent;
    private QQLoginListener mListener;
    private UserInfo mInfo;

    @Override
    protected int getLayout() {
        return R.layout.activity_qqlogin;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        EasyPermission.hasAllPermission();
        mListener = new QQLoginListener();
        // 实例化Tencent
        if (mTencent == null) {
            mTencent = Tencent.createInstance(APPID, this);
        }
    }

    public void logout(View view) {
        if (mTencent.isSessionValid()) {
            mTencent.logout(QQLoginActivity.this);
            mIvFigure.setImageResource(R.mipmap.ic_launcher_round);
            mTvNick.setText("QQ 昵称");
        }
    }

    public void login(View view) {
        if (!EasyPermission.hasAllPermission()) {
            return;
        }
        // 如果session不可用，则登录，否则说明已经登录
        mTencent.login(this, "all", mListener);
        if (!mTencent.isSessionValid()) {
        }
    }

    private class QQLoginListener implements IUiListener {
        @Override
        public void onComplete(Object object) { // 登录成功
            // 获取openid和token
            initOpenIdAndToken(object);
            // 获取用户信息
            getUserInfo();
        }

        @Override
        public void onError(UiError uiError) { // 登录失败
            Log.w("Fire", "QQLoginListener:87行:" + uiError.toString());
        }

        @Override
        public void onCancel() { // 取消登录
        }
    }

    private void initOpenIdAndToken(Object object) {
        JSONObject jsonObject = (JSONObject)object;
        try {
            // openid用户唯一标识
            String openID = jsonObject.getString("openid");
            String access_token = jsonObject.getString("access_token");
            String expires = jsonObject.getString("expires_in");

            mTencent.setOpenId(openID);
            mTencent.setAccessToken(access_token, expires);
        } catch (JSONException e) {
            Log.e("Fire", "QQLoginActivity:125行:" + e.toString());
        }
    }

    private void getUserInfo() {
        QQToken token = mTencent.getQQToken();
        mInfo = new UserInfo(QQLoginActivity.this, token);
        mInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object object) {
                JSONObject jsonObject = (JSONObject)object;
                try {
                    String name = jsonObject.getString("nickname");
                    String figureurl = jsonObject.getString("figureurl_qq_2");
                    mTvNick.setText(name);
                    Log.w("Fire", "QQLoginActivity:121行:" + figureurl);
                    Glide.with(QQLoginActivity.this).load(figureurl).centerCrop().error(R.mipmap.applogo)
                        .placeholder(R.mipmap.ic_attendanced).into(mIvFigure);
                } catch (Exception e) {
                    Log.e("Fire", "QQLoginActivity:142行:" + e.toString());
                }
            }

            @Override
            public void onError(UiError uiError) {}

            @Override
            public void onCancel() {}
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTencent.onActivityResultData(requestCode, resultCode, data, mListener);
    }
}
