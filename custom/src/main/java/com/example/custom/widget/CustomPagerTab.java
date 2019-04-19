package com.example.custom.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerTabStrip;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import com.example.custom.R;
import com.example.custom.util.Utils;


/**
 * CustomerPagerTab
 *
 * @author lao
 * @date 2019/4/18
 */
public class CustomPagerTab extends PagerTabStrip {
    private final static String TAG = "CustomPagerTab";
    private int textColor = Color.BLACK;
    private int textSize = 15;

    public CustomPagerTab(@NonNull Context context) {
        super(context);
    }

    public CustomPagerTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            //从布局文件中获取属性数组描述
            TypedArray attrArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomPagerTab);
            textColor = attrArray.getColor(R.styleable.CustomPagerTab_textColor, textColor);
            textSize = Utils.px2sp(context, attrArray.getDimension(R.styleable.CustomPagerTab_textSize, textSize));
            int customBackground = attrArray.getResourceId(R.styleable.CustomPagerTab_customBackground, 0);
            int customOrientation = attrArray.getInt(R.styleable.CustomPagerTab_customOrientation, 0);
            int customGravity = attrArray.getInt(R.styleable.CustomPagerTab_customGravity, 0);
            Log.d(TAG, "textColor=" + textColor + ", textSize=" + textSize);
            Log.d(TAG, "customBackground=" + customBackground + ", customOrientation=" + customOrientation);
            //回收数组
            attrArray.recycle();
        }
    }
    //PagerTabStrip没有三个参数的构造函数

    @Override
    protected void onDraw(Canvas canvas) {  //绘制函数
        setTextColor(textColor);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        super.onDraw(canvas);
    }
}
