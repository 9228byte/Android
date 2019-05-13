package com.example.group;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.group.bean.LifeItem;

import java.util.ArrayList;

/**
 * LifeRecyclerAdapter
 *
 * @author lao
 * @date 2019/5/11
 */
public class LifeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<LifeItem> mItemArray;

    public LifeRecyclerAdapter(Context context, ArrayList<LifeItem> itemArray) {
        mContext = context;
        mItemArray  = itemArray;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_life, viewGroup, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ItemHolder holder = (ItemHolder) viewHolder;
        holder.iv_pic.setImageResource(mItemArray.get(i).pic);
        holder.tv_title.setText(mItemArray.get(i).title);
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
        return mItemArray.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView iv_pic;
        public TextView tv_title;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            iv_pic = itemView.findViewById(R.id.iv_pic);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
}
