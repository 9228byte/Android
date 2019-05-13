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
import com.example.custom.util.StringUtil;

import java.util.ArrayList;


/**
 * TrafficInfoTraffic
 *
 * @author lao
 * @date 2019/4/24
 */
public class TrafficInfoAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<AppInfo> mAppInfoList;

    public TrafficInfoAdapter(Context context, ArrayList<AppInfo> infoList) {
        mContext = context;
        mAppInfoList = infoList;
    }

    @Override
    public int getCount() {
        return mAppInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAppInfoList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_traffic, null);
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_label = convertView.findViewById(R.id.tv_label);
            holder.tv_package_name = convertView.findViewById(R.id.tv_package_name);
            holder.tv_traffic = convertView.findViewById(R.id.tv_traffic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AppInfo item = mAppInfoList.get(position);
        if (item.icon != null) {
            holder.iv_icon.setImageDrawable(item.icon);
        }
        holder.tv_label.setText(item.label);
        holder.tv_package_name.setText(item.package_name);
        holder.tv_traffic.setText(StringUtil.formatData(item.traffic));
        return convertView;
    }

    public final class ViewHolder {
        public ImageView iv_icon;
        public TextView tv_label;
        public TextView tv_package_name;
        public TextView tv_traffic;
    }
}
