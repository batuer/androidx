package com.gusi.androidx.module;

import com.gusi.androidx.base.IBasePresenter;
import com.gusi.androidx.base.IBaseView;

/**
 * @author Ylw
 * @since 2019/5/30 23:56
 */
public interface MainContract {
    interface View extends IBaseView {

    }

    interface Presenter extends IBasePresenter<View> {

    }
}
