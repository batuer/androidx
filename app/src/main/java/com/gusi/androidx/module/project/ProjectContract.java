package com.gusi.androidx.module.project;

import com.gusi.androidx.base.IBasePresenter;
import com.gusi.androidx.base.IBaseView;
import com.gusi.androidx.model.entity.Project;

import java.util.List;

/**
 * @author Ylw
 * @since 2019/7/21 21:06
 */
public interface ProjectContract {
    interface View extends IBaseView {
        void showProjects(List<Project> projects,boolean isRefresh);
    }

    interface Presenter extends IBasePresenter<View> {
        void getProjects(String url, boolean isRefresh);
    }
}
