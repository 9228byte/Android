package com.example.junior.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.junior.MainActivity;

/**
 * Created by lao on 2019/3/22
 */

public class Utils {
    //根据手机分辨率从dp的单位转化成px(像素）
    public static int dip2px(Context context, float dpValue){
            //获取当前手机的像素密度
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }

    //根据手机的分辨率从px(像素)的单位转化为dp
    public static int px2dip(Context context, float pxValue){
        //获取当前手机的像素密度
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue /scale + 0.5f);
    }

    //获取屏幕的宽度
    public static int getScreenWidth(Context ctx){
        //从系统服务中获取窗口管理器
        WindowManager wm = (WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    //获取屏幕高度
    public static int getScreenHeight(Context ctx){
        //从系统服务中获取窗口服务器
        WindowManager wm = (WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    //获取屏幕像素密度
    public static float getScreenDensity(Context ctx){
        WindowManager wm = (WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }
}
