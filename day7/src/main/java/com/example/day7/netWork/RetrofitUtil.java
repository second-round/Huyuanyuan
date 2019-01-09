package com.example.day7.netWork;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitUtil {
    private static RetrofitUtil instence;
    private String BASE_URL="http://www.zhaoapi.cn/product/getProducts";
    public static RetrofitUtil getInstence() {
        if (instence==null){
            instence=new RetrofitUtil();
        }
        return instence;
    }
    private BaseApis baseApis;

    private RetrofitUtil() {
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient build = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(build)
                .build();
        baseApis=retrofit.create(BaseApis.class);
    }
    public RetrofitUtil post(String url, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        baseApis.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return instence;
    }
    public Map<String, RequestBody> generateRequestBody(Map<String, String> requestDataMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }
    private Observer observer = new Observer<ResponseBody>() {
        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String data = responseBody.string();
                if (listener != null) {
                    listener.success(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.falis(e.getMessage());
                }
            }
        }

        @Override
        public void onError(Throwable e) {
            if (listener != null) {
                listener.falis(e.getMessage());
            }
        }

        @Override
        public void onCompleted() {

        }
    };
    private RetrofitListener listener;



    public interface RetrofitListener{
        void success(String s);
        void falis(String e);
    }
    public void result(RetrofitListener listener) {
        this.listener = listener;
    }

}
