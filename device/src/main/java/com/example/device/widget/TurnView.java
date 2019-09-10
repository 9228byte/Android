package com.example.device.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Paint.Style;


/**
 * TurnView
 *
 * @author lao
 * @date 2019/5/13
 */
public class TurnView extends View {
    private Paint mPaint;
    private RectF mRectF;
    private int mBeginAngle = 0;
    private boolean isRunning = false;
    private Handler mHandler = new Handler();

    public TurnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);      //设置画笔为无锯齿
        mPaint.setColor(Color.RED);     //画笔颜色
        mPaint.setStrokeWidth(10);      //画笔线宽
        mPaint.setStyle(Style.FILL);        //设置画笔类型,STROK表示空心，FILL表示实心
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //在画布上绘制扇形，第四个参数为true表示绘制扇形，为false表示绘制圆弧
        canvas.drawArc(mRectF, mBeginAngle, 30, true, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算转动圆圈的直径
        int diameter = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        //根据圆圈直径创建转动区域的矩形边界
        mRectF = new RectF(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + diameter, getPaddingTop() + diameter);
    }

    private Runnable drawRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                mHandler.postDelayed(this, 70);
                mBeginAngle += 3;
                //立即刷新视图，也就是调用视图的onDraw和dispatchDraw方法
                invalidate();
            } else{
                mHandler.removeCallbacks(this);
            }
        }
    };

    public void start() {
        isRunning = true;
        mHandler.post(drawRunnable);
    }

    public void stop() {
        isRunning = false;
    }
}
