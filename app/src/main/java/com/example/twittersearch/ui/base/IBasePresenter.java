package com.example.twittersearch.ui.base;

public interface IBasePresenter<V extends BaseView, I extends IBaseInteractor> {
    void onAttach(V view);

    void onDetach();

    V getView();

    I getInteractor();

    boolean isViewAttached();
}
