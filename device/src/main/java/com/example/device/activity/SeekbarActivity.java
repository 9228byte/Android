package com.example.device.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.device.R;

/**
 * SeekbarActivity
 *
 * @author lao
 * @date 2019/6/16
 */

public class SeekbarActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private TextView tv_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbar);
        //tv_progress需在sb_progress之前否则异常
        tv_progress = findViewById(R.id.tv_progress);
        SeekBar sb_progress = findViewById(R.id.sb_progress);
        sb_progress.setOnSeekBarChangeListener(this);
        sb_progress.setProgress(50);
    }

    //在进度变更时触发，第三个参数为true时表示用户拖动，为false表示代码设置进度
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        String desc = "当前进度为：" + seekBar.getProgress() + "，最大进度为" + seekBar.getMax();
        tv_progress.setText(desc);
    }

    //在开始拖动进度时触发
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    //在停止拖动进度时触发
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
