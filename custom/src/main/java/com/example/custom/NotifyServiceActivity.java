package com.example.custom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.custom.service.MusicService;

/**
 * NotifyServiceActivity
 *
 * @author lao
 * @date 2019/4/22
 */

public class NotifyServiceActivity extends AppCompatActivity implements OnClickListener {
    private EditText et_song;
    private Button btn_send_service;
    private boolean isPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_service);
        et_song = findViewById(R.id.et_song);
        btn_send_service = findViewById(R.id.btn_send_service);
        btn_send_service.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send_service) {
            Intent intent = new Intent(this, MusicService.class);
            intent.putExtra("is_play", isPlaying);
            intent.putExtra("song", et_song.getText().toString());
            if (isPlaying) {
                startService(intent);
                btn_send_service.setText("停止播放");
            } else  {
                stopService(intent);
                btn_send_service.setText("开始播放");
            }
            isPlaying = !isPlaying;
        }
    }


}
