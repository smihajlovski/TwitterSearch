package com.example.twittersearch.managers;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentTransactionManager {

    public static void replaceFragment(FragmentManager fragmentManager, int layoutId, Fragment fragment) {
        fragmentManager
                .beginTransaction()
                .replace(layoutId, fragment, fragment.getTag())
                .addToBackStack(null)
                .commit();
    }
}
