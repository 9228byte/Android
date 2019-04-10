package com.example.middle;

import android.icu.util.EthiopicCalendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.middle.Util.ViewUtil;

public class EditHideActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hide);
        //在布局文件中获取名叫ll_hide的线性布局
        LinearLayout ll_hide = findViewById(R.id.ll_hide);
        //从布局文件中获取名叫et_phone
        EditText et_phone = findViewById(R.id.et_phone);
        EditText et_password = findViewById(R.id.et_password);
        et_other = findViewById(R.id.et_other);
        ll_hide.setOnClickListener(this);
        //给手机号码编辑框添加文本变化监听器
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone));
        //给密码编辑康添加文本变化监听器
        et_password.addTextChangedListener(new HideTextWatcher(et_password));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_hide) {
            // 实际上不只是et_other的软键盘会关闭，其它编辑框的软键盘也会关闭
            // 因为方法内部去获取视图的WindowToken，这个Token在每个页面上都是唯一的
            ViewUtil.hideOneInputMethod(EditHideActivity.this, et_other);
        }
    }

    //定义一个编辑框监听器，在输入文本达到指定长度时自动隐藏输入法
    private class HideTextWatcher implements TextWatcher {
        private EditText mView;
        private int mMaxLength;
        private CharSequence mStr;

        public HideTextWatcher(EditText v) {
            super();
            mView = v;
            //通过反射机制获取编辑框的最大长度
            mMaxLength = ViewUtil.getMaxLength(v);
        }

        //在编辑框的输入文本变化前触发
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        //在编辑框的输入文本变化时触发
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mStr = s;
        }

        //在编辑框输入文本变化后触发
        @Override
        public void afterTextChanged(Editable s) {
            if (mStr == null || mStr.length() ==0) {
                return;
            }
            //输入文本达到11位（如手机号码）时关闭输入法
            if (mStr.length() == 11 && mMaxLength == 11) {
                ViewUtil.hideAllInputMethod(EditHideActivity.this);
            }
            //输入文本达到6位（如登录密码）时关闭输入法
            if (mStr.length() == 6 && mMaxLength ==6) {
                ViewUtil.hideOneInputMethod(EditHideActivity.this, mView);
            }
        }
    }
}
