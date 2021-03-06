package com.example.xiangmu.netWork;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.xiangmu.app.App;
import com.example.xiangmu.callback.MyCallBack;
import com.example.xiangmu.util.Constant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//Retrofit设计精妙，代码简洁，使用方便

//Retrofit的作用是按照接口去定制Call网络工作对象
//Retrofit并不做网络请求，只是生成一个能做网络请求的对象。

//Retrofit做的事情，就是为开发者节省这部分的工作量，
// Retrofit一方面从底层统一用OkHttp去做网络处理；
// 另一方面在外层灵活提供能直接融入业务逻辑的Call网络访问对象

//具体来说，Retrofit只负责生产对象，生产能做网络请求的工作对象，
// 他有点像一个工厂，只提供产品，工厂本身不处理网络请求，产品才能处理网络请求。



//1.Interceptor拦截器


//Retrofit实现原理——函数解析、网络请求和数据转换





public class RetrofitManager<T> {
    public static RetrofitManager retrofitManager;

    public static synchronized RetrofitManager getInstance(){
        if(retrofitManager==null){
            retrofitManager=new RetrofitManager();
        }
        return retrofitManager;
    }

    private BaseApis mBaseApis;

    public RetrofitManager(){
        OkHttpClient builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        SharedPreferences preferences = App.getApplication().getSharedPreferences("User", Context.MODE_PRIVATE);
                        String userId = preferences.getString("userId", "");
                        String sessionId = preferences.getString("sessionId", "");
                        Request.Builder builder1 = request.newBuilder();
                        builder1.method(request.method(),request.body());
                        if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(sessionId)){
                            builder1.addHeader("userId",userId);
                            builder1.addHeader("sessionId",sessionId);
                        }
                        Request build = builder1.build();
                        return chain.proceed(build);
                    }
                })
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

                Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constant.PATH)
                .client(builder)
                .build();

        mBaseApis = retrofit.create(BaseApis.class);
    }
    public Map<String, RequestBody> generateRequestBody(Map<String,String> requestDataMap){

        HashMap<String, RequestBody> requestBodyHashMap = new HashMap<>();

        for (String key:requestDataMap.keySet()){
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyHashMap.put(key,requestBody);
        }
        return requestBodyHashMap;
    }

    public void get(String url,HttpListener listener){
        mBaseApis.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    public void post(String dataUrl,Map<String,String> map,HttpListener listener){
        if(map==null){
            map=new HashMap<>();
        }
        mBaseApis.post(dataUrl,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    public void put(String dataUrl,Map<String,String> map,HttpListener listener){
        if(map==null){
            map=new HashMap<>();
        }
        mBaseApis.put(dataUrl,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }



    public void delete(String quxiao, Map<String, String> map, HttpListener listener) {
        if(map==null){
            map=new HashMap<>();
        }
        mBaseApis.delete(quxiao,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }


    public RetrofitManager postFormBoby(String dataUrl,Map<String,RequestBody> map,HttpListener listener){
        if(map==null){
            map=new HashMap<>();
        }
        mBaseApis.postFormBody(dataUrl,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
        return retrofitManager;
    }

    private Observer getObserver(final HttpListener listener) {
        Observer observer = new Observer<ResponseBody>() {

            //观察者
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.onFail(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data = responseBody.string();
                    if (listener != null) {
                        listener.onSuccess(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onFail(e.getMessage());
                    }
                }
            }
        };
        return observer;
    }
    public interface HttpListener {
        void onSuccess(String data);
        void onFail(String error);
    }
}
