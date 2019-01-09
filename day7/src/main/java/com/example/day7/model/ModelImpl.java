package com.example.day7.model;

import com.example.day7.netWork.MyCallBack;
import com.example.day7.netWork.RetrofitUtil;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.RequestBody;

public class ModelImpl implements Model {
    @Override
    public void sendMessage(String url, Map<String, String> maps, final Class clazz, final MyCallBack myCallBack) {
        Map<String, RequestBody> map = RetrofitUtil.getInstence().generateRequestBody(maps);
        RetrofitUtil.getInstence().post(url,maps).result(new RetrofitUtil.RetrofitListener() {
            @Override
            public void success(String s) {
                try{
                    Object o = new Gson().fromJson(s, clazz);
                    if(myCallBack != null){
                        myCallBack.success(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(myCallBack != null){
                        myCallBack.fail(e);
                    }
                }
            }

            @Override
            public void falis(String e) {

            }
        });

    }
}
