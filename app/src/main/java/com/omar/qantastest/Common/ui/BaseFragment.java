package com.omar.qantastest.Common.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by omz on 23/5/18
 */
public abstract class BaseFragment extends Fragment {

    // A base fragment that all fragments can extend. It can be used to hold common code shared across all fragments
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
