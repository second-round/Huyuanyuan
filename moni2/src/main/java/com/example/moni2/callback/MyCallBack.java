package com.example.moni2.callback;

public interface MyCallBack<T> {
    void onSuccess(T data);
    void onFail(String error);
}