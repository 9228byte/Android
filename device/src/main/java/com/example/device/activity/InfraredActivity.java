package com.example.device.activity;

import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.hardware.ConsumerIrManager.CarrierFrequencyRange;
import android.widget.Toast;

import com.example.device.R;
import com.example.device.util.DateUtil;

/**
 * InfraredActivity
 *
 * @author lao
 * @date 2019/9/9
 */

public class InfraredActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "InfraredActivity";
    private TextView tv_infrared;
    private ConsumerIrManager cim;      //红外线管理器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_frared);
        findViewById(R.id.btn_send).setOnClickListener(this);
        findViewById(R.id.btn_receive).setOnClickListener(this);
        tv_infrared = findViewById(R.id.tv_infrared);
        initInfrared();
    }

    //初始化红外线管理器
    private void initInfrared(){
        cim = (ConsumerIrManager) getSystemService(Context.CONSUMER_IR_SERVICE);
        if (!cim.hasIrEmitter()) {
            tv_infrared.setText("当前设备不支持红外遥控");
        } else {
            tv_infrared.setText("当前手机支持红外遥控");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send) {
            // NEC协议的红外编码格式通常为：
            // 引导码+用户码+数据码+数据反码+结束码。
            // 下面是一个扫地机器人的开关按键编码，用户码为4055，数据码为44。
            int[] pattern = {9000,4500, // 开头两个数字表示引导码
                    // 下面两行表示用户码
                    560,560, 560,560, 560,560, 560,560, 560,560, 560,560, 560,1680, 560,560,
                    560,1680, 560,560, 560,1680, 560,560, 560,1680, 560,560, 560,1680, 560,560,
                    // 下面一行表示数据码
                    560,560, 560,560, 560,1680, 560,560, 560,560, 560,560, 560,1680, 560,560,
                    // 下面一行表示数据反码
                    560,1680, 560,1680, 560,560, 560,1680, 560,1680, 560,1680, 560,560, 560,1680,
                    560,20000}; // 末尾两个数字表示结束码
            // 发射指定编码格式的红外信号。普通家电的红外发射频率一般为38KHz
            cim.transmit(38000, pattern);
            String hint = DateUtil.getNowDateTime() + "已发射红外信号，请观察扫地机器人是否有反应";
            tv_infrared.setText(hint);
        } else if (v.getId() == R.id.btn_receive) {
            Toast.makeText(this, "获取信息", Toast.LENGTH_SHORT).show();
            CarrierFrequencyRange[] freqs = cim.getCarrierFrequencies();
            String result = "当前手机的红外载波频率范围为：\n";
            for (CarrierFrequencyRange range : freqs) {
                result = String.format("%s    %d - %d\n", result, range.getMinFrequency(), range.getMaxFrequency());
            }
            tv_infrared.setText(result);
        }
    }

}
