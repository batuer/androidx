package com.gusi.androidx.base;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author ylw   2018/6/21 17:34
 * @Des
 */
public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    /**
     * View层
     **/
    protected V mView;
    /**
     * rx统一管理,防止内存泄漏
     **/
    private CompositeDisposable compositeDisposable;

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        unSubscribe();
    }


    public void unSubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void showInfo(CharSequence info) {
        mView.showInfo(info);
    }

    public void showLoading(CharSequence msg, boolean canCancel) {
        mView.showLoading(msg, canCancel);
    }

    public void showError(CharSequence error) {
        mView.showError(error);
    }

    public void showLoading(CharSequence msg) {
        showLoading(msg, false);
    }

    public void hideLoading() {
        mView.hideLoading();
    }
}
