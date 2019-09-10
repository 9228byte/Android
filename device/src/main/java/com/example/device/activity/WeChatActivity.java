package com.example.device.activity;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.example.device.R;
import com.example.device.util.PermissionUtil;

/**
 * WeChatActivity
 *
 * @author lao
 * @date 2019/9/10
 */

public class WeChatActivity extends ActivityGroup implements OnClickListener {
    private static final String TAG = "WeChatActivity";
    public static Activity act;
    private Bundle mBundle = new Bundle();
    private LinearLayout ll_container, ll_first, ll_second, ll_third;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat);
        act = this;
        ll_container = findViewById(R.id.ll_container);
        ll_first = findViewById(R.id.ll_first);
        ll_second = findViewById(R.id.ll_second);
        ll_third = findViewById(R.id.ll_third);
        ll_first.setOnClickListener(this);
        ll_second.setOnClickListener(this);
        ll_third.setOnClickListener(this);
        mBundle.putString("tag", TAG);
        changeContainerView(ll_third);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_first || v.getId() == R.id.ll_second || v.getId() == R.id.ll_third) {
            changeContainerView(v);
        }
    }

    private void changeContainerView(View v) {
        ll_first.setSelected(false);
        ll_second.setSelected(false);
        ll_third.setSelected(false);
        v.setSelected(true);
        if (v == ll_first) {
            toActivity("first", WeConcernActivity.class);
        } else if (v == ll_second) {
            toActivity("second", WeContactActivity.class);
        } else if (v == ll_third) {
            toActivity("third", WeFindActivity.class);
        }
    }

    private void toActivity(String label, Class<?> cls) {
        Intent intent = new Intent(this, cls).putExtras(mBundle);
        ll_container.removeAllViews();
        //启动意图指向的活动，并获取该活动页面的顶层视图
        View v = getLocalActivityManager().startActivity(label, intent).getDecorView();
        //设置内容布局参数
        v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //把活动页面的顶层视图（即内容视图）添加到内容框架上
        ll_container.addView(v);
    }

    //处理发现对定位功能的授权请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == R.id.tv_smell % 4096) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PermissionUtil.goActivity(this, FindSmellActivity.class);
            } else {
                Toast.makeText(this, "需要允许定位权限才能使用咻一咻噢", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
