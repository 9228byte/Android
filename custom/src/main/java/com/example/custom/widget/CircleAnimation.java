package com.example.custom.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.custom.util.Utils;


/**
 * CircleAnimation
 *
 * @author lao
 * @date 2019/4/20
 */
public class CircleAnimation extends RelativeLayout {
    private static final String TAG = "CircleAnimation";
    private RectF mRect;        //矩形边界
    private int mBeginAngle = 0;    //起始角度
    private int mEndAngle = 270;        //终止角度
    private int mFrontColor = 0xffff0000;       //前景颜色
    private float mFrontLine = 10;        //前景线宽
    private Style mFrontStyle = Paint.Style.STROKE;     //前景风格
    private FrontView mFrontView;   //  前景视图
    private int mShadeColor = 0xffeeeeee;   //阴影颜色
    private float mShadeLine = 10;      //阴影线宽
    private Style mShadeStyle = Style.STROKE;       //阴影风格
    private ShadeView mShadeView;       //阴影视图
    private int mRate = 2;  //每次绘制递增角度
    private int mDrawTimes;     //总共要绘制的次数
    private int mInterval = 70;       //没次绘制之间的时间间隔，单位毫秒
    private int mFactor;        //加速因子，让圆弧动画展现加速效果
    private Context mContext;
    private int mSeq = 0;   //已绘制的次数
    private int mDrawingAngle = 0;      //已经绘制的角度

    public CircleAnimation(Context context) {
        super(context);
        mContext = context;
        mRect = new RectF(Utils.dip2px(context, 30), Utils.dip2px(context, 10), Utils.dip2px(context, 330), Utils.dip2px(context, 330));
    }
    //渲染圆弧动画。渲染操作包括初始化与播放两个动作
    public void render() {
        removeAllViews();       //移除所有下级视图
        mShadeView = new ShadeView(mContext);
        addView(mShadeView);
        mFrontView = new FrontView(mContext);
        addView(mFrontView);
        play();
    }

    public void play() {
        mSeq = 0;
        mDrawingAngle = 0;
        mDrawTimes = mEndAngle / mRate;
        mFactor = mDrawTimes / mInterval + 1;
        Log.d(TAG, "mDrawTimes=" + mDrawTimes + ",mInterval=" + mInterval + ",nFactor=" + mFactor);
        mFrontView.invalidateView();        //刷新前景视图
    }

    //设置圆弧动画的矩形边界
    public void setRect(int left, int top, int right, int bottom) {
        mRect = new RectF(left, top, right, bottom);
    }

    //设置圆弧动画的起始角度和终止角度
    public void setAngle(int begin_angle, int end_angle) {
        mBeginAngle = begin_angle;
        mEndAngle = end_angle;
    }

    //设置动画播放参数。speed表示每次移动几个参数，frames表示每秒移动几帧
    public void setRate(int speed, int frames) {
        mRate = speed;
        mInterval = 1000 / frames;
    }

    //设置圆弧阴影前景颜色、线宽和风格
    public void setFront(int color, float line, Style style) {
        mFrontColor = color;
        mFrontLine = line;
        mFrontStyle = style;
    }

    //设置圆弧阴影阴影颜色、线宽、风格
    public void setShade(int color, float line, Style style) {
        mShadeColor = color;
        mShadeLine = line;
        mShadeStyle = style;
    }

    //定义一个阴影视图
    private class ShadeView extends View {
        private Paint paint;    //声明一个画笔对象

        public ShadeView(Context context) {
            super(context);
            paint = new Paint();
            paint.setAntiAlias(true);   //设置为无锯齿
            paint.setDither(true);  //设置画笔为防抖动
            paint.setColor(mShadeColor);
            paint.setStrokeWidth(mShadeLine);   //画笔线宽
            paint.setStyle(mShadeStyle);    //设置画笔类型
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //绘制360度的阴影圆弧，第四个参数表示为true表示绘制扇形,为false表示绘制圆弧
            canvas.drawArc(mRect, mBeginAngle, 360, false, paint);
        }
    }

    //声明一个前景视图
    private class FrontView extends View {
        private Paint paint;
        private Handler handler = new Handler();        //声明一个处理器对象

        public FrontView(Context context) {
            super(context);
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setColor(mFrontColor);
            paint.setStrokeWidth(mFrontLine);
            paint.setStyle(mFrontStyle);
            paint.setStrokeCap(Paint.Cap.ROUND);        //设置画笔笔刷类型,影响画笔的始末端。ROUND表示圆角
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //绘制指定角度的阴影圆弧。第四个参数为true表示绘制扇形,为false表示绘制圆形
            canvas.drawArc(mRect, mBeginAngle, (float) (mDrawingAngle), false, paint);
        }

        public void invalidateView() {
            //立即启动绘制任务
            handler.post(drawRunnable);
        }

        //定义一个绘制任务
        private Runnable drawRunnable = new Runnable() {
            @Override
            public void run() {
                if (mDrawingAngle >= mEndAngle) {   //绘制已完成
                    mDrawingAngle = mEndAngle;
                    invalidate();   //立即触发视图的onDraw方法
                    handler.removeCallbacks(drawRunnable);  //清除绘制任务
                } else {    //绘制为完成
                    mDrawingAngle = mSeq * mRate;
                    mSeq++;
                    //间隔若干时间后，再次启动绘制任务
                    handler.postDelayed(drawRunnable, (long) (mInterval - mSeq / mFactor));
                    invalidate();       //立即触发视图onDraw方法
                }
            }
        };
    }

}
