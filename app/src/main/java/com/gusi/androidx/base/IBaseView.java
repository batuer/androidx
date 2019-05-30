package com.gusi.androidx.base;

/**
 * @author ylw   2018/6/21 17:33
 * @Des
 */
public interface IBaseView {
    /**
     * 提示基本信息
     *
     * @param info 信息内容
     */
    void showInfo(CharSequence info);

    /**
     * 提示错误
     *
     * @param error 错误提示
     */
    void showError(CharSequence error);

    /**
     * show loading
     *
     * @param msg       msg
     * @param canCancel can cancel
     */
    void showLoading(CharSequence msg, boolean canCancel);

    /**
     * 隐藏loading信息
     */
    void hideLoading();
}
