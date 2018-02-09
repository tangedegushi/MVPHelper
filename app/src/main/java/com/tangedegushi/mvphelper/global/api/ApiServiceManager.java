package com.tangedegushi.mvphelper.global.api;

/**
 * Created by zq on 2017/8/26.
 */

public class ApiServiceManager {

    private static volatile ApiServiceManager instance = null;

    // 所有的接口都定义在这里
    private ApiService apiService;
//    public HotelService hotelService;
    //...

    public static ApiServiceManager getInstance() {
        if (instance == null) {
            synchronized (ApiServiceManager.class) {
                if (instance == null) {
                    instance = new ApiServiceManager();
                }
            }
        }
        return instance;
    }

    // 为每一个接口服务定义获取接口服务的方法

    public ApiService getApiService() {
        if (apiService == null) {
            apiService = RetrofitManager.getInstance()
                    .getRetrofit()
                    .create(ApiService.class);
        }
        return apiService;
    }

}
