package com.example.device.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.device.R;
import com.example.device.adapter.ShootingAdapter;

import java.util.ArrayList;

/**
 * ShootingActivity
 *
 * @author lao
 * @date 2019/6/16
 */

public class ShootingActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = "ShootingActivity";
    private FrameLayout fl_content;
    private ImageView iv_photo;
    private GridView gv_shooting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooting);
        fl_content = findViewById(R.id.fl_content);
        iv_photo = findViewById(R.id.iv_photo);
        gv_shooting = findViewById(R.id.gv_shooting);
        findViewById(R.id.btn_catch_behind).setOnClickListener(this);
        findViewById(R.id.btn_catch_front).setOnClickListener(this);
    }


    //处理拍照返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle resp = data.getExtras();
        String is_null = resp.getString("is_null");
        if (!TextUtils.isEmpty(is_null) &&!is_null.equals("yes")) {
            int type = resp.getInt("type");
            Log.d(TAG, "type=" + type);
            if (type == 0) {
                iv_photo.setVisibility(View.VISIBLE);
                gv_shooting.setVisibility(View.GONE);
                String path = resp.getString("path");
                fillBitmap(BitmapFactory.decodeFile(path, null));
            } else if (type == 1) {
                iv_photo.setVisibility(View.GONE);
                gv_shooting.setVisibility(View.VISIBLE);
                ArrayList<String> path_list = resp.getStringArrayList("path_list");
                Log.d(TAG, "path_list.size()=" + path_list.size());
                //通过设配器显示图片
                ShootingAdapter adapter = new ShootingAdapter(this, path_list);
                gv_shooting.setAdapter(adapter);
            }

        }
    }

    //调整比例显示照片
    private void fillBitmap(Bitmap bitmap) {
        Log.d(TAG, "fillBitmap width= " + bitmap.getWidth() + ",height=" + bitmap.getHeight());
        if (bitmap.getHeight() > fl_content.getMeasuredHeight()) {
            ViewGroup.LayoutParams params = iv_photo.getLayoutParams();
            params.height = fl_content.getHeight();
            params.width = fl_content.getWidth();
            iv_photo.setLayoutParams(params);
        }
        iv_photo.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv_photo.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        //获取相机管理器
        CameraManager cm = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String[] ids;
        try {
            ids = cm.getCameraIdList();
        } catch (CameraAccessException e) {
            e.printStackTrace();
            return;
        }
        if (v.getId() == R.id.btn_catch_behind) {
            if (checkCamera(ids, CameraCharacteristics.LENS_FACING_FRONT + "")) {
                Intent intent = new Intent(this, TakeShootingActivity.class);
                intent.putExtra("type", CameraCharacteristics.LENS_FACING_FRONT);
                startActivityForResult(intent, 1);
            }
            else {
                Toast.makeText(this, "当前设备不支持后置摄像头", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btn_catch_front) {
            if (checkCamera(ids, CameraCharacteristics.LENS_FACING_BACK + "")) {
                Intent intent = new Intent(this, TakeShootingActivity.class);
                intent.putExtra("type", CameraCharacteristics.LENS_FACING_BACK);
                startActivity(intent);
            } else {
                Toast.makeText(this, "当前设备不支持前置摄像头", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //检查是否存在指定类型的摄像头
    private boolean checkCamera(String[] ids, String type) {
        boolean result = false;
        for (String  id : ids) {
            if (id.equals(type)) {
                result = true;
                break;
            }
        }
        return result;
    }

}
