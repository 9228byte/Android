package com.example.device.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.device.R;
import com.example.device.widget.CameraView;

/**
 * TakePictureActivity
 *
 * @author lao
 * @date 2019/6/16
 */

public class TakePictureActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = "TakePictureActivity";
    private CameraView camera_view;
    private int mTakeType = 0;      //拍照类型,0为单拍，1位连拍

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        int camera_type = getIntent().getIntExtra("type", CameraView.CAMERA_BEHIND);    //  默认值为后置拍照
        camera_view = findViewById(R.id.camera_view);
        camera_view.setCameraType(camera_type);
        findViewById(R.id.btn_shutter).setOnClickListener(this);
        findViewById(R.id.btn_shooting).setOnClickListener(this);
    }

    //按下返回时携带数据触发
    public void onBackPressed() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        String photo_path = camera_view.getPhotoPath();     //获取照片保存路径
        bundle.putInt("type", mTakeType);
        if (photo_path == null && mTakeType == 0) {
            bundle.putString("is_null", "yes");
        } else {
            bundle.putString("is_null", "no");
            if (mTakeType == 0) {
                bundle.putString("path", photo_path);       //图片路径
            } else if (mTakeType == 1) {
                bundle.putStringArrayList("path_list", camera_view.getShootingList());
            }
        }
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_shutter) {
            mTakeType = 0;
            camera_view.doTakePicture();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(TakePictureActivity.this, "已完成拍照，按返回键回到上一页查看照片", Toast.LENGTH_SHORT).show();
                }
            }, 1500);
        } else if (v.getId() == R.id.btn_shooting) {
            mTakeType = 1;
            camera_view.doTakeShooting();
        }
    }
}
