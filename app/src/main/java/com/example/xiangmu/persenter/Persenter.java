package com.example.xiangmu.persenter;

import com.example.xiangmu.bean.BannerBean;
import com.example.xiangmu.bean.LoginBean;
import com.example.xiangmu.bean.RegBean;
import com.example.xiangmu.bean.ShopCarAddBean;

import java.util.Map;

public interface Persenter {
    void sendMessage(String Path, Map<String,String> map, Class clazz);

    void sendGet(String zhuBanner, Class clazz);

    void onPutStartRequest(String tobushop, Map<String,String> map, Class clazz);

    void sendMessageDelete(String quxiao, Map<String, String> map, Class clazz);

}
