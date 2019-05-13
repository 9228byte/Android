package com.example.custom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.custom.R;

import java.util.ArrayList;
import java.util.Collections;


/**
 * FriendRelationAdapter
 *
 * @author lao
 * @date 2019/4/20
 */
public class FriendRelationAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mContentList;
    private int mSelected;

    public FriendRelationAdapter(Context context, String[] content_list, int selected) {
        mContext = context;
        mContentList = new ArrayList<String>();
        Collections.addAll(mContentList, content_list);
        mSelected = selected;
    }

    @Override
    public int getCount() {
        return mContentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mContentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_friend_relation, null);
            holder.tv_friend_relation = convertView.findViewById(R.id.tv_friend_relation);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_friend_relation.setText(mContentList.get(position));
        //如果当前元素正是选中的记录则高亮显示
        if (position == mSelected) {
            holder.tv_friend_relation.setBackgroundResource(R.color.blue);
            holder.tv_friend_relation.setTextColor(mContext.getResources().getColor(R.color.white));
        }
        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_friend_relation;
    }
}
