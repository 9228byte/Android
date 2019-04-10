package com.example.senior.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.senior.MainApplication;
import com.example.senior.R;
import com.example.senior.ShoppingDetailActivity;
import com.example.senior.bean.GoodsInfo;

import java.util.ArrayList;

/**
 * Created by lao on 2019/4/7
 */

public class GoodsAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private Context mContext;
    private ArrayList<GoodsInfo> mGoodsArray;

    public GoodsAdapter(Context context, ArrayList<GoodsInfo> goods_list, addCartListener listener) {
        mContext = context;
        mGoodsArray = goods_list;
        mAddCartListener = listener;
    }

    @Override
    public int getCount() {
        return mGoodsArray.size();
    }

    @Override
    public Object getItem(int position) {
        return mGoodsArray.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_goods, null);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.iv_thumb = convertView.findViewById(R.id.iv_thumb);
            holder.tv_price = convertView.findViewById(R.id.tv_price);
            holder.btn_add = convertView.findViewById(R.id.btn_add);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final GoodsInfo info = mGoodsArray.get(position);
        holder.tv_name.setText(info.name);
        holder.iv_thumb.setImageBitmap(MainApplication.getInstance().mIconMap.get(info.rowid));
        holder.tv_price.setText("" + (int) info.price);
        holder.btn_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //触发就加入购物车监听器的添加动作
                mAddCartListener.addToCart(info.rowid);
                Toast.makeText(mContext, "已添加一部" + info.name + "到购物车", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_name;
        public ImageView iv_thumb;
        public TextView tv_price;
        public Button btn_add;
    }

    //声明一个而加入购物车的监听器对象
    private  addCartListener mAddCartListener;

    //定义一个加入购物车的监听器接口
    public interface addCartListener {
        void addToCart(long goods_id);      //在商品加入购物车时触发
    }

    //处理列表项的点击事件，由接口OnItemClickListener触发
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GoodsInfo info = mGoodsArray.get(position);
        //携带商品编号跳转到商品详情页
        Intent intent = new Intent(mContext, ShoppingDetailActivity.class);
        intent.putExtra("goods_id", info.rowid);
        mContext.startActivity(intent);
    }
}
