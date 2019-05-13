package com.example.custom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.custom.R;
import com.example.custom.util.Friend;
import com.example.custom.widget.DialogFriendRelation;
import com.example.custom.widget.DialogFriendRelation.onSelectRelationListener;

import java.util.List;


/**
 * FriendAdapter
 *
 * @author lao
 * @date 2019/4/20
 */
public class FriendAdapter extends BaseAdapter implements onSelectRelationListener {
    private Context mContext;
    private String[] names;
    private List<Friend> mFriendList;

    public FriendAdapter(Context context, List<Friend> friendList, OnDeleteListener listener) {
        mContext = context;
        mFriendList  = friendList;
        names = mContext.getResources().getStringArray(R.array.relation_name);
        deleteListener = listener;
    }

    @Override
    public int getCount() {
        return mFriendList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFriendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_friend, null);
            holder.rl_relation = convertView.findViewById(R.id.rl_relation);
            holder.tv_relation = convertView.findViewById(R.id.tv_relation);
            holder.ib_dropdown = convertView.findViewById(R.id.ib_dropdown);
            holder.tv_phone = convertView.findViewById(R.id.tv_phone);
            holder.rg_admit = convertView.findViewById(R.id.rg_admit);
            holder.rb_true = convertView.findViewById(R.id.rb_true);
            holder.rb_false = convertView.findViewById(R.id.rb_false);
            holder.tv_operation = convertView.findViewById(R.id.tv_operation);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_phone.setText(mFriendList.get(position).phone);
        holder.tv_relation.setText(mFriendList.get(position).relation);
        holder.rg_admit.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_true) {
                    mFriendList.get(position).admit_circle = true;
                } else if (checkedId == R.id.rb_false) {
                    mFriendList.get(position).admit_circle = false;
                }
            }
        });
        //设置删除按钮的点击监听器
        holder.tv_operation.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (deleteListener != null) {
                    deleteListener.onDeleteClick(position);
                }
            }
        });
        holder.rl_relation.setBackgroundResource(R.color.white);
        setRelationChangeListener(holder, position);
        return convertView;
    }

    //设置朋友变化监听器
    private void setRelationChangeListener(final ViewHolder holder, final int position) {
        holder.rl_relation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = 0;
                for (int i = 0; i < names.length; i++) {
                    if (names[i].equals(mFriendList.get(position).relation)) {
                        selected = i;
                        break;
                    }
                }
                //构建一个朋友关系的选择对话框
                DialogFriendRelation dialog = new DialogFriendRelation(mContext, FriendAdapter.this);
                //显示朋友关系对话框
                dialog.show(getCount() - position, selected);
                holder.rl_relation.setBackgroundResource(R.color.grey);
            }
        });
    }

    public List<Friend> getFriends() {
        return mFriendList;
    }

    public void setRelation(int gap, String name, String value) {
        mFriendList.get(getCount() - gap).relation = name;
        mFriendList.get(getCount() - gap).value = value;
        //通知适配器数据发生变化，以便适配器及时刷新列表显示
        notifyDataSetChanged();
    }

    private OnDeleteListener deleteListener;

    //定义一个删除朋友关系的监听器接口
    public interface OnDeleteListener {
        void onDeleteClick(int position);
    }

    public class ViewHolder {
        public RelativeLayout rl_relation;
        public TextView tv_relation;
        public ImageButton ib_dropdown;
        public TextView tv_phone;
        public RadioGroup rg_admit;
        public RadioButton rb_true;
        public RadioButton rb_false;
        public TextView tv_operation;
    }
}
