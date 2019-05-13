package com.example.group.widget;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.group.R;
import com.example.group.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * BannerPager
 *
 * @author lao
 * @date 2019/5/6
 */
public class BannerPager extends RelativeLayout implements View.OnClickListener {
    private static final String TAG = "BannerPager";
    private Context mContext;
    private ViewPager vp_banner;
    private RadioGroup rg_indicator;
    private List<ImageView> mViewList = new ArrayList<ImageView>();
    private int mInterval = 2000;

    public BannerPager(Context context) {
        this(context, null);
    }

    public BannerPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private Handler mHandler = new Handler();

    private Runnable mScroll = new Runnable() {
        @Override
        public void run() {
            scrollToNext();
            mHandler.postDelayed(this, mInterval);
        }
    };

    //开始轮播
    public void start() {
        //延迟若干秒滚动
        mHandler.postDelayed(mScroll, mInterval);
    }

    public void stop() {
        //移栋任务
        mHandler.removeCallbacks(mScroll);
    }

    public void setInterval(int interval) {
        mInterval = interval;
    }

    //设置广告图片队列
    public void setImage(ArrayList<Integer> imageList) {
        int dip_15 = Utils.dip2px(mContext, 15);
        //根据图片队列生成图像视图队列
        for (int i = 0; i < imageList.size(); i++) {
            Integer imageID = imageList.get(i);
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(imageID);
            iv.setOnClickListener(this);
            mViewList.add(iv);
        }
        //设置翻页视图的图像翻页适配
        vp_banner.setAdapter(new ImageAdapter());

        vp_banner.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setButton(position);
            }
        });

        for (int i = 0; i < imageList.size(); i++) {
            RadioButton radio = new RadioButton(mContext);
            radio.setLayoutParams(new RadioGroup.LayoutParams(dip_15, dip_15));
            radio.setGravity(Gravity.CENTER);
            radio.setButtonDrawable(R.drawable.indicator_selector);
            rg_indicator.addView(radio);
        }
        vp_banner.setCurrentItem(0);
        setButton(0);
    }

    //设置选中单选内部的哪个单选按钮
    private void setButton(int position) {
        ((RadioButton) rg_indicator.getChildAt(position)).setChecked(true);
    }

    //初始化视图
    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.banner_pager, null);
        vp_banner = view.findViewById(R.id.vp_banner);
        rg_indicator = view.findViewById(R.id.rg_indicator);
        addView(view);
    }

    public void scrollToNext() {
        int index = vp_banner.getCurrentItem() + 1;
        if (index >= mViewList.size()) {
            index = 0;
        }
        //设置翻页视图显示的指定图像
        vp_banner.setCurrentItem(index);
    }

    //定义一个翻页视图适配器
    private class ImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mViewList.get(position));
        }
    }

    @Override
    public void onClick(View v) {
        int position = vp_banner.getCurrentItem();
        mListener.onBannerClick(position);
    }

    public void setOnBannerListener(BannerClickListener listener) {
        mListener = listener;
    }

    private BannerClickListener mListener;

    public interface BannerClickListener {
        void onBannerClick(int position);
    }
}
