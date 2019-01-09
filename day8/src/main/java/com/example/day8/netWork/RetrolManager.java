package com.example.day8.netWork;

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

public class RetrolManager {
    private String Base_URL="http://www.xieast.com/api/";
    private static RetrolManager instence;

    public static RetrolManager getInstence() {
        if (instence==null){
            instence=new RetrolManager();
        }
        return instence;
    }
    private BaseApis baseApis;
    public RetrolManager() {
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient build = new OkHttpClient.Builder()
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(build)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Base_URL)
                .build();
        baseApis=retrofit.create(BaseApis.class);
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
    public RetrolManager post(String url,Map<String,String> map){
        if (map==null){
            map=new HashMap<>();
        }
        baseApis.post(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return instence;
    }
    Observer observer= new Observer<ResponseBody>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if (listener!=null){
                listener.onFail(e.getMessage());
            }
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String string = responseBody.string();
                if (listener!=null){
                    listener.onSuccess(string);
                }


            } catch (IOException e) {
                e.printStackTrace();
                if (listener!=null){
                    listener.onFail(e.getMessage());
                }
            }
        }
    };

    private HttpListener listener;

    public void result(HttpListener listener) {
        this.listener = listener;
    }

    public interface HttpListener {
        void onSuccess(String data);

        void onFail(String error);
    }
}
