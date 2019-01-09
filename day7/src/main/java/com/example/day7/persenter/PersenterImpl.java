package com.example.day7.persenter;

import com.example.day7.UserBean;
import com.example.day7.ivew.IView;
import com.example.day7.model.ModelImpl;
import com.example.day7.netWork.MyCallBack;

import java.util.Map;

public class PersenterImpl implements Persenter{
    private IView iView;
    private ModelImpl model;

    public PersenterImpl(IView iView) {
        this.iView = iView;
        model=new ModelImpl();
    }

    @Override
    public void sendMessage(String url, Map<String, String> map, Class clazz) {
        model.sendMessage(url,map,clazz, new MyCallBack() {
            @Override
            public void success(Object data) {
                iView.getData(data);
            }

            @Override
            public void fail(Exception e) {

            }
        });
    }
}
