package com.example.twittersearch.di.module;

import android.content.Context;

import androidx.fragment.app.Fragment;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    Fragment provideFragment() {
        return fragment;
    }

    @Provides
    Context provideContext() {
        return fragment.getContext();
    }
}
