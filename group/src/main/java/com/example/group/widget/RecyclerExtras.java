package com.example.group.widget;

import android.view.View;

/**
 * RecyclerExtras
 *
 * @author lao
 * @date 2019/5/7
 */
public class RecyclerExtras {

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public interface OnItemDeleteClickListener {
        void onItemDeleteClick(View view, int position);
    }
}
