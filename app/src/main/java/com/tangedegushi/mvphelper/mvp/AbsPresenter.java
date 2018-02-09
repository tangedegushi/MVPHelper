package com.tangedegushi.mvphelper.mvp;

/**
 * Created by zq on 2017/8/26.
 */

public abstract class AbsPresenter<V extends IView> implements IPresenter {

    private V iView;

    public void attachView(V iView){
        this.iView = iView;
    }

    public void detachView(){
        iView = null;
    }

    public V getView(){
        return iView;
    }

}
