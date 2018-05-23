package com.omar.qantastest.Common.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by omz on 23/5/18
 */
public abstract class BaseActivity extends AppCompatActivity {

    // A Base activity that all activities can extend. It can be used to hold common code shared across all activities
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewReference());
    }

    abstract public int getContentViewReference();

}
