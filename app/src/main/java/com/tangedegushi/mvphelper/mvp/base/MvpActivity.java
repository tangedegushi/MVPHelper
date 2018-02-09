package com.tangedegushi.mvphelper.mvp.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tangedegushi.mvphelper.global.util.ShowAndSpUtil;
import com.tangedegushi.mvphelper.mvp.AbsPresenter;
import com.tangedegushi.mvphelper.mvp.IView;

/**
 * Created by zq on 2017/8/26.
 */

public abstract class MvpActivity<P extends AbsPresenter<IView>> extends BaseActivity implements IView{

    public P mPresenter;
    private ProgressDialog mProgressDialog;

    @NonNull
    public abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (mPresenter == null){
            mPresenter = createPresenter();
            mPresenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    //--------------------------显示相关--------------------------//

    @Override
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    @Override
    public void showToast(String msg) {
        if(!isFinishing()){
            ShowAndSpUtil.showToast(msg);
        }
    }

    @Override
    public void showProgress(boolean cancelable, String message) {
        if (mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(cancelable);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage(message);
        }
    }

    @Override
    public void showProgress(String message) {
        showProgress(true,message);
    }

    @Override
    public void showProgress() {
        showProgress(true,"");
    }

    @Override
    public void showProgress(boolean cancelable) {
        showProgress(cancelable,"");
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog == null)
            return;
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
