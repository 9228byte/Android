package com.example.network.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.network.R;
import com.example.network.util.DateUtil;

public class ProgressDialogActivity extends AppCompatActivity {
    private static final String TAG = "ProgressDialogActivity";
    private ProgressDialog mDialog;
    private TextView tv_result;
    private String mStyleDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_dialog);
        tv_result = findViewById(R.id.tv_result);
        initStyleSpinner();
    }

    private void initStyleSpinner() {
        ArrayAdapter<String> styleAdapter = new ArrayAdapter<String>(this, R.layout.item_select, descArray);
        Spinner sp_style = findViewById(R.id.sp_style);
        sp_style.setPrompt("请选择对话框样式");
        sp_style.setAdapter(styleAdapter);
        sp_style.setOnItemSelectedListener(new StyleSelectedListener());
        sp_style.setSelection(0);
    }

    private String[] descArray = {"圆圈进度", "水平进度条"};
    private int[] styleArray = {ProgressDialog.STYLE_SPINNER, ProgressDialog.STYLE_HORIZONTAL};

    class StyleSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "position" + position);
            if (mDialog == null || !mDialog.isShowing()) {
                mStyleDesc = descArray[position];
                int style = styleArray[position];
                if (style == ProgressDialog.STYLE_SPINNER) {
                    //弹出有文字的对话框
                    mDialog = ProgressDialog.show(ProgressDialogActivity.this, "请稍后", "正在努力加载页面");
                    mHandler.postDelayed(mCloseDialog, 1500);
                } else {
                    Log.d(TAG, "onItemSelected: ");
                    //创建一个进度对话框
                    mDialog = new ProgressDialog(ProgressDialogActivity.this);
                    mDialog.setTitle("请稍后");
                    mDialog.setMessage("正在努力加载页面");
                    mDialog.setMax(100);
                    mDialog.setProgressStyle(style); //  设置对话框的样式
                    mDialog.show();
                    new RefreshThread().start();
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    //定义一个关闭对话框的任务
    private Runnable mCloseDialog = new Runnable() {
        @Override
        public void run() {
            if (mDialog.isShowing()) {
                mDialog.dismiss();      //关闭对话框
                tv_result.setText(DateUtil.getNowTime() + " " + mStyleDesc + "加载完成");
            }
        }
    };


    //定义一个进度刷新线程
    private class RefreshThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i <= 10 ; i++) {
                Message message = Message.obtain();     //获得一个默认的消息对象
                message.what = 0;       //消息类型
                message.arg1 = i * 10;      //消息数值
                mHandler.sendMessage(message);      //往处理区发送消息
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mHandler.sendEmptyMessage(1);       //往处理器发送类型为1的空消息
        }
    }

    //创建一个处理器对象
    private Handler mHandler = new Handler() {
        //消息收到是触发

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                mDialog.setProgress(msg.arg1);      //设置进度对话框上的当前进度
            } else if (msg.what == 1) {
                post(mCloseDialog);     //立即关闭对话框
            }
        }
    };
}
