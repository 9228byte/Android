package com.example.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.group.constant.ImageList;
import com.example.group.util.StatusBarUtil;
import com.example.group.util.Utils;
import com.example.group.widget.BannerPager;

/**
 * BannerTopActivity
 *
 * @author lao
 * @date 2019/5/6
 */

public class BannerTopActivity extends AppCompatActivity implements View.OnClickListener, BannerPager.BannerClickListener {
    private static final String TAG = "BannerTopActivity";
    private Button btn_top;
    private boolean isOccupy = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_top);
        btn_top = findViewById(R.id.btn_top);
        btn_top.setOnClickListener(this);
        StatusBarUtil.fullScreen(this);
        BannerPager banner = findViewById(R.id.banner_top);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) banner.getLayoutParams();
        params.height = (int) (Utils.getScreenWidth(this) * 250f / 640f);
        //设置图片队列
        banner.setImage(ImageList.getDefault());
        banner.setOnBannerListener(this);
        banner.start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_top) {
            if (isOccupy) {
                StatusBarUtil.reset(this);
            } else {
                StatusBarUtil.fullScreen(this);
            }
            isOccupy = !isOccupy;
            btn_top.setText(isOccupy ? "腾出状态栏" : "霸占状态栏");
        }
    }

    @Override
    public void onBannerClick(int position) {
        String desc = String.format("您点击了第%d张图片", position + 1);
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }
}
