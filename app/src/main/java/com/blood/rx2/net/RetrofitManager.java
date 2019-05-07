package com.blood.rx2.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    // 接口根地址
    private static final String BASE_URL = "http://fy.iciba.com/";
    // 设置超时时间
    private static final long DEFAULT_TIMEOUT = 3_000L;

    private Retrofit retrofit;
    private OkHttpClient client;

    private RetrofitManager() {
        init();
    }

    private static class SingletonHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private void init() {
        client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new LoggerInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <T> T createService(Class<T> clazz) {
        return retrofit.create(clazz);
    }

}
