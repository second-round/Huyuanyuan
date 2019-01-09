package com.example.xiangmu.persenter;
import com.example.xiangmu.bean.BannerBean;
import com.example.xiangmu.callback.MyCallBack;
import com.example.xiangmu.model.ModelImpl;
import com.example.xiangmu.view.IView;

import java.util.Map;

public class PersenterImpl implements Persenter{
    private IView iViewl;
    private ModelImpl model;

    public PersenterImpl(IView iViewl) {
        this.iViewl = iViewl;
        model=new ModelImpl();
    }


    @Override
    public void sendMessage(String path, Map<String, String> map, Class clazz) {
        model.sendMessage(path,map,clazz, new MyCallBack() {
            @Override
            public void CallBack(Object data) {
                iViewl.requesta(data);
            }
        });
    }

    @Override
    public void sendGet(String url, Class clazz) {
        model.requestDataGet(url, clazz, new MyCallBack() {
            @Override
            public void CallBack(Object data) {
                iViewl.requesta(data);
            }
        });
    }

    @Override
    public void onPutStartRequest(String tobushop, Map<String, String> map, Class clazz) {
        model.requestPut(tobushop,map,clazz, new MyCallBack() {
            @Override
            public void CallBack(Object data) {
                iViewl.requesta(data);
            }
        });
    }
}
