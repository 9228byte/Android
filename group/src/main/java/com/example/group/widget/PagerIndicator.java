package com.example.group.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.group.R;
import com.example.group.util.Utils;


/**
 * PagerIndicator
 *
 * @author lao
 * @date 2019/5/4
 */
public class PagerIndicator extends LinearLayout {
    private static final String TAG = "PagerIndicator";
    private Context mContext;
    private int mCount;     //指示器的个数
    private int mPad;       //两个圆点之间的间隔
    private int mSeq = 0;   //当前序号
    private float mRadio = 0.0f;        //已经移动的距离百分比
    private Paint mPaint;
    private Bitmap mBackImage;      //背景位图，通常为灰色圆点
    private Bitmap mForeImage;      //前景位图，通常是高亮的红色圆点

    public PagerIndicator(Context context) {
        this(context, null);
    }

    public PagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPad = Utils.dip2px(mContext, 15);
        mBackImage = BitmapFactory.decodeResource(getResources(), R.drawable.icon_point_n);
        mForeImage = BitmapFactory.decodeResource(getResources(), R.drawable.icon_point_c);
    }

    //绘制圆点
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int left = (getMeasuredWidth() - mCount * mPad) / 2;
        for (int i = 0; i < mCount; i++) {
            //先绘制作为背景的几个灰色圆点
            canvas.drawBitmap(mBackImage, left + i * mPad, 0, mPaint );
        }
        //再绘制作为前的高亮圆点，该圆点随着翻页滑动而左右滚动
        canvas.drawBitmap(mForeImage, left + (mSeq + mRadio) * mPad, 0 , mPaint);
    }

    //设置指示器的个数，以及指示器之间的距离
    public void setCount(int count, int pad) {
        mCount = count;
        mPad = Utils.dip2px(mContext, pad);
        invalidate();       //立即刷新视图
    }

    //设置指示器当前移动的位置。及其位移比率
    public void setCurrent(int seq, float ratio) {
        mSeq = seq;
        mRadio = ratio;
        invalidate();       //立即刷视图
    }

}
