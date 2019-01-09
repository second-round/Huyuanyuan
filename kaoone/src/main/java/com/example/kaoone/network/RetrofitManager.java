package com.example.kaoone.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager {
    private static RetrofitManager manager;
    private String base_api="http://www.zhaoapi.cn/product/";
    public static RetrofitManager getManager() {
        if (manager==null){
            manager=new RetrofitManager();
        }
        return manager;
    }
    private BaseApi baseApi;
    public RetrofitManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient build = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(base_api)
                .client(build)
                .build();
        baseApi=retrofit.create(BaseApi.class);
    }

    public RetrofitManager post(String url, Map<String,String> map){
        if (map==null){
            map=new HashMap<>();
        }
        Subscription subscribe = baseApi.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        return manager;
    }

    private Observer observer=new Observer<ResponseBody>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String string = responseBody.string();
                if (listener!=null){
                    listener.success(string);
                }
                
                
            } catch (IOException e) {
                e.printStackTrace();
                if (listener!=null){
                    listener.fail(e.getMessage());
                }
            }
        }

    };



    public HttpListener listener;

    public void result(HttpListener listener){
        this.listener=listener;
    }

    public interface HttpListener{

        void success(String string);

        void fail(String message);
    }

}
