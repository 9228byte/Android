package com.example.custom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

/**
 * NotifyProgressActivity
 *
 * @author lao
 * @date 2019/4/21
 */

public class NotifyProgressActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_progress;
    private ProgressBar pb_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_progress);
        pb_progress = findViewById(R.id.pb_progress);
        et_progress = findViewById(R.id.et_progress);
        findViewById(R.id.btn_progress).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int progress= 0;
        if (v.getId() == R.id.btn_progress) {
            String desc = et_progress.getText().toString();
            if (!TextUtils.isEmpty(desc)) {
                progress = Integer.parseInt(desc);
            }
            pb_progress.setProgress(progress);
        }
    }
}
