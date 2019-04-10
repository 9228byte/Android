package com.example.senior.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.senior.MainApplication;
import com.example.senior.R;
import com.example.senior.bean.CartInfo;

import java.util.ArrayList;

/**
 * Created by lao on 2019/4/7
 */

public class CartAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<CartInfo> mCartArray;

    public CartAdapter(Context context, ArrayList<CartInfo> cart_list) {
        mContext = context;
        mCartArray = cart_list;
    }

    @Override
    public int getCount() {
        return mCartArray.size();
    }

    @Override
    public Object getItem(int position) {
        return mCartArray.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_cart, null);
            holder.iv_thumb = convertView.findViewById(R.id.iv_thumb);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            holder.tv_count = convertView.findViewById(R.id.tv_count);
            holder.tv_price = convertView.findViewById(R.id.tv_price);
            holder.tv_sum = convertView.findViewById(R.id.tv_sum);
            //将试图持有者保存到转换视图当中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CartInfo info = mCartArray.get(position);
        holder.iv_thumb.setImageBitmap(MainApplication.getInstance().mIconMap.get(info.goods_id));
        holder.tv_name.setText(info.goods.name);
        holder.tv_desc.setText(info.goods.desc);
        holder.tv_count.setText("" + info.count);
        holder.tv_price.setText("" + (int) info.goods.price);
        holder.tv_sum.setText("" + (int) (info.count * info.goods.price));
        return convertView;
    }

    public final class ViewHolder {
        public ImageView iv_thumb;
        public TextView tv_name;
        public TextView tv_desc;
        public TextView tv_count;
        public TextView tv_price;
        public TextView tv_sum;
    }
}
