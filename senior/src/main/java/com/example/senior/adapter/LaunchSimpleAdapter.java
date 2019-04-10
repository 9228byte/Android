package com.example.senior.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.example.senior.R;

import java.util.ArrayList;

/**
 * Created by lao on 2019/4/7
 */

public class LaunchSimpleAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<View> mViewList = new ArrayList<View>();

    public LaunchSimpleAdapter(Context context, int[] imageArray) {
        mContext = context;
        for (int i = 0; i < imageArray.length; i++) {
            //根据布局文件生成图像对象
            View view = LayoutInflater.from(context).inflate(R.layout.item_launch, null);
            ImageView iv_launch = view.findViewById(R.id.iv_launch);
            RadioGroup rg_indicate = view.findViewById(R.id.rg_indicate);
            Button btn_start = view.findViewById(R.id.btn_start);       //最后一页的按钮
            //设置引导页的全屏图片
            iv_launch.setImageResource(imageArray[i]);
            //没张图片都分配一个对应的单选按钮RadioButton
            for (int j = 0; j < imageArray.length; j++) {
                RadioButton radio = new RadioButton(mContext);
                radio.setLayoutParams(new LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                radio.setButtonDrawable(R.drawable.launch_guide);
                radio.setPadding(10, 10, 10, 10);
                //把单选按钮添加到底部指示的单选组
                rg_indicate.addView(radio);
            }

            //当前位置的单选按钮要高亮显示，比如第二个引导页就高亮就第二个单选按钮
            ((RadioButton) rg_indicate.getChildAt(i)).setChecked(true);
            //如果是最后一个引导页，则显示入口按钮，以便用户点击进入首页
            if (i == imageArray.length - 1) {
                btn_start.setVisibility(View.VISIBLE);
                btn_start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "欢迎您开启美好生活",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            //把图片对应的引导页添加到引导页的视图队列
            mViewList.add(view);
        }
    }
    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }
}
