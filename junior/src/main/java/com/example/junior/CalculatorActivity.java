package com.example.junior;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junior.util.Arith;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "CalcullatorActivity";
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        tv_result = findViewById(R.id.tv_result);
        //设置tv_result内部文本的移动方式为滚动形式
        tv_result.setMovementMethod(new ScrollingMovementMethod());
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_devide).setOnClickListener(this);
        findViewById(R.id.btn_multiply).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_seven).setOnClickListener(this);
        findViewById(R.id.btn_eight).setOnClickListener(this);
        findViewById(R.id.btn_nine).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);
        findViewById(R.id.btn_five).setOnClickListener(this);
        findViewById(R.id.btn_six).setOnClickListener(this);
        findViewById(R.id.btn_miuus).setOnClickListener(this);
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_zero).setOnClickListener(this);
        findViewById(R.id.btn_sqrt).setOnClickListener(this);
        findViewById(R.id.btn_zero).setOnClickListener(this);
        findViewById(R.id.btn_dot).setOnClickListener(this);
        findViewById(R.id.btn_equal).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int resid = v.getId();      //获得当前按钮编号
        String inptText;
        if (resid == R.id.btn_sqrt) {       //如果是开根号
            inptText = "√";
        } else {       //除了开根号按钮之外的其他按钮
            inptText = ((TextView)v).getText().toString();
        }
        Log.d(TAG, "resid=" + resid + inptText.toString());

        if (resid == R.id.btn_clear) {      //点击清除
            clear("");
        } else if (resid == R.id.btn_cancel){   //点击取消
            if (operator.equals("")) {      //无操作符，则表示逐位取消前一个操作数
                if (firstNum.length() == 1) {
                    firstNum = "0";
                } else if (firstNum.length() > 0){
                    firstNum = firstNum.substring(0,firstNum.length() - 1);
                } else {
                    Toast.makeText(this,"没有可取消的数字了",Toast.LENGTH_SHORT).show();
                    return;
                }
                showText = firstNum;
                tv_result.setText(showText);
            } else {    //有操作符，则表示逐位取消后一个操作数
                if (nextNum.length() == 1){
                    nextNum = "";
                } else if (nextNum.length() > 0) {
                    nextNum = nextNum.substring(0,nextNum.length() - 1);
                } else {
                    Toast.makeText(this,"没有可取消的数字了",Toast.LENGTH_SHORT).show();
                }
                showText = showText.substring(0,showText.length() - 1);
                tv_result.setText(showText);
            }
        } else if(resid == R.id.btn_equal) {    //点击等于号
            if (operator.length() == 0 || operator.equals("=")) {
                Toast.makeText(this,"请输入运算符",Toast.LENGTH_SHORT).show();
                return;
            } else if (nextNum.length() <= 0) {
                Toast.makeText(this ,"请输入数字",Toast.LENGTH_SHORT).show();
                return;
            }
            if (caculate()) {   //计算成功
                operator = inptText;
                showText = showText + "=" + result;
                tv_result.setText(showText);
            } else {    //计算失败
                return;
            }
        } else if (resid == R.id.btn_plus || resid ==R.id.btn_miuus || resid == R.id.btn_multiply
        || resid == R.id.btn_devide){
            if (firstNum.length() <= 0) {
                Toast.makeText(this,"请输入数字",Toast.LENGTH_SHORT).show();
                return;
            }
            if (operator.length() == 0 || operator.equals("=") || operator.equals("√")){
                operator = inptText;//操作符
                showText = showText + operator;
                tv_result.setText(showText);
            } else {
                Toast.makeText(this,"请输入数字",Toast.LENGTH_SHORT).show();
                return;
            }
        } else if (resid == R.id.btn_sqrt) {    //点击开根号按钮
            if (firstNum.length() <= 0) {
                Toast.makeText(this,"请输入数字",Toast.LENGTH_SHORT).show();
                return;
            }
            if (Double.parseDouble(firstNum) < 0) {
                Toast.makeText(this,"开根号的数值不能小于0",Toast.LENGTH_SHORT);
                return;
            }
            //进行开根号运算
            result = String.valueOf(Math.sqrt(Double.parseDouble(firstNum)));
            firstNum = result;
            nextNum = "";
            operator = inptText;
            showText = showText + "√=" + result;
            Log.d(TAG, "result=" + result+"，firstNum="+ firstNum+",operator="+operator);
        } else {    //点击了其他按钮
            if (operator.equals("=")) { //上一个点击了等号按钮，则清空操作符
                operator = "";
                firstNum = "";
                showText = "";
            }
            if (operator.equals("")) {   //无操作符，则继续拼接前一个操作数
                firstNum = firstNum + inptText;
            } else {    //有操作符，则继续拼接后一个操作数
                nextNum = nextNum + inptText;
            }
            showText = showText + inptText;
            tv_result.setText(showText);
        }
        return;
    }

    private String operator = "";   //操作符
    private String firstNum = "";   //前一个操作数
    private String nextNum = "";     //后一个操作数
    private String result = "";     //当前计算结果
    private String showText = "";   //显示的文本内容

    //开始加减乘除四则运算，成功则返回true,计算失败返回falsec
    private boolean caculate(){
        if (operator.equals("+")) {     //当前相加结果
            result = String.valueOf(Arith.add(firstNum,nextNum));
        } else if (operator.equals("-")) {      //相当于减法结果
            result = String.valueOf(Arith.sub(firstNum,nextNum));
        } else if (operator.equals("×")) {
            result = String.valueOf(Arith.mul(firstNum,nextNum));
        } else if (operator.equals("÷")) {
            if ("0".equals(nextNum)){
                Toast.makeText(this,"被除数不能为0",Toast.LENGTH_LONG).show();
                return false;
            }
            else {
                result = String.valueOf(Arith.div(firstNum,nextNum));
            }
        }
        //运算结果打印在日志中
        Log.d(TAG, "result="+result);
        firstNum = result;
        nextNum = "";
        //运算成功返回true
        return true;
    }

    //清空并初始化
    private void clear(String text){
        showText = text;
        tv_result.setText(showText);
        operator = "";
        firstNum = "";
        nextNum = "";
        result = "";
    }
}
