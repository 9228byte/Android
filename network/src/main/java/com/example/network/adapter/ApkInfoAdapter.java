package com.example.network.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.network.R;
import com.example.network.bean.ApkInfo;

import java.util.ArrayList;


/**
 * ApkInfoAdapter
 *
 * @author lao
 * @date 2019/9/10
 */
public class ApkInfoAdapter extends BaseAdapter {
    private static final String TAG = "ApkInfoAdapter";
    private Context mContext;
    private ArrayList<ApkInfo> mApkList;

    public ApkInfoAdapter(Context mContext, ArrayList<ApkInfo> mApkList) {
        this.mContext = mContext;
        this.mApkList = mApkList;
    }

    @Override
    public int getCount() {
        return mApkList.size();
    }

    @Override
    public Object getItem(int position) {
        return mApkList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_apk, null);
            holder.tv_file_name = convertView.findViewById(R.id.tv_file_name);
            holder.tv_package_name = convertView.findViewById(R.id.tv_package_name);
            holder.tv_version_name = convertView.findViewById(R.id.tv_version_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ApkInfo item = mApkList.get(position);
        holder.tv_file_name.setText(item.file_name);
        holder.tv_package_name.setText(item.package_name);
        holder.tv_version_name.setText(item.version_name);
        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_file_name;
        public TextView tv_package_name;
        public TextView tv_version_name;
    }
}
