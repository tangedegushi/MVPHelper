package com.tangedegushi.mvphelper.global.api;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.tangedegushi.mvphelper.BuildConfig;
import com.tangedegushi.mvphelper.global.MyApplication;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zq on 2017/8/25.
 */

public class RetrofitManager {

    private static final String BASE_URL = "http://v.juhe.cn/";

    private static volatile RetrofitManager instance = null;
    private static final long CASE_DISK_SIZE = 50*1024 * 1024; // 1 MB
    private Gson gson;
    private Retrofit retrofit;
    private static final String DEFAULT_CACHE = "_Cache";
    private OkHttpClient okHttpClient;
    private TreeMap<String, Retrofit> hashMap = new TreeMap<>();

    private RetrofitManager(){
        initGson();
        initOkhttp3(MyApplication.getInstence().getApplicationContext());
    }

    public static RetrofitManager getInstance(){
        if (instance == null){
            synchronized (RetrofitManager.class){
                if (instance == null) {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    private void initGson(){
        gson = new GsonBuilder().serializeNulls().create();
    }

    private void initOkhttp3(final Context context){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //打印请求log日志
        if (BuildConfig.DEBUG) {
            //HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            //loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //builder.addInterceptor(loggingInterceptor);
        }
        //设置缓存 默认开启
        File cacheFile = new File(context.getExternalCacheDir(), DEFAULT_CACHE);
        Cache cache = new Cache(cacheFile, CASE_DISK_SIZE);
        builder.cache(cache);

        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);

        // 设置连接池
        builder.connectionPool(new ConnectionPool(10, 10, TimeUnit.MINUTES));

        okHttpClient = builder.build();
    }

    public Retrofit getRetrofit(String baseUrl) {
        String url = !TextUtils.isEmpty(baseUrl) ? baseUrl : BASE_URL;
        retrofit = hashMap.get(url);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            hashMap.put(url, retrofit);
        }
        return retrofit;
    }

    public Retrofit getRetrofit() {
        return this.getRetrofit(null);
    }

}
