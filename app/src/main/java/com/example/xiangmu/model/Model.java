package com.example.xiangmu.model;

import com.example.xiangmu.callback.MyCallBack;

import java.util.Map;

public interface Model {
    void sendMessage(String path, Map<String,String> map, Class clazz, MyCallBack myCallBack);
    void requestDataGet(String path, final Class clazz, final MyCallBack myCallBack);

    void requestPut(String tobushop, Map<String,String> map, Class clazz, MyCallBack myCallBack);

    void requestDelete(String quxiao, Map<String, String> map, Class clazz, MyCallBack myCallBack);
}
