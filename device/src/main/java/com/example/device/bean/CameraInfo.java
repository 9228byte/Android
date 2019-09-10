package com.example.device.bean;

import java.util.List;

/**
 * Camera
 *
 * @author lao
 * @date 2019/5/14
 */
public class CameraInfo {
    public String camera_type;      //摄像头类型
    public String flash_mode;        //闪光模式
    public String focus_mode;       //聚焦
    public String scene_mode;       //
    public String color_effect;     //颜色
    public String white_balance;        //白平衡
    public int max_zoom;
    public int zoom;
    public List<android.hardware.Camera.Size> resolutionList;
}
