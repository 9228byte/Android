package com.example.custom.util;

import android.app.Activity;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * MeasureUtil
 *
 * @author lao
 * @date 2019/4/18
 */
public class MeasureUtil {

    public static float getTextWidth(String text, float textSize) {
        if (TextUtils.isEmpty(text)) {
            return 0;
        }
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        return paint.measureText(text);
    }

    public static float getTextHeight(String text,float textSize) {
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        Paint.FontMetrics fm = paint.getFontMetrics();      //获取默认字体的度量衡
        return fm.descent - fm.ascent;
    }

    //根据资源编号获得线性布局的实际高度(页面来源）
    public static float getRealHeight(Activity act, int resid) {
        LinearLayout llayout = act.findViewById(resid);
        return getRealHeight(llayout);
    }

    //根据资源编号获得线性布局的实际高度（视图来源）
    public static float getRealHeight(View parent, int resid) {
        LinearLayout llayout = parent.findViewById(resid);
        return getRealHeight(llayout);
    }

    //计算指定线性布局的实际高度
    public static float getRealHeight(View child) {
        LinearLayout llayout = (LinearLayout) child;
        //获得线性布局的布局参数
        ViewGroup.LayoutParams params = llayout.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        //获得布局参数里面的宽度规格
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0 , params.width);
        int heightSpec;
        if (params.height > 0) {    //高度大于0，说明这是明确的dp数值
            heightSpec = View.MeasureSpec.makeMeasureSpec(params.height, View.MeasureSpec.EXACTLY);
        } else {        //
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        llayout.measure(widthSpec, heightSpec);
        return llayout.getMeasuredHeight();
    }
}
