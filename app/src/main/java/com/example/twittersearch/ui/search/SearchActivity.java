package com.example.twittersearch.ui.search;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.example.twittersearch.R;
import com.example.twittersearch.common.Constants;
import com.example.twittersearch.databinding.ActivitySearchBinding;
import com.example.twittersearch.managers.FragmentTransactionManager;
import com.example.twittersearch.ui.base.BaseActivity;
import com.example.twittersearch.ui.base.FragmentInteractionCallback;

public class SearchActivity extends BaseActivity<ActivitySearchBinding> implements
        FragmentInteractionCallback,
        FragmentManager.OnBackStackChangedListener {

    private int backStackCounter = 0;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        FragmentTransactionManager.replaceFragment(getSupportFragmentManager(), R.id.frame_layout, SearchFragment.newInstance());
    }

    @Override
    public void onBackStackChanged() {
        int countBackStack = getSupportFragmentManager().getBackStackEntryCount() - 1;
        if (countBackStack >= 0) backStackCounter = countBackStack;
    }

    @Override
    public void onBackPressed() {
        if (backStackCounter > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        getSupportFragmentManager().removeOnBackStackChangedListener(this);
        super.onDestroy();
    }

    @Override
    public void onFragmentInteractionCallback(Bundle bundle) {
        String action = bundle.getString(Constants.ACTION);
    }
}
