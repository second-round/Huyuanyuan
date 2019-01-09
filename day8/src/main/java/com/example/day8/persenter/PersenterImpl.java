package com.example.day8.persenter;

import com.example.day8.bean.UserBean;
import com.example.day8.model.Model;
import com.example.day8.model.ModelImpl;
import com.example.day8.netWork.MyCallBack;
import com.example.day8.view.IView;

import java.util.Map;

public class PersenterImpl implements Persenter{
    private IView iView;
    private ModelImpl model;

    public PersenterImpl(IView iView) {
        this.iView = iView;
        model=new ModelImpl();
    }


    @Override
    public void sendData(String s, Map<String, String> map, Class clazz) {
        model.sendMessage(s, map, clazz, new MyCallBack() {
            @Override
            public void success(Object o) {
                iView.setData(o);
            }

            @Override
            public void fail(String e) {

            }
        });
    }
}
