package com.gusi.androidx.module.areastate;

import androidx.core.util.Pair;

import com.gusi.androidx.base.IBasePresenter;
import com.gusi.androidx.base.IBaseView;

import java.util.List;

/**
 * @author Ylw
 * @since 2019/5/30 23:56
 */
public interface AreaContract {
    interface View extends IBaseView {
        void showRegistration(List<Pair<String, String>> pairList);
    }

    interface Presenter extends IBasePresenter<View> {
        void getRegistration(String areaUrl);
    }
}
