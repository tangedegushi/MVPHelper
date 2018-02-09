package com.tangedegushi.mvphelper;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.tangedegushi.mvphelper.global.MyApplication;
import com.tangedegushi.mvphelper.mvp.base.BaseActivity;
import com.tangedegushi.mvphelper.mvp.base.BaseModel;
import com.tangedegushi.mvphelper.global.util.AppManager;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainActivity extends BaseActivity {

    @BindView(R2.id.netconnectstatus)
    TextView netconnectstatus;
    @BindView(R2.id.netstatus)
    TextView netstatus;

    private Disposable networkDisposable;
    private Disposable internetDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseModel baseModel = new BaseModel(){

            @Override
            public void onDestroy() {

            }
        };
        baseModel.loadData(new Observer<NewsItem>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                Logger.d("onSubscribe");
            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull NewsItem adsEntity) {
                Logger.d(adsEntity.getResult().getData());
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Logger.d("onError");
            }

            @Override
            public void onComplete() {
                Logger.d("onComplete");
            }
        });
        Observable.create(new ObservableOnSubscribe<NewsItem>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<NewsItem> e) throws Exception {

            }
        }).map(new Function<NewsItem, Object>() {
            @Override
            public Object apply(@io.reactivex.annotations.NonNull NewsItem newsItem) throws Exception {
                return null;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @NonNull
    @Override
    public int setResLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    long firstTime;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            //注销声明周期管理
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                MyApplication.getInstence().unRegisterActivityLifecycleCallbacks();
            }
            AppManager.newInstance().appExit(getApplicationContext());
        }
    }
}
