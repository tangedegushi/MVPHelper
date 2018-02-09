package com.tangedegushi.mvphelper.mvp;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by zq on 2017/8/18.
 */

public interface IModel {

    //加载数据
    void loadData(Observer observer);

    void onDestroy();
}
