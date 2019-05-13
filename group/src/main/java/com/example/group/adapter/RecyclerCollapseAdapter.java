package com.example.group.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.group.R;

/**
 * RecyclerCollapseAdapter
 *
 * @author lao
 * @date 2019/5/10
 */
public class RecyclerCollapseAdapter extends Adapter<ViewHolder> {
    private static final String TAG = "RecyclerCollapseAdapter";
    private Context mContext;
    private String[] mTitleArray;

    public RecyclerCollapseAdapter(Context conetxt, String[] titlrArray) {
        mContext = conetxt;
        mTitleArray = titlrArray;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_collapse, viewGroup, false);
        return new TitleHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        TitleHolder holder = (TitleHolder) viewHolder;
        holder.tv_seq.setText("" + (i + 1));
        holder.tv_title.setText(mTitleArray[i]);
    }

    @Override
    public int getItemCount() {
        return mTitleArray.length;
    }

    public class TitleHolder extends ViewHolder {
        public LinearLayout ll_item;
        public TextView tv_seq;
        public TextView tv_title;

        public TitleHolder(@NonNull View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item);
            tv_seq = itemView.findViewById(R.id.tv_seq);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
}
