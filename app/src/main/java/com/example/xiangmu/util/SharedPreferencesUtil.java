package com.example.xiangmu.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.xiangmu.app.App;

public class SharedPreferencesUtil {
    private static final String file_name = "user";
    private static final int sp_mode = Context.MODE_PRIVATE;
    private static Context mContext = App.context;
    public static boolean put(String key, Object value) {
        SharedPreferences sp = mContext.getSharedPreferences(file_name, sp_mode);
        SharedPreferences.Editor edit = sp.edit();
        if (value instanceof String) {
            if (!TextUtils.isEmpty((CharSequence) value)) {
                edit.putString(key, (String) value);
            }
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else {
            edit.putLong(key, (Long) value);
        }
        boolean commit = edit.commit();
        return commit;
    }
    //取值
    public static String getString(String key, String defult) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(file_name, sp_mode);
        return sharedPreferences.getString(key, defult);
    }
    //取值
    public static int getInt(String key, Integer defult) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(file_name, sp_mode);
        return sharedPreferences.getInt(key, defult);
    }

    //取值
    public static boolean getBoolean(String key, boolean defult) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(file_name, sp_mode);
        return sharedPreferences.getBoolean(key, false);
    }

    //摧毁方法
    public static void remove(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(file_name, sp_mode);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(key);
        edit.commit();
    }
    public static void removeAll() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(file_name, sp_mode);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear();
        edit.commit();
    }




}
