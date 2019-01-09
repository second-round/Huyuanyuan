package com.example.day8.persenter;

import com.example.day8.bean.UserBean;

import java.util.Map;

public interface Persenter {
    void sendData(String s, Map<String,String> map,Class clazz);
}
