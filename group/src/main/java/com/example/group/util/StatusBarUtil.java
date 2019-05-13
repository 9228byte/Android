package com.example.group.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * StatusBarUtil
 *
 * @author lao
 * @date 2019/5/6
 */
public class StatusBarUtil {
    private static final String TAG = "StatusBarUtil";
    private static final String TAG_FAKE_STATUS_BAR_VIEW = "statusBarView";
    private static final String TAG_MARGIN_ADDED = "marginAdded";

    //获取顶部状态栏的高度
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = -1;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取相应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    //把页面内容顶到状态栏内部，看到状态栏就像是悬浮在页面之上
    public static void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个标志位要结合使用，表示让应用的主题内容占用系统状态栏的空间
                //第三个标志位可让底部状态栏变透明View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                //添加到状态栏
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

                //底部导航栏也可以透明
//            int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//            attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
            //需要把自身颜色设置透明，这样才有悬浮效果
            setStatusBarColor(activity, Color.TRANSPARENT);
            Log.d(TAG, "fullScreen: ");
        }
    }

    //重置状态栏为黑色
    public static void reset(Activity activity) {
        setStatusBarColor(activity, Color.BLACK);
    }

    //设置状态栏的背景色。android5.0和4.4单独设置
    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(color);
            } else {
                setKitKatStatusBarColor(activity, color);
            }
            if (color == Color.TRANSPARENT) {
                removeMarginTop(activity);
            } else {
                addMarginTop(activity);
            }
        }
    }

    //添加顶部间隔，留出状态栏
    private static void addMarginTop(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup contentView = window.findViewById(Window.ID_ANDROID_CONTENT);
        View child = contentView.getChildAt(0);
        if (!TAG_MARGIN_ADDED.equals(child.getTag())) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) child.getLayoutParams();
            params.topMargin += getStatusBarHeight(activity);
            child.setLayoutParams(params);
            child.setTag(TAG_MARGIN_ADDED);
        }
    }

    //移除顶部状态栏
    private static void removeMarginTop(Activity activity) {
        Window window  = activity.getWindow();
        ViewGroup contentView = window.findViewById(Window.ID_ANDROID_CONTENT);
        View child = contentView.getChildAt(0);
        if (TAG_MARGIN_ADDED.equals(child.getTag())) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) child.getLayoutParams();
            params.topMargin -= getStatusBarHeight(activity);
            child.setLayoutParams(params);
            child.setTag(null);
        }
    }

    //设置android4.4系统状态栏颜色
    private static void setKitKatStatusBarColor(Activity activity, int statusBarColor) {
        Window window = activity.getWindow();
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View fakeView = decorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
        if (fakeView != null) {
            decorView.removeView(fakeView);
        }
        View statusBarView = new View(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(statusBarColor);
        statusBarView.setTag(TAG_FAKE_STATUS_BAR_VIEW);
        decorView.addView(statusBarView);
    }
}
