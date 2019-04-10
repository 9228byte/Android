package com.example.senior.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 *  Created by lao on 2019/4/7
 */



public class SharedUtil {
    private static SharedUtil mUtil;     //声明一个共享参数工具类的实例
    private static SharedPreferences mShared;       //声明一个共享参数的实例

    public static SharedUtil getInstance(Context context) {
        if (mUtil == null) {
            mUtil = new SharedUtil();
        }
        mShared = context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        return mUtil;
    }

    //写入共享参数
    public void writeString(String key, String value) {
        SharedPreferences.Editor editor = mShared.edit();       //获得编辑器对象
        editor.putString(key, value);
        editor.commit();
    }

    //找到共享参数
    public String readString(String key, String defaultVaule) {
        return mShared.getString(key, defaultVaule);
    }

    public void writeInt(String key, int value) {
        SharedPreferences.Editor editor = mShared.edit();       //获得编辑器对象
        editor.putInt(key, value);
        editor.commit();
    }

    //找到共享参数
    public int readInt(String key, int defaultVaule) {
        return mShared.getInt(key, defaultVaule);
    }
}
