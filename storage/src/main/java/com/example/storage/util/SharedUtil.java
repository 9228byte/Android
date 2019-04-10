package com.example.storage.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lao on 2019/4/4
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
    public void writeShared(String key, String value) {
        SharedPreferences.Editor editor = mShared.edit();       //获得编辑器对象
        editor.putString(key, value);
        editor.commit();
    }

    //找到共享参数
    public String readShared(String key, String defaultVaule) {
        return mShared.getString(key, defaultVaule);
    }
}
