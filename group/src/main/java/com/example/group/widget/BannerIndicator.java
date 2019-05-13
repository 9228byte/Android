package com.example.group.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.group.R;

import java.util.ArrayList;
import java.util.List;


/**
 * BannerIndicator
 *
 * @author lao
 * @date 2019/5/4
 */

/*带指示点的广告轮播图，不自动滚动*/
public class BannerIndicator extends RelativeLayout implements View.OnClickListener {
    private static final String TAG = "BannerIndicator";
    private Context mContext;
    private ViewPager vp_banner;
    private PagerIndicator pi_banner;       //声明一个翻页指示器对象
    private List<ImageView> mViewList = new ArrayList<ImageView>();

    public  BannerIndicator(Context context) {
        this(context, null);
    }

    public BannerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    //设置广告图片队列
    public void setImage(ArrayList<Integer> imageList) {
        for (int i = 0; i < imageList.size(); i++ ) {
            Integer imageID = imageList.get(i);
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(imageID);
            iv.setOnClickListener(this);
            mViewList.add(iv);
        }
        vp_banner.setAdapter(new ImageAdapter());
        vp_banner.setOnPageChangeListener(new BannerChangeListener());
        vp_banner.setCurrentItem(0);
        //设置翻页视图指示器的个数与间隔
        pi_banner.setCount(imageList.size(), 15);
    }

    //初始化视图
    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.banner_indicator, null);
        vp_banner = view.findViewById(R.id.vp_banner);
        pi_banner = view.findViewById(R.id.pi_banner);
        addView(view);
    }

    //定义一个图像翻页适配器
    private class ImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        //从容器中销毁指定位置的页面
        @Override
        public void destroyItem(ViewGroup container, int position, Object object){
            container.removeView(mViewList.get(position));
        }

        //实例化指定位置的页面,并将其添加到容器中
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }
    }

    @Override
    public void onClick(View v) {
        int position = vp_banner.getCurrentItem();
        mListener.onBannerClick(position);
    }

    //设置广告图点击的监听器对象
    public void setOnBannerListener(BannerClickListener listener) {
        mListener = listener;
    }

    private BannerClickListener mListener;

    //定义一个广告图片的点击监听器接口
    public interface BannerClickListener {
        void onBannerClick(int position);
    }

    //设置广告图轮播监听器
    private class BannerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {
            pi_banner.setCurrent(i, v);
        }

        @Override
        public void onPageSelected(int i) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }

    }


}
