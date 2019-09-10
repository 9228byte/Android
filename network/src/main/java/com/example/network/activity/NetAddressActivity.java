package com.example.network.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.network.R;

import java.net.InetAddress;

public class NetAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "NetAddressActivity";
    private EditText et_host_name;
    private TextView tv_host_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_address);
        et_host_name = findViewById(R.id.et_host_name);
        et_host_name.setText("www.borntorace.top");
        tv_host_name = findViewById(R.id.tv_host_name);
        findViewById(R.id.btn_host_name).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_host_name) {
            new CheckThread(et_host_name.getText().toString()).start(); //传送地址
        }
    }


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            tv_host_name.setText("主机检查结果如下：" + msg.obj);
        }
    };


    private class CheckThread extends Thread {
        private String mHostName;

        public CheckThread(String host_name) {
            mHostName = host_name;
        }

        @Override
        public void run() {
            Log.d(TAG, "run: ");
                    
            Message message = Message.obtain();
            try {
                InetAddress host = InetAddress.getByName(mHostName);
                boolean isReachable = host.isReachable(5000);
                String desc = (isReachable) ? "可以连接" : "无法连接";
                if (isReachable) {
                    desc = String.format("%S\n主机名为%s\n主机地址为%s", desc, host.getHostName(), host.getHostAddress());
                }
                message.what = 0;
                message.obj = desc;
            } catch (Exception e) {
                e.printStackTrace();
                message.what = -1;
                message.obj = e.getMessage();
            }
            mHandler.sendMessage(message);
            Log.d(TAG, "run: end");
        }
    }
}
