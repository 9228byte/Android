package com.example.custom.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.custom.R;
import com.example.custom.adapter.FriendRelationAdapter;
import com.example.custom.util.Utils;

/**
 * DialogFriendRelation
 *
 * @author lao
 * @date 2019/4/20
 */
public class DialogFriendRelation implements OnItemClickListener, OnDismissListener {
    private Context mContext;
    private Dialog dialog;
    private View view;
    private GridView gv_relation;
    private LinearLayout ll_relation_agp;
    private FriendRelationAdapter friendRelationAdapter;
    private String[] relation_name_array;
    private String[] relation_value_array;
    private int mGap;
    private int mSelected;

    public DialogFriendRelation(Context context, onSelectRelationListener listener) {
        mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_friend_relation, null);
        dialog = new Dialog(context, R.style.dialog_layout_bottom_transparent);
        gv_relation = view.findViewById(R.id.gv_relation);
        ll_relation_agp = view.findViewById(R.id.ll_relation_gap);
        relation_name_array = context.getResources().getStringArray(R.array.relation_name);
        relation_value_array = context.getResources().getStringArray(R.array.relation_value);
        mOnSelectRelationListener = listener;
    }

    public void show(final int gap, final int selected) {
        mGap = gap;
        mSelected = selected;
        int dip_48 = Utils.dip2px(mContext, 48);
        int dip_2 = Utils.dip2px(mContext, 2);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, dip_48 * (gap + 1) - dip_2);
        ll_relation_agp.setLayoutParams(params);
        ll_relation_agp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //朋友关系适配器
        friendRelationAdapter = new FriendRelationAdapter(mContext, relation_name_array, mSelected);
        gv_relation.setAdapter(friendRelationAdapter);
        gv_relation.setOnItemClickListener(this);
        dialog.getWindow().setContentView(view);
        dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
        dialog.setOnDismissListener(this);
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public boolean isShowing() {
        if (dialog != null) {
            return dialog.isShowing();
        } else {
            return false;
        }
    }

    //点击某个网格项的时候，便触发OnItemClick
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mSelected = position;
        dismiss();
    }

    //声明一个关系选择监听器对象
    private onSelectRelationListener mOnSelectRelationListener;

    //定义一个关系选择的监听器接口
    public interface onSelectRelationListener {
        void setRelation(int gap, String name, String value);
    }

    //关闭对话框的时候，便触发onDismiss方法
    @Override
    public void onDismiss(DialogInterface dialog) {
        //回调监听器的setRelation方法
        mOnSelectRelationListener.setRelation(mGap, relation_name_array[mSelected], relation_value_array[mSelected]);
    }

}
