package com.example.group.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
 * RecyclerStaggeredAdapter
 *
 * @author lao
 * @date 2019/5/8
 */
public class RecyclerStaggeredAdapter extends RecyclerView.Adapter<ViewHolder> implements
        OnItemClickListener, OnItemLongClickListener {
    private static final String TAG = "RecyclerStaggeredAdapter";
    private Context mContext;
    private ArrayList<GoodsInfo> mGoodsArray;

    public RecyclerStaggeredAdapter(Context context, ArrayList<GoodsInfo> goodsArray) {
        mContext = context;
        mGoodsArray = goodsArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_staggered, viewGroup, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        ItemHolder holder = (ItemHolder) viewHolder;
        holder.iv_pic.setImageResource(mGoodsArray.get(i).pic_id);
        holder.tv_title.setText(mGoodsArray.get(i).title);
        //设置随机高度
//        LayoutParams params = holder.ll_item.getLayoutParams();
//        params.height = (int) Math.round(300 * Math.random());
//        if (params.height < 60) {
//            params.height = 60;
//        }
        //setLayoutParams对瀑布网格不起作用，只能用setHeight
//        holder.ll_item.setLayoutParams(params);
//        holder.tv_title.setHeight(params.height);
        //点击监听器
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, i);
                }
            }
        });
        //长按监听器
        holder.ll_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null){
                    mOnItemLongClickListener.onItemLongClick(v, i);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mGoodsArray.size();
    }

    @Override
    public void onItemClick(View view, int position) {
        String desc = String.format("您点击了第%d项，商品名称是%s", position + 1, mGoodsArray.get(position).title);
        Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        String desc = String.format("您长按了第%d项，商品名称是%s", position + 1, mGoodsArray.get(position).title);
        Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
    }

    public class ItemHolder extends ViewHolder {
        public LinearLayout ll_item;
        public ImageView iv_pic;
        public TextView tv_title;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item);
            iv_pic = itemView.findViewById(R.id.iv_pic);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }

    private OnItemLongClickListener mOnItemLongClickListener;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
