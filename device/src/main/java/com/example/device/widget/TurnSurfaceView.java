package com.example.device.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * TurnSurfaceView
 *
 * @author lao
 * @date 2019/5/13
 */
public class TurnSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Paint mPaint1, mPaint2;
    private RectF mRectF;
    private int mBeginAngel1 = 0, mBeginAngel2 = 180;
    private int mInterval = 70;
    private boolean isRunning = false;
    private final SurfaceHolder mHolder;

    public TurnSurfaceView(Context context) {
        this(context, null);
    }

    public TurnSurfaceView(Context context, AttributeSet attrs) {
        super(context,attrs);
        mHolder = getHolder();
        //get表面持有者添加变更监听器
        mHolder.addCallback(this);
        //下面两行设置背景透明，因为Surface默认背景是黑色
        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算圆圈的直径
        int diameter = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        mRectF = new RectF(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + diameter, getPaddingTop() + diameter);
    }

    public void start() {
        isRunning = true;
        new Thread() {
            @Override
            public void run() {
                while (isRunning) {
                    draw(mPaint1, mBeginAngel1);
                    try {
                        Thread.sleep(mInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mBeginAngel1 += 3;
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                while (isRunning) {
                    draw(mPaint2 , mBeginAngel2);
                    try {
                        Thread.sleep(mInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mBeginAngel2 += 3;
                }
            }
        }.start();
    }

    public void stop() {
        isRunning = false;
    }

    //绘制图形
    private void draw(Paint paint, int beginAngel) {
        //因为两个图形都在绘制，所以这里利用线程同步机制，防止资源被锁
        synchronized (mHolder) {
            //锁定表面持有者的画布对象
            Canvas canvas = mHolder.lockCanvas();
            if (canvas != null) {
//                SurfaceView上次绘图结果仍然保留，如果不想保留上次的绘图，则需要将整个画布清空
//                canvas.drawColor(Color.WHITE);
                //在画布上绘制，第四个参数为true表示绘制扇形，为false表示绘制圆弧
                canvas.drawArc(mRectF, beginAngel, 10, true, paint);
                //解锁表面持有者的画布对象
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    //获取指定颜色的画笔
    private  Paint getPaint(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
       mPaint1 = getPaint(Color.RED);
       mPaint2 = getPaint(Color.CYAN);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //在表面视图变更时触发
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //在表面销毁时触发
    }
}
