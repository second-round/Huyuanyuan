package com.example.day8.model;

import com.example.day8.netWork.MyCallBack;
import com.example.day8.netWork.RetrolManager;
import com.google.gson.Gson;

import java.util.Map;

public class ModelImpl implements Model{
    @Override
    public void sendMessage(String s, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrolManager.getInstence().post(s,map).result(new RetrolManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                if (myCallBack!=null){
                    myCallBack.success(o);
                }
            }
            @Override
            public void onFail(String error) {
                if (myCallBack!=null){
                    myCallBack.fail(error);
                }
            }
        });
    }
}
