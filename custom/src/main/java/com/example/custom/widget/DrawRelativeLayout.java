package com.example.custom.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;


/**
 * DrawRelativeLayout
 *
 * @author lao
 * @date 2019/4/19
 */
public class DrawRelativeLayout extends RelativeLayout {
    private int mDrawType = 0;  //  绘制类型
    private Paint mPaint = new Paint();

    public DrawRelativeLayout(Context context) {
        this(context, null);
    }

    public DrawRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setAntiAlias(true);  //  设置画笔为无锯齿
        mPaint.setDither(true);     //设置画笔放抖动
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);       //设置画笔的线宽
        mPaint.setStyle(Paint.Style.STROKE);        //设置画笔的类型。STROKE表示空心, FILL表示实心
    }

    //onDrawz在绘制下级视图之前调用
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (width > 0 && height > 0 ) {
            if (mDrawType == 1) {   //绘制矩形
                Rect rect = new Rect(0 , 0, width, height);
                canvas.drawRect(rect, mPaint);
            } else if (mDrawType == 2) {    //绘制圆角矩形
                RectF rectF = new RectF(0, 0, width, height);
                canvas.drawRoundRect(rectF, 30, 30, mPaint);
            } else if (mDrawType == 3) {    //绘制圆圈
                int radius = Math.min(width, height) / 2;
                canvas.drawCircle(width / 2, height / 2, radius, mPaint);
            } else if (mDrawType == 4) {    //绘制椭圆
                RectF oval = new RectF(0, 0 , width, height);
                canvas.drawOval(oval, mPaint);
            } else if (mDrawType == 5) {    //绘制矩形及其对角线
                Rect rect = new Rect(0, 0, width, height);
                canvas.drawRect(rect, mPaint);
                canvas.drawLine(0, 0 , width, height, mPaint);
                canvas.drawLine(0, height, width, 0, mPaint);
            }
        }
    }

    //dispatchDraw方法在绘制下级视图之前调用
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (width > 0 && height > 0) {
            if (mDrawType == 6) {
                Rect rect = new Rect(0, 0, width, height);
                canvas.drawRect(rect, mPaint);
                canvas.drawLine(0, 0, width, height, mPaint);
                canvas.drawLine(0, height, width, 0, mPaint);
            }
        }
    }

    //设置绘制类型
    public void setmDrawType(int type) {
        //背景设置白色，目的是把画布擦干净
        setBackgroundColor(Color.WHITE);
        mDrawType = type;
        //立即重新绘图，此时会触发onDraw方法和dispatchDraw方法
        invalidate();
    }
}
