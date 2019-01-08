package com.example.xiangmu.persenter;

import com.example.xiangmu.bean.BannerBean;
import com.example.xiangmu.bean.LoginBean;

import java.util.Map;

public interface Persenter {
    void sendMessage(String Path, Map<String,String> map, Class clazz);

    void sendGet(String zhuBanner, Class<BannerBean> bannerBeanClass);
}
