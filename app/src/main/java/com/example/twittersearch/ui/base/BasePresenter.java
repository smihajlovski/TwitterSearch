package com.example.twittersearch.ui.base;

import com.example.twittersearch.managers.SchedulerProviderManager;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends BaseView, I extends IBaseInteractor> implements IBasePresenter<V, I> {

    private final SchedulerProviderManager schedulerProviderManager;
    private final CompositeDisposable compositeDisposable;
    private V baseView;
    private I baseInteractor;

    @Inject
    public BasePresenter(I baseInteractor,
                         SchedulerProviderManager schedulerProviderManager,
                         CompositeDisposable compositeDisposable) {
        this.baseInteractor = baseInteractor;
        this.schedulerProviderManager = schedulerProviderManager;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(V view) {
        this.baseView = view;
    }

    @Override
    public void onDetach() {
        this.compositeDisposable.dispose();
        this.baseView = null;
    }

    @Override
    public V getView() {
        return baseView;
    }

    @Override
    public I getInteractor() {
        return baseInteractor;
    }

    @Override
    public boolean isViewAttached() {
        return baseView != null;
    }

    protected SchedulerProviderManager getSchedulerProviderManager() {
        return schedulerProviderManager;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}
