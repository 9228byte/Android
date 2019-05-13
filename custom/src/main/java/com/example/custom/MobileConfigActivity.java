package com.example.custom;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.example.custom.util.SharedUtil;

public class MobileConfigActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = "MobileConfigActivity";
    private static EditText et_config_month;
    private static EditText et_config_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_traffic);
        et_config_month = findViewById(R.id.et_config_month);
        et_config_day = findViewById(R.id.et_config_day);
        findViewById(R.id.btn_config_save).setOnClickListener(this);
        findViewById(R.id.btn_auto_adjust).setOnClickListener(this);
        int limit_month = SharedUtil.getIntance(this).readInt("limit_month", 1024);
        int limit_day = SharedUtil.getIntance(this).readInt("limit_day", 50);
        et_config_month.setText("" + limit_month);
        et_config_day.setText("" + limit_day);
        //初始化短息的内容观察器
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS} , 0);
        initSmsObserver();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_config_save) {
            saveFlowConfig(Integer.parseInt(et_config_month.getText().toString()),
                    Integer.parseInt(et_config_day.getText().toString()));
            Toast.makeText(this, "成功保存配置", Toast.LENGTH_SHORT).show();
            finish();
        } else if (v.getId() == R.id.btn_auto_adjust) {
            sendSmsAuto(mCustomNumber, "103");
        }
    }

    //把限额保存进共享参数中
    private static void saveFlowConfig(int limit_month, int limit_day) {
        SharedUtil.getIntance(MainApplication.getInstance()).writeInt("limit_month", limit_month);
        SharedUtil.getIntance(MainApplication.getInstance()).writeInt("limit_day", limit_day);
    }

    //短信发送时间
    private String SENT_SMS_ACTION = "com.example.custom.SENT_SMS_ACTION";
    //短信接收事件
    private String DELIVERED_SMS_ACTION = "com.example.custom.DELIVERED_ACTION";

    public void sendSmsAuto(String phoneNumber, String message) {
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        sentIntent.putExtra("phone", phoneNumber);
        sentIntent.putExtra("message", message);
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //指定短信接收事件的详细信息
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        deliverIntent.putExtra("phone", phoneNumber);
        deliverIntent.putExtra("message", message);
        PendingIntent deliverPI = PendingIntent.getBroadcast(this, 1, deliverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //获取默认短信管理器
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, sentPI, deliverPI);
    }

    private Handler mHandler = new Handler();
    private SmsGetObserver mObserver;       //获取短信观察器
    private static String mCustomNumber = "10086";
    private static Uri mSmsUri;
    private static String[] mSmsColumn;

    //初始化观察器
    private void initSmsObserver() {
        mSmsUri = Uri.parse("content://sms");
        mSmsColumn = new String[]{"address", "body", "date"};
        mObserver = new SmsGetObserver(this, mHandler);
        //一旦观察到发生变化，触发onChange方法
        getContentResolver().registerContentObserver(mSmsUri, true, mObserver);
    }

    @Override
    protected void onDestroy() {
        //注销内容观察器
        getContentResolver().unregisterContentObserver(mObserver);
        super.onDestroy();
    }

    //定义一个短息获取的观察器
    private static class SmsGetObserver extends ContentObserver {
        private Context mContext;

        public SmsGetObserver(Context context, Handler handler) {
            super(handler);
            mContext = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            String sender = "", content = "";
            String selection = String.format("address='%s' and date>%d'", mCustomNumber, System.currentTimeMillis() - 1000 * 60 * 60);
            Cursor cursor = mContext.getContentResolver().query(mSmsUri, mSmsColumn, selection, null, " date desc");
            while (cursor.moveToNext()) {
                sender = cursor.getString(0);
                content = cursor.getString(1);
                break;
            }
            cursor.close();
            String totalFlow = "0";
            if (sender.equals(mCustomNumber)) {
                totalFlow = findFlow(content, "总流量为", ", ");
            }
            String[] flows = totalFlow.split("GB");
            Log.d(TAG, "totalFlow=" + totalFlow + ", flows.length=" + flows.length);
            int flowData = 0;
            if (totalFlow.contains("GB") && TextUtils.isDigitsOnly(flows[0])) {
                flowData += Integer.parseInt(flows[0]) * 1024;
            }
            if (flows.length > 1 && TextUtils.isDigitsOnly(flows[1])) {
                flowData += Integer.parseInt(flows[1]);
            }
            if (et_config_month != null && flowData != 0) {
                et_config_month.setText("" + flowData);
                et_config_day.setText("" + flowData / 30);
                // 把流量限额保存到共享参数中
                saveFlowConfig(flowData, flowData / 30);
                Toast.makeText(MainApplication.getInstance(), "流量校准成功", Toast.LENGTH_SHORT).show();
            }
            super.onChange(selfChange);
        }
    }

    private static String findFlow(String sms, String begin, String end) {
        int begin_pos = sms.indexOf(begin);
        if (begin_pos < 0) {
            return "未获取";
        }
        String sub_sms = sms.substring(begin_pos);
        int end_pos = sub_sms.indexOf(end);
        if (end_pos < 0) {
            return "未获取";
        }
        if (end.equals("，")) {
            return sub_sms.substring(begin.length(), end_pos);
        } else {
            return sub_sms.substring(begin.length(), end_pos + end.length());
        }
    }

}
