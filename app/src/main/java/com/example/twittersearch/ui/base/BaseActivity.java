package com.example.twittersearch.ui.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.twittersearch.common.App;
import com.example.twittersearch.di.component.ActivityComponent;
import com.example.twittersearch.di.component.DaggerActivityComponent;
import com.example.twittersearch.di.module.ActivityModule;

public abstract class BaseActivity<DB extends ViewDataBinding> extends AppCompatActivity {

    protected DB binder;
    private ActivityComponent activityComponent;

    @LayoutRes
    public abstract int getLayoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, getLayoutRes());
    }

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent
                    .builder()
                    .appComponent(App.get(this).getAppComponent())
                    .build();
        }

        return activityComponent;
    }
}
