package com.example.custom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.custom.util.Utils;
import com.example.custom.widget.OffsetLayout;

/**
 * OnLayoutActivity
 *
 * @author lao
 * @date 2019/4/19
 */

public class OnLayoutActivity extends AppCompatActivity {
    private static final String TAG = "OnLayoutActivity";
    private OffsetLayout ol_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_layout);
        ol_content = findViewById(R.id.ol_content);
        initOffsetSpinner();
    }

    private void initOffsetSpinner() {
        ArrayAdapter<String> offsetAdapter = new ArrayAdapter<String>(this,
                R.layout.item_select, descArray);
        Spinner sp_offset = findViewById(R.id.sp_offset);
        sp_offset.setPrompt("请选择偏移大小");
        sp_offset.setAdapter(offsetAdapter);
        sp_offset.setOnItemSelectedListener(new OffsetSelectedListener());
        sp_offset.setSelection(0);
    }

    private String[] descArray = {"无偏移", "向左偏移50", "向右偏移50", "向上偏移50", "向下偏移50"};
    private int[] offsetArray = {0 , -50, 50, -50, 50};

    class OffsetSelectedListener implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int offset = Utils.dip2px(OnLayoutActivity.this, offsetArray[position]);
            if (position == 0 || position == 1 || position == 2) {
                //设置偏移布局在水平方向上的偏移量
                ol_content.setOffsetHorizontal(offset);
            } else if (position == 3 || position == 4) {
                ol_content.setOffsetVertical(offset);
            }
            Log.d(TAG, "onItemSelected: + position" + position);
            Toast.makeText(OnLayoutActivity.this, descArray[position],Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
