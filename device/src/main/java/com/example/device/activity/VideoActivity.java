package com.example.device.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.device.R;
import com.example.device.widget.VideoPlayer;
import com.example.device.widget.VideoRecorder;

/**
 * VideoActivity
 *
 * @author lao
 * @date 2019/6/16
 */

public class VideoActivity extends AppCompatActivity implements VideoRecorder.OnRecordFinishListener{
    private static final String TAG = "VideoActivity";
    private VideoRecorder vr_movie;
    private VideoPlayer vp_movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        vr_movie = findViewById(R.id.vr_movie);
        vp_movie = findViewById(R.id.vp_movie);
        vr_movie.setOnRecordFinishListener(this);
    }

    //视频一旦完成，就触发监听器的onRecordFinish
    @Override
    public void onRecordFinish() {
        //延迟1秒后启动播放任务，好让系统有时间生成视频文件
        mHandler.postDelayed(mPreplay, 1000);
    }

    private Handler mHandler = new Handler();

    private Runnable mPreplay = new Runnable() {
        @Override
        public void run() {
            vp_movie.init(vr_movie.getRecordFilePath());
        }
    };

}
