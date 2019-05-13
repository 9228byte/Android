package com.example.custom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.custom.R;
import com.example.custom.bean.Planet;

import java.util.ArrayList;

/**
 * PlanetListAdapter
 *
 * @author lao
 * @date 2019/4/19
 */
public class PlanetListAdapter extends BaseAdapter implements
OnItemClickListener, OnItemLongClickListener {
    private Context mContext;
    private ArrayList<Planet> mPlanetList = new ArrayList<Planet>();

    public PlanetListAdapter(Context context, ArrayList<Planet> planet_List) {
        mContext = context;
        mPlanetList = planet_List;
    }

    @Override
    public int getCount() {
        return mPlanetList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPlanetList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list, null);
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Planet planet = mPlanetList.get(position);
        holder.iv_icon.setImageResource(planet.image);
        holder.tv_name.setText(planet.name);
        holder.tv_desc.setText(planet.desc);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String desc = String.format("您点击了第%s个行星，它的名字是%s", position + 1, mPlanetList.get(position).name);
        Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String desc = String.format("您点击了第%s个行星，它的名字是%s", position + 1, mPlanetList.get(position).name);
        Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
        return true;
    }

    final class ViewHolder {
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_desc;
    }
}
