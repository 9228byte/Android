package com.example.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.custom.widget.DrawRelativeLayout;

;

/**
 * ShowDrawActivity
 *
 * @author lao
 * @date 2019/4/19
 */

public class ShowDrawActivity extends AppCompatActivity implements OnClickListener {
    private DrawRelativeLayout drl_content;
    private Button btn_center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_draw);
        drl_content = findViewById(R.id.drl_content);
        btn_center = findViewById(R.id.btn_center);
        initTypeSpinner();
    }

    private void initTypeSpinner() {
        ArrayAdapter<String> drawAdapter = new ArrayAdapter<String>(this,
                R.layout.item_select, descArray);
        Spinner sp_draw = findViewById(R.id.sp_draw);
        sp_draw.setPrompt("请选择绘图方式");
        sp_draw.setAdapter(drawAdapter);
        sp_draw.setOnItemSelectedListener(new DrawSelectedListener());
        sp_draw.setSelection(0);
    }

    private String[] descArray = {"不画图", "画矩形", "画圆角矩形", "画圆圈", "画椭圆", "onDraw画叉叉", "dispatch画叉叉"};
    private int[] typeArray = {0, 1, 2, 3, 4, 5, 6};

    class DrawSelectedListener implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int type = typeArray[position];
            if (type == 5 || type == 6) {
                btn_center.setVisibility(View.VISIBLE);
            } else {
                btn_center.setVisibility(View.GONE);
            }
            //绘制布局的绘制类型
            drl_content.setmDrawType(type);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    @Override
    public void onClick(View v) {

    }
}
