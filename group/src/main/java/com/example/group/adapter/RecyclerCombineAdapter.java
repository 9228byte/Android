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
import com.example.group.util.Utils;
import com.example.group.widget.RecyclerExtras.OnItemClickListener;
import com.example.group.widget.RecyclerExtras.OnItemLongClickListener;

import java.util.ArrayList;

/**
 * RecyclerCombineAdapter
 *
 * @author lao
 * @date 2019/5/7
 */
public class RecyclerCombineAdapter extends RecyclerView.Adapter<ViewHolder> implements
        OnItemClickListener, OnItemLongClickListener {
    private static final String TAG = "RecyclerCombineAdapter";
    private Context mContext;
    private ArrayList<GoodsInfo> mGoodsArray;

    public RecyclerCombineAdapter(Context context, ArrayList<GoodsInfo> goodsArray) {
        mContext  = context;
        mGoodsArray = goodsArray;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_combine, viewGroup, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        ItemHolder holder = (ItemHolder) viewHolder;
        holder.iv_pic.setImageResource(mGoodsArray.get(i).pic_id);
        holder.tv_title.setText(mGoodsArray.get(i).title);
        ViewGroup.LayoutParams iv_params = holder.iv_pic.getLayoutParams();
        if (i == 0 || i == 1) {
            iv_params.height = Utils.dip2px(mContext, 130);
        } else {
            iv_params.height = Utils.dip2px(mContext, 60);
        }
        holder.iv_pic.setLayoutParams(iv_params);
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

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public int getItemViewType(int position) {
        return 0;
    }

    public long getItemId(int position) {
        return position;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mGoodsArray.size();
    }


    @Override
    public void onItemClick(View view, int position) {
        String desc = String.format("您点击了第%d项，推荐频道是%s", position + 1, mGoodsArray.get(position).title);
        Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        String desc = String.format("您长按了第%d项，推荐频道是%s", position + 1, mGoodsArray.get(position).title);
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
}
