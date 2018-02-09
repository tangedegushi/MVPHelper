package com.tangedegushi.mvphelper.mvp.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tangedegushi.mvphelper.global.util.AppManager;

import butterknife.ButterKnife;

/**
 * Created by zq on 2017/8/19.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            AppManager.newInstance().addActivity(this);
        }
        setContentView(setResLayout());
        if (savedInstanceState == null) {
            ButterKnife.bind(this);
        }
        initView();
        initData();
    }

    @NonNull
    public abstract int setResLayout();

    public abstract void initView();

    public abstract void initData();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.newInstance().finishActivity(this);
    }
}
