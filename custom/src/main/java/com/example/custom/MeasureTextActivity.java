package com.example.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.custom.util.MeasureUtil;

/**
 * MeasureTextActivity
 *
 * @author lao
 * @date 2019/4/19
 */

public class MeasureTextActivity extends AppCompatActivity {
    private TextView tv_desc, tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_text);
        tv_desc = findViewById(R.id.tv_desc);
        tv_text = findViewById(R.id.tv_text);
        initSizeSpinner();
    }
    private String[] descArray = {"12sp", "15sp", "17sp", "20sp", "22sp", "25sp", "27sp", "30sp"};

    private int[] sizeArray = {12, 15, 17, 20, 22, 25, 27, 30};

    private void initSizeSpinner() {
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this,
                R.layout.item_select, descArray);
        Spinner sp_size = findViewById(R.id.sp_size);
        sp_size.setPrompt("请选择文字大小");
        sp_size.setAdapter(sizeAdapter);
        sp_size.setOnItemSelectedListener(new SizeSelectedListener());
        sp_size.setSelection(0);
    }

    class SizeSelectedListener implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String text = tv_text.getText().toString();
            int textSize = sizeArray[position];
            tv_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            int width = (int) MeasureUtil.getTextWidth(text, textSize);
            int height = (int) MeasureUtil.getTextHeight(text, textSize);
            String desc = String.format("下面文字的宽度是%d，高度是%d", width, height);
            tv_desc.setText(desc);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}


