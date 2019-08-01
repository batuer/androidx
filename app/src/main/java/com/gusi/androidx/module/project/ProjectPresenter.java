package com.gusi.androidx.module.project;

import android.util.Log;

import com.gusi.androidx.base.BasePresenter;
import com.gusi.androidx.model.DataManager;
import com.gusi.androidx.util.RxUtil;

import javax.inject.Inject;

/**
 * @author Ylw
 * @since 2019/7/21 21:06
 */
public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {
    private DataManager mDataManager;

    @Inject
    public ProjectPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getProjects(String url, boolean isRefresh) {
        Log.w("Fire", "ProjectPresenter:23行:" + url);
        showLoading();
        addDisposable(mDataManager.getProjects(url).compose(RxUtil.rxSchedulerFlowable()).subscribe(projects -> {
            mView.showProjects(projects, isRefresh);
            hideLoading();
        }, throwable -> hideLoading()));
    }
}
