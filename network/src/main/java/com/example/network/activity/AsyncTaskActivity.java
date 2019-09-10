package com.example.network.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.network.R;
import com.example.network.task.ProgressAsyncTask;
import com.example.network.task.ProgressAsyncTask.OnProgressListener;

public class AsyncTaskActivity extends AppCompatActivity implements OnProgressListener {
    private TextView tv_async;
    private ProgressBar pb_async;
    private ProgressDialog mDialog;
    public int mShowStyle;
    private int BAR_HORIZONTAL = 1; //  水平条
    private int DIALOG_CIECLE = 2;      //圆圈对话框
    private int DIALOG_HORIZONTAL = 3;  //水平对话框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        tv_async = findViewById(R.id.tv_async);
        pb_async = findViewById(R.id.pb_async);
        initBookSpinner();
    }

    private void  initBookSpinner() {
        ArrayAdapter<String> styleAdapter = new ArrayAdapter<String>(this, R.layout.item_select, bookArray);
        Spinner sp_style = findViewById(R.id.sp_style);
        sp_style.setPrompt("请选择要加载的小说");
        sp_style.setAdapter(styleAdapter);
        sp_style.setOnItemSelectedListener(new StyleSelectedListener());
        sp_style.setSelection(0);
    }

    private String[] bookArray = {"三国演义", "西游记", "红楼梦"};
    private int[] styleArray = {BAR_HORIZONTAL, DIALOG_CIECLE, DIALOG_HORIZONTAL};

    @Override
    public void onFinish(String result) {
        String desc = String.format("您要阅读的《%s》已经加载完毕", result);
        tv_async.setText(desc);
        closeDialog();
    }

    @Override
    public void onCancel(String result) {
        String desc = String.format("您要阅读的《%s》已经取消加载", result);
        tv_async.setText(desc);
        closeDialog();
    }

    @Override
    public void onUpdate(String request, int progress, int sub_progress) {
        String desc = String.format("%s当前加载进度%d%%", request, progress);
        tv_async.setText(desc);
        if (mShowStyle == BAR_HORIZONTAL) {
            pb_async.setProgress(progress);
            pb_async.setSecondaryProgress(sub_progress);
        } else if (mShowStyle == DIALOG_HORIZONTAL) {
            mDialog.setProgress(progress);
            mDialog.setProgressStyle(sub_progress);
        }
    }

    @Override
    public void onBegin(String request) {
        tv_async.setText(request + "开始加载");
        if (mDialog == null || !mDialog.isShowing()) {
            if (mShowStyle == DIALOG_CIECLE) {
                mDialog = ProgressDialog.show(this, "稍等", request + "页面加载中...");
            } else if (mShowStyle == DIALOG_HORIZONTAL) {
                mDialog = new ProgressDialog(this);
                mDialog.setTitle("稍等");
                mDialog.setMessage(request + "页面加载中....");
                mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mDialog.show();
            }
        }
    }

    class StyleSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            startTask(styleArray[position], bookArray[position]);       //启动加载线程
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private void startTask(int style, String msg) {
        mShowStyle = style;
        ProgressAsyncTask asyncTask = new ProgressAsyncTask(msg);
        asyncTask.setProgressListener(this);
        asyncTask.execute(msg);
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }


}
