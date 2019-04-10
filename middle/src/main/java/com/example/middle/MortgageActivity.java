package com.example.middle;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.middle.bean.Repayment;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MortgageActivity extends AppCompatActivity implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {
    private EditText et_price;
    private EditText et_loan;
    private TextView tv_loan;
    private RadioGroup rg_payment;
    private CheckBox ck_business;
    private EditText et_businesss;
    private CheckBox ck_accumulation;
    private EditText et_accumulation;
    private TextView tv_payment;

    private boolean isInterest = true;  //是否等额本息
    private boolean hasBusiness = true; //是否存在商业贷款
    private boolean hasAccumulation = false;    //是否存在公积金贷款
    private int mYear;      //还款年限
    private double mBusinessRatio;  //商业贷款利率
    private double mAccumulationRatio;  //公积金贷款利率

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage);

        et_price = findViewById(R.id.et_price);
        et_loan = findViewById(R.id.et_loan);
        tv_loan = findViewById(R.id.tv_loan);
        rg_payment = findViewById(R.id.rg_payment);
        rg_payment.setOnCheckedChangeListener(this);
        ck_business = findViewById(R.id.ck_business);
        ck_business.setOnCheckedChangeListener(this);
        et_businesss = findViewById(R.id.et_business);
        ck_accumulation = findViewById(R.id.ck_accumulation);
        ck_accumulation.setOnCheckedChangeListener(this);
        et_accumulation = findViewById(R.id.et_accumulation);
        tv_payment = findViewById(R.id.tv_payment);
        //设置按钮监听
        findViewById(R.id.btn_loan).setOnClickListener(this);
        findViewById(R.id.btn_calculate).setOnClickListener(this);
        initYearSpinner();
        initRatioSpinner();
    }

    private String[] yearDescArray = {"5年", "10年", "15年", "20年", "30年"};
    private int[] yearArray = {5, 10, 15, 20, 30};

    //初始化年利率
    private void initYearSpinner(){
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this,
                R.layout.item_select, yearDescArray);
        yearAdapter.setDropDownViewResource(R.layout.item_dropdown);
        Spinner sp_year  = findViewById(R.id.sp_year);
        sp_year.setPrompt("请选择贷款年限");
        sp_year.setAdapter(yearAdapter);
        sp_year.setSelection(0);
        sp_year.setOnItemSelectedListener(new YearSelectedListener());
    }

    //定义一个贷款年限的选择监听器
    class YearSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            mYear = yearArray[arg2];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    //初始化基准利率下拉框
    private void initRatioSpinner() {
        ArrayAdapter<String> ratioAdapter = new ArrayAdapter<String>(this,
                R.layout.item_select, ratioDescArray);
        ratioAdapter.setDropDownViewResource(R.layout.item_dropdown);
        Spinner sp_ratio = findViewById(R.id.sp_ratio);
        sp_ratio.setPrompt("请选择基准利率");
        sp_ratio.setAdapter(ratioAdapter);
        sp_ratio.setSelection(0);
        sp_ratio.setOnItemSelectedListener(new RatioSelectListenner());
    }

    private String[] ratioDescArray = {
            "2015年10月24日 五年期商贷利率 4.90%  公积金利率 3.25%",
            "2015年08月26日 五年期商贷利率 5.15%  公积金利率 3.25%",
            "2015年06月28日 五年期商贷利率 5.40%  公积金利率 3.50%",
            "2015年05月11日 五年期商贷利率 5.65%  公积金利率 3.75%",
            "2015年03月01日 五年期商贷利率 5.90%  公积金利率 4.00%",
            "2014年11月22日 五年期商贷利率 6.15%  公积金利率 4.25%",
            "2014年07月06日 五年期商贷利率 6.55%  公积金利率 4.50%",
    };
    private double[] businessArray = {4.90, 5.15, 5.40, 5.65, 5.90, 6.15, 6.55};
    private double[] accumulationArray = {3.25, 3.25, 3.50, 3.75, 4.00, 4.25, 4.50};

    //定义一个基准利率监听器
    class RatioSelectListenner implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mBusinessRatio = businessArray[position];
            mAccumulationRatio = accumulationArray[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_loan) {   //点击了按钮“计算贷款”
            if (TextUtils.isEmpty(et_price.getText().toString())) {
                Toast.makeText(this, "购房总价不能为空",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(et_loan.getText().toString())) {
                Toast.makeText(this, "按揭部分不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            showLoan();     //显示计算好的贷款总额
        }else if (v.getId() == R.id.btn_calculate) {    //点击了按钮“计算还款明细”
            if (hasBusiness && TextUtils.isEmpty(et_businesss.getText().toString())) {
                Toast.makeText(this, "商业贷款总额不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (hasAccumulation && TextUtils.isEmpty(et_accumulation.getText().toString())) {
                Toast.makeText(this, "公积金贷款总额不能为空",Toast.LENGTH_SHORT).show();
                return;
            }
            if (!hasBusiness && !hasAccumulation) {
                Toast.makeText(this, "选择商业贷款或者公积金贷款", Toast.LENGTH_SHORT).show();
                return;
            }
            showRePayment();        //显示计算好的还款明细
        }
    }

    //根据贷款的相关条件，计算还款总额、利息总额、以及月供
    private void showLoan() {
        double total = Double.parseDouble(et_price.getText().toString());
        double rate = Double.parseDouble(et_loan.getText().toString())/100;
        String desc = String.format("您的贷款利率为%s万元", formatDecimal(total * rate, 2));
        tv_loan.setText(desc);
    }

    //根据贷款的相关条件，计算还款总额、利息总额，以及月供
    private void showRePayment() {
        Repayment businessResult = new Repayment();
        Repayment accumulationResult = new Repayment();
        if (hasBusiness) {  //申请了商业贷款
            double businessLoad = Double.parseDouble(et_businesss.getText().toString()) * 10000;
            double businessTime = mYear * 12;
            double businessRate = mBusinessRatio / 100;
            //计算商业贷款部分的还款明细
            businessResult = calMortgage(businessLoad, businessTime, businessRate, isInterest);
        }
        if (hasAccumulation) {  //申请了公积金贷款
            double accumulationLoad = Double.parseDouble(et_accumulation.getText().toString()) * 10000;
            double accumulationTime = mYear * 12;
            double accumulationRate = mAccumulationRatio / 100;
            //计算公积金贷款部分的还款明细
            accumulationResult = calMortgage(accumulationLoad, accumulationTime, accumulationRate, isInterest);
        }
        String desc = String.format("您的贷款总额为%s万元", formatDecimal(
                (businessResult.mTotal + accumulationResult.mTotal) /10000, 2));
        desc = String.format("%s\n   还款总额为%s万元", desc, formatDecimal(
                (businessResult.mTotal+accumulationResult.mTotalInterest) / 10000,2));
        desc = String.format("%s\n其中利息总额为%s万元", desc, formatDecimal(
                (businessResult.mTotalInterest + accumulationResult.mTotalInterest) / 10000, 2));
        desc = String.format("%s\n   还款总时间为%d月", desc, mYear * 12);
        if (isInterest) {       //如果是等额本息
            desc = String.format("%s\n每月还款金额为%s元", desc, formatDecimal(
                    businessResult.mMonthRepayment + accumulationResult.mMonthRepayment ,2));
        } else {        //如果是等额本金
            desc = String.format("%s\n首月还款金额为%s元，其后每余额递减%s元", desc, formatDecimal(
                    businessResult.mMonthRepayment + accumulationResult.mMonthRepayment ,2),
                    formatDecimal(businessResult.mMonthMinus + accumulationResult.mMonthMinus, 2));
        }
        tv_payment.setText(desc);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_interest) {    //选择了等额本息方式
            isInterest  = true;
        } else if (checkedId == R.id.rb_principal) {    //选择了等额本金方式
            isInterest = false;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.ck_business) {//选择了商业贷款
            hasBusiness = isChecked;
        } else if (buttonView.getId() == R.id.ck_accumulation) {        //勾选了公积金贷款
            hasAccumulation = isChecked;
        }
    }

    //精确到小数点后几位数
    private String formatDecimal(double value, int dight) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(dight, RoundingMode.HALF_UP);
        return bd.toString();
    }

    //根据贷款金额，还款年限，基准利率，计算还款信息
    private Repayment calMortgage(double ze, double nx, double rate, boolean bInterest) {
        double zem = (ze * rate / 12 * Math.pow((1 + rate / 12), nx))
                / (Math.pow((1 + rate / 12), nx) - 1);

        double amount = zem * nx;
        double rateAmount = amount - nx;

        double benjinnm = ze / nx;
        double lixim = ze * (rate / 12);
        double diff = benjinnm * (rate / 12);
        double huankaunm = benjinnm + lixim;
        double zuihoukuan = diff + benjinnm;
        double av = (huankaunm + zuihoukuan) / 2;
        double zong = av + nx;
        double zongli = zong - ze;
        Repayment result = new Repayment();
        result.mTotal = ze;
        if (bInterest) {
            result.mMonthRepayment = zem;
            result.mTotalInterest = rateAmount;
        } else {
            result.mMonthRepayment = huankaunm;
            result.mMonthMinus = diff;
            result.mTotalInterest = zongli;
        }
        return result;
    }
}