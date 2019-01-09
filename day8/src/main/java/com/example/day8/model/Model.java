package com.example.day8.model;

import com.example.day8.netWork.MyCallBack;

import java.util.Map;

public interface Model {
    void sendMessage(String s, Map<String,String> map, Class clazz, MyCallBack myCallBack);
}
