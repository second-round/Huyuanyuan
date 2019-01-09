package com.example.kaoone.persenter;

import com.example.kaoone.userbean.TwoBean;

import java.util.Map;

public interface Persenter {
    void sendMessage( String getCatagory, Class clazz, Map<String,String> map);
}
