package com.example.middle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SpinnerDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_dialog);
        initSpinner();
    }

    private void initSpinner() {
        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this, R.layout.item_select, startArray);
        starAdapter.setDropDownViewResource(R.layout.item_dropdown);
        Spinner sp = findViewById(R.id.sp_dialog);
        //设置下拉框的标题
        sp.setPrompt("请选择行星");
        //设置下拉框的数组适配器s  
        sp.setAdapter(starAdapter);
        //设置下拉框默认第一项
        sp.setSelection(0);
        sp.setOnItemSelectedListener(new MyselectedListener());
    }

    private String[] startArray = {"水星", "金星", "地球", "木星", "土星"};
    class MyselectedListener implements AdapterView.OnItemSelectedListener {
        //选择的处理方法
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(SpinnerDialogActivity.this, "您选择的是"+startArray[position], Toast.LENGTH_LONG).show();
        }

        //未选择时的处理方法，通常无需关注
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
