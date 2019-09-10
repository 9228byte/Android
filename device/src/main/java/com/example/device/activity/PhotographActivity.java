package com.example.device.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.device.R;
import com.example.device.adapter.ShootingAdapter;
import com.example.device.widget.CameraView;

import java.util.ArrayList;

/**
 * PhotographActivity
 *
 * @author lao
 * @date 2019/6/16
 */

public class PhotographActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = "PhotographActivity";
    private FrameLayout fl_content;
    private ImageView iv_photo;
    private GridView gv_shooting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photograph);
        fl_content = findViewById(R.id.fl_content);
        iv_photo = findViewById(R.id.iv_photo);
        gv_shooting = findViewById(R.id.gv_shooting);
        findViewById(R.id.btn_catch_behind).setOnClickListener(this);
        findViewById(R.id.btn_catch_front).setOnClickListener(this);
    }

    //处理Camera拍照页面的返回效果
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Bundle resp = intent.getExtras();       //获取返回的数据
        String is_null = resp.getString("is_null");
        if (!TextUtils.isEmpty(is_null) && !is_null.equals("yes")) {
            int type = resp.getInt("type");
            if (type == 0) {        //单拍
                iv_photo.setVisibility(View.VISIBLE);
                gv_shooting.setVisibility(View.GONE);
                String path  = resp.getString("path");
                fillBitmap(BitmapFactory.decodeFile(path, null));
            } else if (type ==1) {      //连拍
                iv_photo.setVisibility(View.GONE);
                gv_shooting.setVisibility(View.VISIBLE);
                ArrayList<String> pathList = resp.getStringArrayList("path_list");
                //通过网格视图展示连拍的照片
                ShootingAdapter adapter = new ShootingAdapter(this, pathList);
                gv_shooting.setAdapter(adapter);
            }
        }
    }

    //以合适的比例显示图片
    private void fillBitmap(Bitmap bitmap) {
        //位图的而高度大于框架布局的高度，则按比例调整图像视图的宽高
        if (bitmap.getHeight() > fl_content.getMeasuredHeight()) {
            LayoutParams params = iv_photo.getLayoutParams();
            params.height = fl_content.getHeight();
            params.width = bitmap.getWidth() + fl_content.getMeasuredHeight() / bitmap.getHeight();
            iv_photo.setLayoutParams(params);
        }
        iv_photo.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv_photo.setImageBitmap(bitmap);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_catch_behind) {
            Camera mCamera = Camera.open();
            if (mCamera != null) {
                mCamera.release();
                Intent intent = new Intent(this, TakePictureActivity.class);
                intent.putExtra("type", CameraView.CAMERA_BEHIND);
                startActivityForResult(intent, 1);
            } else {
                Toast.makeText(this, "当前设备不支持后置摄像头", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btn_catch_front) {
            Log.d(TAG, "getNumberOfCameras" + Camera.getNumberOfCameras());
            Camera mCamera = Camera.open(CameraView.CAMERA_FRONT);
            if (mCamera != null) {
                mCamera.release();
                Intent intent = new Intent(this, TakePictureActivity.class);
                intent.putExtra("type", CameraView.CAMERA_FRONT);
                startActivityForResult(intent, 1);
            } else {
                Toast.makeText(this, "当前设备不支持前置摄像头", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
