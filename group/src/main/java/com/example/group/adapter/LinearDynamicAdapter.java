package com.example.group.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.group.R;
import com.example.group.bean.GoodsInfo;
import com.example.group.widget.RecyclerExtras;
import com.example.group.widget.RecyclerExtras.OnItemClickListener;
import com.example.group.widget.RecyclerExtras.OnItemLongClickListener;

import java.util.ArrayList;

;

/**
 * RecyclerDynamicAdapter
 *
 * @author lao
 * @date 2019/5/8
 */
public class LinearDynamicAdapter extends Adapter<ViewHolder> implements
        OnClickListener, OnLongClickListener {
    private static final String TAG = "LinearDynamicAdapter";
    private Context mContext;
    private ArrayList<GoodsInfo> mPublicArray;

    public LinearDynamicAdapter(Context context, ArrayList<GoodsInfo> publicArray) {
        mContext = context;
        mPublicArray = publicArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_linear, viewGroup, false);
        return new ItemHolder(v);
    }

    //获取类表项当前的位置序号
    public int getPosition(int item_id) {
        int pos = 0;
        for (int i = 0; i < mPublicArray.size() ; i++) {
            if (mPublicArray.get(i).id == item_id) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GoodsInfo item = mPublicArray.get(i);
        ItemHolder holder = (ItemHolder) viewHolder;
        holder.iv_pic.setImageResource(item.pic_id);
        holder.tv_title.setText(item.title);
        holder.tv_desc.setText(item.desc);
        holder.tv_delete.setVisibility((item.bPressed) ? View.VISIBLE : View.GONE);
        holder.tv_delete.setId(item.id * 10 + DELETE);
        holder.tv_delete.setOnClickListener(this);
        holder.ll_item.setId(item.id * 10 + CLICK);
        //类表项的点击事件需要自己实现
        holder.ll_item.setOnClickListener(this);
        //长按事件
        holder.ll_item.setOnLongClickListener(this);
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
        return mPublicArray.size();
    }

    private int CLICK = 0;      //正常点击
    private int DELETE = 1;     //点击了删除按钮

    @Override
    public void onClick(View v) {
        int position  = getPosition((int) v.getId() / 10);
        int type = (int) v.getId() % 10;
        if (type == CLICK) {        //正常点击
            Log.d(TAG, "onClick: ");
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, position);
            }
         } else if (type == DELETE) {       //点击删除
            Log.d(TAG, "onClick: ");
            if (mOnItemDeleteClickListener != null) {
                mOnItemDeleteClickListener.onItemDeleteClick(v, position);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        int position = getPosition((int) v.getId() / 10);
        int type = (int) v.getId() % 10;
        if (type == CLICK) {
            Log.d(TAG, "onLongClick: ");
            if (mOnItemLongClickListener != null) {
                mOnItemLongClickListener.onItemLongClick(v, position);
            }
        }
        return true;
    }

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private RecyclerExtras.OnItemDeleteClickListener mOnItemDeleteClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public void setOnItemDeleteClickListener(RecyclerExtras.OnItemDeleteClickListener mOnItemDeleteClickListener) {
        this.mOnItemDeleteClickListener = mOnItemDeleteClickListener;
    }


    public class ItemHolder extends ViewHolder {
        private LinearLayout ll_item;
        private ImageView iv_pic;
        private TextView tv_title;
        private TextView tv_desc;
        private TextView tv_delete;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item);
            iv_pic = itemView.findViewById(R.id.iv_pic);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }
}
