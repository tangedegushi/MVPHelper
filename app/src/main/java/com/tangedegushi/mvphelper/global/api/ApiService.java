package com.tangedegushi.mvphelper.global.api;

import com.tangedegushi.mvphelper.NewsItem;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zq on 2017/8/26.
 */

public interface ApiService {

    @GET("toutiao/index")
    Observable<NewsItem> getNews(@Query("type") String newstype, @Query("key") String appkey);

}
