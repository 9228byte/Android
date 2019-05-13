package com.example.custom;

import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.custom.adapter.TrafficInfoAdapter;
import com.example.custom.bean.AppInfo;
import com.example.custom.util.AppUtil;
import com.example.custom.util.StringUtil;

import java.util.ArrayList;

/**
 * TrafficInfoActivity
 *
 * @author lao
 * @date 2019/4/24
 */

public class TrafficInfoActivity extends AppCompatActivity {
    private static final String TAG = "TrafficInfoActivity";
    private TextView tv_traffic;
    private ListView lv_traffic;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_info);
        tv_traffic = findViewById(R.id.tv_traffic);
        lv_traffic = findViewById(R.id.lv_traffic);
        mHandler.postDelayed(mRefresh, 50);
    }

    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
            String desc = String.format("当前总共接收数据流量：%s\n其中接收数据流量：%s" +
                    "\n当前总共发送流量：%s\n" +
                            "其中发送数据流量：%s",
            StringUtil.formatData(TrafficStats.getTotalRxBytes()),
            StringUtil.formatData(TrafficStats.getMobileRxBytes()),
            StringUtil.formatData(TrafficStats.getTotalTxBytes()),
            StringUtil.formatData(TrafficStats.getMobileTxBytes()));
            tv_traffic.setText(desc);
            ArrayList<AppInfo> appinfoList = AppUtil.getAppInfo(TrafficInfoActivity.this, 1);
            for (int i = 0;i < appinfoList.size(); i++) {
                AppInfo item = appinfoList.get(i);
                item.traffic = TrafficStats.getUidRxBytes(item.uid);
                Log.d(TAG, "run: " + item.uid +" "+ item.label + ":" + item.traffic);
                appinfoList.set(i ,item);
            }
            TrafficInfoAdapter adapter = new TrafficInfoAdapter(TrafficInfoActivity.this, appinfoList);
            lv_traffic.setAdapter(adapter);
        }
    };
}
