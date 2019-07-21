package com.gusi.androidx.module.areastate;

import com.gusi.androidx.base.BasePresenter;
import com.gusi.androidx.model.DataManager;
import com.gusi.androidx.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * @author Ylw
 * @since 2019/7/21 21:06
 */
public class AreaStatePresenter extends BasePresenter<AreaStateContract.View> implements AreaStateContract.Presenter {
    private DataManager mDataManager;

    @Inject
    public AreaStatePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getProjects(String url) {
        showLoading();
        addDisposable(
            mDataManager.getProjects(url).compose(RxUtil.rxSchedulerFlowable()).subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Exception {
                    hideLoading();
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    hideLoading();
                }
            }));
    }
}
