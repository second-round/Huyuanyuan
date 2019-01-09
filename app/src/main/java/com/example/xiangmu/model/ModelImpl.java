package com.example.xiangmu.model;

import com.example.xiangmu.callback.MyCallBack;
import com.example.xiangmu.netWork.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

public class ModelImpl implements Model{


    @Override
    public void sendMessage(String path, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().post(path,map).post(path,map).result(new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallBack.CallBack(o);
            }

            @Override
            public void onFail(String error) {
            }
        });
    }
    @Override
    public void requestDataGet(String path, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().get(path).result(new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallBack.CallBack(o);
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

    @Override
    public void requestPut(String tobushop, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().put(tobushop,map).result(new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallBack.CallBack(o);
            }

            @Override
            public void onFail(String error) {
            }
        });
    }

}
