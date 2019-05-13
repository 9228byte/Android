package com.example.custom.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.custom.R;

/**
 * CustomMonthDialog
 *
 * @author lao
 * @date 2019/4/20
 */
public class CustomMonthDialog implements View.OnClickListener {
    private Dialog dialog;
    private View view;
    private TextView tv_title;
    private DatePicker dp_date;

    public CustomMonthDialog(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_date, null);
        dialog = new Dialog(context, R.style.CustomDateDialog);
        tv_title = view.findViewById(R.id.tv_title);
        dp_date = view.findViewById(R.id.dp_date);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);
        tv_title.setText("请选择下拉月份");
        //获取年月日下拉列表项
        ViewGroup vg = ((ViewGroup) ((ViewGroup) dp_date.getChildAt(0)).getChildAt(0));
        if (vg.getChildCount() == 3) {
            vg.getChildAt(2).setVisibility(View.GONE);
        } else if (vg.getChildCount() == 5) {
            vg.getChildAt(3).setVisibility(View.GONE);
            vg.getChildAt(4).setVisibility(View.GONE);
        }
    }

    //设置月份对话框的标题文本
    public void setTitle(String title) {
        tv_title.setText(title);
    }

    //设置月份对话框内部的年月，以及月份变监听器
    public void setDate(int year, int month, int day, OnMonthSetListener listener) {
        dp_date.init(year, month, day, null);
        mMonthSetListener = listener;
    }

    //显示月份对话框
    public void show() {
        //设置对话框的内容视图
        dialog.getWindow().setContentView(view);
        //设置对话框窗口的布局参数
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    //关闭月份对话框
    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    //判断月份对话框是否显示
    public boolean isShowing() {
        if (dialog != null) {
            return dialog.isShowing();
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_ok) {
            dismiss();
            if (mMonthSetListener != null) {
                dp_date.clearFocus();       //清除日期选择器的焦点
                //回调监听器的onMonthSet方法
                mMonthSetListener.onMonthSet(dp_date.getYear(), dp_date.getMonth() + 1);
            }
        }
    }

    //声明一个月份变更监听器
    private OnMonthSetListener mMonthSetListener;

    //定义一个月份变更监听器接口
    public interface OnMonthSetListener {
        void onMonthSet(int year, int monthOfYear);
    }
}
