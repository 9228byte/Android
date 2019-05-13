package com.example.group;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.group.bean.LifeItem;
import com.example.group.util.Utils;

public class ScrollAlipayActivity extends AppCompatActivity implements OnOffsetChangedListener {
    private static final String TAG = "ScrollAlipayActivity";
    private View tl_expand, tl_collapse;
    private View v_expand_mask, v_collapse_mask, v_pay_mask;
    private int mMaskColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_alipay);
        mMaskColor = getResources().getColor(R.color.blue_dark);
        RecyclerView rv_content = findViewById(R.id.rv_content);
        //设置循环视图的布局管理器（四列的网格布局管理器）
        rv_content.setLayoutManager(new GridLayoutManager(this, 4));
        //设置生活频道网格适配器
        rv_content.setAdapter(new LifeRecyclerAdapter(this, LifeItem.getDefault()));
        AppBarLayout abl_bar = findViewById(R.id.abl_bar);
        tl_expand = findViewById(R.id.tl_expand);
        tl_collapse = findViewById(R.id.tl_collapse);
        v_expand_mask = findViewById(R.id.v_expand_mask);
        v_collapse_mask = findViewById(R.id.v_collapse_mask);
        v_pay_mask = findViewById(R.id.v_pay_mask);
        abl_bar.addOnOffsetChangedListener(this);
    }

    //每当应用栏向上滚动或者向下滚动，就会触发位置偏移监听器的onOffsetChanged
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int offset = Math.abs(verticalOffset);
        //获取应用栏的整个华东范围，以此计算当前的位移比例
        int total = appBarLayout.getTotalScrollRange();
        int alphaIn = Utils.px2dip(this, offset);
        int alphaOut = (200 - alphaIn) < 0 ? 0 : 200 - alphaIn;
        //计算淡入淡出时候的遮罩透明度
        int mMaskColorIn = Color.argb(alphaIn, Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor));
        //工具栏下方的生活频道布局要加入淡入或淡出
        int mMaskColorInDouble = Color.argb(alphaIn * 2, Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor));
        //计算淡出时候的遮罩透明度
        int mMaskColorOut = Color.argb(alphaOut * 3, Color.red(mMaskColor), Color.green(mMaskColor), Color.red(mMaskColor));
        if (offset <= total * 0.45) {       //伸缩量小于一半，则显示伸展时候的工具栏
            tl_expand.setVisibility(View.VISIBLE);
            tl_collapse.setVisibility(View.GONE);
            v_expand_mask.setBackgroundColor(mMaskColorInDouble);
        } else {        //偏移量大于一半，则显示收缩时候的工具栏
            tl_expand.setVisibility(View.GONE);
            tl_collapse.setVisibility(View.VISIBLE);
            v_collapse_mask.setBackgroundColor(mMaskColorOut);
        }
        //设置life_pay.xml即生活频道视图的遮罩视图的颜色
        v_pay_mask.setBackgroundColor(mMaskColorIn);
    }
}
