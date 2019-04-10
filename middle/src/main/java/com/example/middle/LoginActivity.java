package com.example.middle;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.middle.Util.ViewUtil;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup rg_login;
    private RadioButton rb_password;
    private RadioButton rb_verifycode;
    private EditText et_phone;
    private TextView tv_password;
    private EditText et_password;
    private Button btn_forget;
    private CheckBox ck_remember;

    private int mRequestCode = 0;       //跳转页面时的请求代码
    private int mType = 0;      //用户类型
    private boolean bRemember = false;
    private String mPassword = "111111";    //默认密码
    private String mVerifyCode;     //验证码
    private SharedPreferences sharedPreferences;    //共享密码参数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 获取控件
        rg_login = findViewById(R.id.rg_login);
        rb_password = findViewById(R.id.rb_password);
        rb_verifycode = findViewById(R.id.rb_verifycode);
        et_phone = findViewById(R.id.et_phone);
        tv_password = findViewById(R.id.tv_password);
        et_password = findViewById(R.id.et_password);
        btn_forget = findViewById(R.id.btn_forget);
        ck_remember = findViewById(R.id.ck_remember);
        //设置监听器
        rg_login.setOnCheckedChangeListener(new RadioListener());       //登录类型
        ck_remember.setOnCheckedChangeListener(new CheckListener());    //记住密码选项
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone));     //手机编辑框监听器
        et_password.addTextChangedListener(new HideTextWatcher(et_password));   //密码编辑框监听器
        btn_forget.setOnClickListener(this);        //忘记密码监听器
        findViewById(R.id.btn_login).setOnClickListener(this);  //登录监听器
        initTypeSpinner();      //初始化下拉框
        sharedPreferences = getSharedPreferences("share_login", MODE_PRIVATE);

        String phone = sharedPreferences.getString("phone","");
        String password = sharedPreferences.getString("password", "");
        et_phone.setText(phone);
        et_password.setText(password);
    }

    private String[] typeArray = {"个人用户", "公司用户"};

    //初始化用户类型下拉框
    private void initTypeSpinner() {
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String >(this,
                R.layout.item_select, typeArray);
        typeAdapter.setDropDownViewResource(R.layout.item_dropdown);
        Spinner sp_type = findViewById(R.id.sp_type);
        //设置下拉框标题
        sp_type.setPrompt("请选择用户类型");
        sp_type.setAdapter(typeAdapter);
        sp_type.setSelection(mType);
        sp_type.setOnItemSelectedListener(new TypeSelectedListener());
    }

    //定义用户类型监听器
    class TypeSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mType = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    //定义登录方式监听器
    private class RadioListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.rb_password) {    //密码登录
                tv_password.setText("登录密码：");
                et_password.setHint("请输入密码");
                btn_forget.setText("忘记密码");
                ck_remember.setVisibility(View.VISIBLE);
            }
            else if (checkedId == R.id.rb_verifycode) { //验证码登录
                tv_password.setText("验证码：    ");
                et_password.setHint("请输入验证码");
                btn_forget.setText("获取验证码");
                ck_remember.setVisibility(View.INVISIBLE);
            }
        }
    }

    //定义是否记住密码的勾选监听器
    private class CheckListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.ck_remember) {
                bRemember = isChecked;
            }
        }
    }

    //定义编辑框的文本变化监听器
    private class HideTextWatcher implements TextWatcher {
        private EditText mView;        //编辑框视图
        private int mMaxLength;     //文本框限制输入长度
        private CharSequence mStr;      //字符串

        HideTextWatcher(EditText v) {
            super();
            mView = v;
            mMaxLength = ViewUtil.getMaxLength(v);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        //检测到变化时，实时获取
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mStr = s;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mStr == null || mStr.length() == 0) {   //为空
                return;
            }
            if ((mStr.length() == 11) && (mMaxLength == 11) ||
                    (mStr.length() == 6) && (mMaxLength ==6)) {     //检测手机号码，或者验证码，密码
                //关闭输入法
                ViewUtil.hideOneInputMethod(LoginActivity.this, mView);
            }
        }
    }
    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();       //手机号码
        if (v.getId() == R.id.btn_forget) {     //忘记密码
            if (phone.length() < 11){
                Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                return;
            }
           if (rb_password.isChecked()) {       //密码登录
               //如果忘记密码。设置意图
               Intent intent = new Intent(this, LoginForgetActivity.class);
               intent.putExtra("phone", phone);
               startActivityForResult(intent, mRequestCode);
           } else if (rb_verifycode.isChecked()) {      //验证码登录
               mVerifyCode = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
               AlertDialog.Builder builder = new AlertDialog.Builder(this);
               builder.setTitle("请记住验证码");
               builder.setMessage("手机号" + phone + ",本次验证码是" + mVerifyCode + "，请输入验证码");
               builder.setPositiveButton("好的", null);
               AlertDialog alert = builder.create();
               alert.show();
           }
        } else if (v.getId() == R.id.btn_login) {
            if (phone.length() < 11) {
                Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (rb_password.isChecked()) {
                if (!et_password.getText().toString().equals(mPassword)) {
                    Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                } else {
                    loginSuccess();
                }
            }else if (rb_verifycode.isChecked()) {
                if (!et_password.getText().toString().equals(mVerifyCode)) {
                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                } else {
                    loginSuccess();
                }
            }
        }
    }

    //从后一个页面携带参数返回当前页面
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == mRequestCode && data != null) {
            mPassword = data.getStringExtra("new_password");
        }
    }

    //从修改面页面返回登录页面，要清空密码的输入框
    @Override
    protected void onRestart() {
        et_password.setText("");
        super.onRestart();
    }

    //校验通过
    private void loginSuccess() {
        String desc = String.format("您的手机号码是%s，类型是%s。恭喜你通过验证登录，点击“确定”返回上一个页面",
                 et_phone.getText().toString(), typeArray[mType]);
        //弹出提醒对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
        builder.setMessage(desc);
        builder.setPositiveButton("确定返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("我再看看", null);
        AlertDialog alert = builder.create();
        alert.show();

        if(bRemember) {
            ck_remember.setChecked(true);
            SharedPreferences.Editor editor = sharedPreferences.edit(); //获得编辑对象
            editor.putString("phone", et_phone.getText().toString());
            editor.putString("password",et_password.getText().toString());
            editor.commit();
        }
    }
}
