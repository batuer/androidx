package com.gusi.androidx.module.areastate;

import com.gusi.androidx.base.IBasePresenter;
import com.gusi.androidx.base.IBaseView;

/**
 * @author Ylw
 * @since 2019/7/21 21:06
 */
public interface AreaStateContract {
    interface View extends IBaseView {
//        void showProjects();
    }

    interface Presenter extends IBasePresenter<View> {
        void getProjects(String url);
    }
}
