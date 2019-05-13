package com.example.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.group.constant.ImageList;
import com.example.group.util.Utils;
import com.example.group.widget.BannerIndicator;


/**
 * BannerIndicatorActivity
 *
 * @author lao
 * @date 2019/5/4
 */

public class BannerIndicatorActivity extends AppCompatActivity implements BannerIndicator.BannerClickListener {
    private static final String TAG = "BannerIndicatorActivity";
    private TextView tv_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_indicator);
        tv_pager = findViewById(R.id.tv_pager);
        BannerIndicator banner = findViewById(R.id.banner_indicator);
        LayoutParams params = (LayoutParams) banner.getLayoutParams();
        params.height = (int)(Utils.getScreenWidth(this) * 150f / 640f);
        //设置航服指示器布局参数
        banner.setLayoutParams(params);
        //设置横幅指示器的广告图片队列
        banner.setImage(ImageList.getDefault());
        //设置横幅指示器的广告监听器
        banner.setOnBannerListener(this);
    }

    //点击广告图，回调监听器的onBannerClick方法
    @Override
    public void onBannerClick(int position) {
        String desc = String.format("您点击了第%d张图片", position + 1);
        tv_pager.setText(desc);
    }
}
