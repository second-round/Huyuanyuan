package com.example.kaoone.model;

import com.example.kaoone.network.MyCallBack;

import java.util.Map;

public interface Model {
    void sendMessage(String getCatagory, Class clazz, Map<String,String> map, MyCallBack myCallBack);
}
