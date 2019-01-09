package com.example.moni2.model;

import com.example.moni2.callback.MyCallBack;
import com.example.moni2.netWork.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.RequestBody;

public class ModelImpl implements Model{
    @Override
    public void requestData(String url, Map<String, String> params, final Class clazz, final MyCallBack callBack) {
        Map<String, RequestBody> map = RetrofitManager.getInstance().generateRequestBody(params);
        RetrofitManager.getInstance().postFormBody(url, map).result(new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    Object o = new Gson().fromJson(data, clazz);
                    if(callBack != null){
                        callBack.onSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(callBack != null){
                        callBack.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                if(callBack != null){
                    callBack.onFail(error);
                }
            }
        });

    }
}
