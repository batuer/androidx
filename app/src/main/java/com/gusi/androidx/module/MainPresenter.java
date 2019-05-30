package com.gusi.androidx.module;

import com.gusi.androidx.base.BasePresenter;
import com.gusi.androidx.model.DataManager;

import javax.inject.Inject;

/**
 * @author Ylw
 * @since 2019/5/30 23:56
 */
public class MainPresenter extends BasePresenter<MainContract.View> {

    private DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }
}
