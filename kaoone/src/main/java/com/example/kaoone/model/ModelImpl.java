package com.example.kaoone.model;

import com.example.kaoone.network.MyCallBack;
import com.example.kaoone.network.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

public class ModelImpl implements Model{
    @Override
    public void sendMessage( String getCatagory, final Class clazz, Map<String, String> map, final MyCallBack myCallBack) {
        RetrofitManager.getManager().post(getCatagory,map).result(new RetrofitManager.HttpListener() {
            @Override
            public void success(String string) {
                Object o = new Gson().fromJson(string, clazz);
                myCallBack.success(o);
            }

            @Override
            public void fail(String message) {
                myCallBack.fail(message);
            }
        });
    }
}
