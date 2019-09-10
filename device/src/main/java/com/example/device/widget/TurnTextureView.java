package com.example.device.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.TextureView;


/**
 * TurnTextureView
 *
 * @author lao
 * @date 2019/5/14
 */
public class TurnTextureView extends TextureView implements TextureView.SurfaceTextureListener, Runnable{
    private Paint mPaint;
    private RectF mRectF;
    private int mBeginAngle = 0;
    private boolean isRunning = false;

    public TurnTextureView(Context context) {
        this(context, null);
    }

    public TurnTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int diameter = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        mRectF = new RectF(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + diameter , getPaddingTop() + diameter);
    }

    public void start() {
        isRunning = true;
        new Thread(this).start();
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            draw(false);
            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mBeginAngle += 3;
        }
    }

    private void draw(boolean isFirst) {
        //锁定纹理视图的画布对象
        Canvas canvas = lockCanvas();
        if (canvas != null){
            //清除整个画布，设置为默认白色背景
//            canvas.drawColor(Color.WHITE);
            if (!isFirst) {
                canvas.drawArc(mRectF, mBeginAngle, 30, true, mPaint);
            }
            //解锁画布
            unlockCanvasAndPost(canvas);
        }
    }


    //在纹理表面可用时触发
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        draw(true);
    }

    //在纹理表面的尺寸发生改变时触发
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    //在纹理表面销魂时触发
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return true;
    }

    //在纹理表面更新时触发
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }


}
