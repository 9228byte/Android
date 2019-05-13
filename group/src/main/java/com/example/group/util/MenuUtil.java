package com.example.group.util;

import android.content.Context;
import android.view.Menu;
import android.view.ViewConfiguration;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * MenuUtil
 *
 * @author lao
 * @date 2019/5/2
 */
public class MenuUtil {

    //如果设备有物理按键菜单，需要将其屏蔽才能显示OverflowMenu
    //api18以下需要改函数在右上角强制显示选项菜单
    public static void forceShowOverMenu(Context context) {
        try {
            ViewConfiguration config = ViewConfiguration.get(context);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    //显示OverflowMenu的Icon
    public static void setOverflowIconVisible(int featureId, Menu menu) {
        //ActionBar 的featureId是8，Toolbar的featureId是108
        if (featureId % 100 == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    //setOptionIconVisible是个隐藏方法，需要通过反射机制调用
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
