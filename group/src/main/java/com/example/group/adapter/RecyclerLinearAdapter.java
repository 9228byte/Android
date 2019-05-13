package com.example.group.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
 * RecyclerLinearAdapter
 *
 * @author lao
 * @date 2019/5/7
 */
public class RecyclerLinearAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        OnItemClickListener, OnItemLongClickListener {
    private static final String TAG = "RecycleLinearAdapter";
    private Context mContext;
    private ArrayList<GoodsInfo> mPublicArray;

    public RecyclerLinearAdapter(Context context, ArrayList<GoodsInfo> publicArray) {
        mContext = context;
        mPublicArray = publicArray;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_linear, viewGroup, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, final int position) {
        ItemHolder holder = (ItemHolder) vh;
        holder.iv_pic.setImageResource(mPublicArray.get(position).pic_id);
        holder.tv_title.setText(mPublicArray.get(position).title);
        holder.tv_desc.setText(mPublicArray.get(position).desc);
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            }
        });

        holder.ll_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnLongItemClickListener != null) {
                    mOnLongItemClickListener.onItemLongClick(v, position);
                }
                return true;
            }
        });
    }

    public int getItemViewType(int position) {
        //这里返回每项的类型，开发者可定义头部类型与一般类型
        //然后在onCreateViewHolder方法中根据类型加载不同的布局，从而实现带头部的网格布局
        return 0;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mPublicArray.size();
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickLintener(OnItemClickListener lintener) {
        this.mOnItemClickListener = lintener;
    }

    private OnItemLongClickListener mOnLongItemClickListener;

    public void setOnLongItemClickListener(OnItemLongClickListener listener) {
        this.mOnLongItemClickListener = listener;
    }

    @Override
    public void onItemClick(View view, int position) {
        String desc = String.format("您点击了第%d项,标题是%s", position + 1, mPublicArray.get(position).title);
        Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        String desc = String.format("您长按了第%d项,标题是%s", position + 1, mPublicArray.get(position).title);
        Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_item;
        private ImageView iv_pic;
        private TextView tv_title;
        private TextView tv_desc;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item);
            iv_pic = itemView.findViewById(R.id.iv_pic);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_desc = itemView.findViewById(R.id.tv_desc);
        }
    }
}
