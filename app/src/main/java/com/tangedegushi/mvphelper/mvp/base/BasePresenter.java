package com.tangedegushi.mvphelper.mvp.base;

import com.tangedegushi.mvphelper.mvp.AbsPresenter;
import com.tangedegushi.mvphelper.mvp.IModel;

/**
 * Created by zq on 2017/8/18.
 */

public class BasePresenter<M extends IModel> extends AbsPresenter {

    private M iModel;

    public BasePresenter() {
        onStart();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void detachView() {
        if (iModel != null)
            iModel.onDestroy();
        iModel = null;
    }
}
