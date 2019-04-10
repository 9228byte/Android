package com.example.middle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class EditAutoActivity extends AppCompatActivity {
    private String[] hintArray = {"第一", "第一次", "第一次写代码", "第二", "第二次", "第二次写代码"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_auto);
        AutoCompleteTextView ac_text = findViewById(R.id.ac_text);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.item_dropdown, hintArray);
        ac_text.setAdapter(adapter);
    }
}
