package com.example.custom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.custom.R;
import com.example.custom.bean.AppInfo;

import java.util.ArrayList;


/**
 * AppInfoAdapter
 *
 * @author lao
 * @date 2019/4/22
 */
public class AppInfoAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<AppInfo> mAppinfoList;

    public AppInfoAdapter(Context context, ArrayList<AppInfo> appInfoList) {
        mAppinfoList = appInfoList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mAppinfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAppinfoList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_appinfo, null);
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_label = convertView.findViewById(R.id.tv_label);
            holder.tv_package_name = convertView.findViewById(R.id.tv_package_name);
            holder.tv_uid = convertView.findViewById(R.id.tv_uid);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AppInfo item = mAppinfoList.get(position);
        holder.iv_icon.setImageDrawable(item.icon);
        holder.tv_label.setText(item.label);
        holder.tv_package_name.setText(item.package_name);
        holder.tv_uid.setText("" + item.uid);
        return convertView;
    }

    public final class ViewHolder {
        public ImageView iv_icon;
        public TextView tv_label;
        public TextView tv_package_name;
        public TextView tv_uid;
    }
}
