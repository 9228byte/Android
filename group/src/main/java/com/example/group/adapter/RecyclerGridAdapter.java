package com.example.group.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group.R;
import com.example.group.bean.GoodsInfo;
import com.example.group.widget.RecyclerExtras.OnItemClickListener;
import com.example.group.widget.RecyclerExtras.OnItemLongClickListener;

import java.util.ArrayList;


/**
 * RecyclerGridAdapter
 *
 * @author lao
 * @date 2019/5/7
 */
public class RecyclerGridAdapter extends RecyclerView.Adapter<ViewHolder> implements
        OnItemClickListener, OnItemLongClickListener {
    private static final String TAG = "RecycleGridAdapter";
    private Context mContext;
    private ArrayList<GoodsInfo> mGoodsArray;

    public RecyclerGridAdapter(Context context, ArrayList<GoodsInfo> goodsArray) {
        mContext = context;
        mGoodsArray = goodsArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_grid, viewGroup, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int i) {
        ItemHolder holder = (ItemHolder) viewHolder;
        holder.iv_pic.setImageResource(mGoodsArray.get(i).pic_id);
        holder.tv_title.setText(mGoodsArray.get(i).title);
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, i);
                }
            }
        });

        holder.ll_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    mOnItemLongClickListener.onItemLongClick(v, i);
                }
                return true;
            }
        });
    }

    public int getItemViewType(int position) {
        return 0;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mGoodsArray.size();
    }

    @Override
    public void onItemClick(View view, int position) {
        String desc = String.format("您点击了第%d项,栏目名称是%s", position + 1, mGoodsArray.get(position).title);
        Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        String desc = String.format("您长按了第%d项,栏目名称是%s", position + 1, mGoodsArray.get(position).title);
        Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
    }

    public class ItemHolder extends ViewHolder {
        public LinearLayout ll_item;
        public ImageView iv_pic;
        public TextView tv_title;

        public ItemHolder(View v) {
            super(v);
            ll_item = v.findViewById(R.id.ll_item);
            iv_pic = v.findViewById(R.id.iv_pic);
            tv_title = v.findViewById(R.id.tv_title);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }
}
