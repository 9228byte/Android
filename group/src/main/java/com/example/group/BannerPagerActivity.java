package com.example.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.group.constant.ImageList;
import com.example.group.util.Utils;
import com.example.group.widget.BannerPager;

public class BannerPagerActivity extends AppCompatActivity implements BannerPager.BannerClickListener {
    private static final String TAG = "BannerPagerActivity";
    private TextView tv_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_pager);
        tv_pager = findViewById(R.id.tv_pager);
        BannerPager banner = findViewById(R.id.banner_pager);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) banner.getLayoutParams();
        params.height = (int) (Utils.getScreenWidth(this) * 250f / 640f);
        banner.setLayoutParams(params);
        banner.setImage(ImageList.getDefault());
        banner.setOnBannerListener(this);
        banner.start();
    }

    @Override
    public void onBannerClick(int position) {
        String desc = String.format("您点击了第%d张图片", position + 1);
        tv_pager.setText(desc);
    }
}
