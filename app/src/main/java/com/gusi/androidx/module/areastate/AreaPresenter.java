package com.gusi.androidx.module.areastate;

import android.util.Log;

import com.gusi.androidx.base.BasePresenter;
import com.gusi.androidx.model.DataManager;
import com.gusi.androidx.util.RxUtil;

import javax.inject.Inject;

/**
 * @author Ylw
 * @since 2019/5/30 23:56
 */
public class AreaPresenter extends BasePresenter<AreaContract.View> implements AreaContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public AreaPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getRegistration(String areaUrl) {
        showLoading("Loading");
        addDisposable(mDataManager.getRegistration(areaUrl).compose(RxUtil.rxSchedulerFlowable()).subscribe(pairs -> {
            hideLoading();
            mView.showRegistration(pairs);
        }, throwable -> {
            hideLoading();
            Log.e("Fire", "AreaPresenter:30行:" + throwable.toString());
        }));
    }
}
