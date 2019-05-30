package com.gusi.androidx.base;

/**
 * @Author ylw  2018/7/20 21:56
 */
public interface IBasePresenter<V extends IBaseView> {
    /**
     * attach view
     *
     * @param v
     */
    void attachView(V v);

    /**
     * detach view
     */
    void detachView();
}
