package com.example.day7.model;

import com.example.day7.netWork.MyCallBack;

import java.util.Map;

public interface Model {
    void sendMessage(String url, Map<String,String> map, Class clazz, MyCallBack myCallBack);
}
