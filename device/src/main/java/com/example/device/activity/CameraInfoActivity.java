package com.example.device.activity;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.device.R;
import com.example.device.adapter.CameraAdapter;
import com.example.device.bean.CameraInfo;

import java.util.ArrayList;

/**
 * CameraInfoActivity
 *
 * @author lao
 * @date 2019/6/16
 */

public class CameraInfoActivity extends AppCompatActivity {
    private static final String TAG = "CameraInfoActivity";
    private ListView lv_camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_info);
        lv_camera = findViewById(R.id.lv_camera);
        checkCamera();
    }

    private void checkCamera() {
        ArrayList<CameraInfo> cameraList = new ArrayList<CameraInfo>();
        int cameraCount = Camera.getNumberOfCameras();
        Log.d(TAG, "checkCamera: 摄像头个数=%d" + cameraCount);
        for (int i = 0 ; i < cameraCount ; i++) {
            CameraInfo info = new CameraInfo();
            Camera camera = Camera.open(i);
            Camera.Parameters parames = camera.getParameters();     //获取摄像头参数
            info.camera_type = (i == 0) ? "前置" : "后置";
            info.flash_mode = parames.getFlashMode();      //闪光模式
            info.focus_mode = parames.getFocusMode();       //对焦模式
            info.scene_mode = parames.getSceneMode();       //场景模式
            info.color_effect = parames.getColorEffect();       //摄像头的颜色效果
            info.white_balance = parames.getWhiteBalance();     //白平衡
            info.max_zoom = parames.getMaxZoom();       //获取西大缩放比例
            info.zoom = parames.getZoom();      //获取当前摄像头的缩放比例
            info.resolutionList = parames.getSupportedPictureSizes();       //摄像头支持的预览分辨率
            camera.release();
            cameraList.add(info);
        }
        CameraAdapter adapter = new CameraAdapter(this, cameraList);
        lv_camera.setAdapter(adapter);
    }
}
