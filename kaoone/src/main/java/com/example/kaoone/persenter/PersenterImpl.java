package com.example.kaoone.persenter;

import com.example.kaoone.model.ModelImpl;
import com.example.kaoone.network.MyCallBack;
import com.example.kaoone.userbean.TwoBean;
import com.example.kaoone.view.IView;

import java.util.Map;

public class PersenterImpl implements Persenter{
    private ModelImpl model;
    private IView iView;

    public PersenterImpl(IView iView) {
        this.iView = iView;
        model=new ModelImpl();
    }

    @Override
    public void sendMessage(String getCatagory, Class clazz, Map<String, String> map) {
        model.sendMessage(getCatagory, clazz, map, new MyCallBack() {
            @Override
            public void success(Object o) {
                iView.data(o);
            }

            @Override
            public void fail(String message) {

            }
        });
    }
}
