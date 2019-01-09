package com.example.xiangmu.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.WindowManager;

import com.example.xiangmu.util.Constant;

public class App extends Application {
    //绘制页面时参照的设计图宽度
    public final static float DESIGN_WIDTH = 750;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        resetDensity();
        context=getApplicationContext();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetDensity();
    }
    public void resetDensity(){
        Point size = new Point();
        ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        getResources().getDisplayMetrics().xdpi = size.x/DESIGN_WIDTH*72f;
        getApplication();
    }

    public static Context getApplication() {
        return context;
    }
}
