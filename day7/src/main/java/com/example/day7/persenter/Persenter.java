package com.example.day7.persenter;

import com.example.day7.UserBean;

import java.util.Map;

public interface Persenter {
    void sendMessage(String url, Map<String,String> map, Class clazz);
}
