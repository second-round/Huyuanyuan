package com.example.moni2.model;

import com.example.moni2.callback.MyCallBack;

import java.util.Map;

public interface Model {
    void requestData(String url, Map<String, String> params, Class clazz, MyCallBack callBack);
}
