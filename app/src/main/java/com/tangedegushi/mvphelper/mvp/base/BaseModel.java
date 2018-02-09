package com.tangedegushi.mvphelper.mvp.base;

import android.support.annotation.IntDef;

import com.tangedegushi.mvphelper.global.api.ApiServiceManager;
import com.tangedegushi.mvphelper.mvp.IModel;

import org.reactivestreams.Subscriber;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zq on 2017/8/26.
 */

public abstract class BaseModel implements IModel {

    @IntDef({RED,YELLOW,BLUE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LightColor{}
    public static final int RED = 1;
    public static final int YELLOW = 2;
    public static final int BLUE = 3;
    public void setColor(@LightColor int color){

    };

    @Override
    public void loadData(Observer subscriber){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {

            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {

            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
        ApiServiceManager.getInstance()
                .getApiService()
                .getNews("top","509e1319694893a9df49d9b6ea7b2ed0")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }
}
