package com.example.twittersearch.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.twittersearch.common.App;
import com.example.twittersearch.di.component.DaggerFragmentComponent;
import com.example.twittersearch.di.component.FragmentComponent;
import com.example.twittersearch.di.module.FragmentModule;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<DB extends ViewDataBinding> extends Fragment {
    protected DB binder;
    protected FragmentInteractionCallback fragmentInteractionCallback;
    private FragmentComponent fragmentComponent;

    @LayoutRes
    public abstract int getLayoutRes();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        return binder.getRoot();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            fragmentInteractionCallback = (FragmentInteractionCallback) context;
        } catch (ClassCastException e) {
            throw new RuntimeException(context.toString() + " must implement " + FragmentInteractionCallback.class.getName());
        }
    }

    protected FragmentComponent getFragmentComponent() {
        if (fragmentComponent == null) {
            fragmentComponent = DaggerFragmentComponent
                    .builder()
                    .appComponent(App.get(getContext()).getAppComponent())
                    .build();
        }
        return fragmentComponent;
    }
}
