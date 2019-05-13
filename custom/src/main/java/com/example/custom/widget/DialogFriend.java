package com.example.custom.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.example.custom.R;
import com.example.custom.adapter.FriendAdapter;
import com.example.custom.adapter.FriendAdapter.OnDeleteListener;
import com.example.custom.util.Friend;

import java.util.List;

/**
 * DialogFriend
 *
 * @author lao
 * @date 2019/4/20
 */
public class DialogFriend implements OnClickListener, OnDeleteListener{
    private Context mContext;
    private Dialog dialog;
    private View view;
    private TextView tv_title;
    private ListView lv_friend;
    private List<Friend> mFriendList;
    private FriendAdapter friendAdapter;

    public DialogFriend(Context context, List<Friend> friendList, onAddFriendListener listener) {
        mContext = context;
        view  = LayoutInflater.from(mContext).inflate(R.layout.dialog_friend, null);
        dialog = new Dialog(context, R.style.dialog_layout_bottom);
        tv_title = view.findViewById(R.id.tv_title);
        lv_friend = view.findViewById(R.id.lv_friend);
        view.findViewById(R.id.tv_ok).setOnClickListener(this);
        mOnAddFriendListener = listener;
        mFriendList = friendList;
        //朋友信息适配器
        friendAdapter = new FriendAdapter(mContext, mFriendList, this);
        lv_friend.setAdapter(friendAdapter);
    }

    public void show() {
        dialog.getWindow().setContentView(view);
        dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        //允许取消对话框
        dialog.setCancelable(true);
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public boolean isShowing() {
        if (dialog != null) {
            return dialog.isShowing();
        } else {
            return false;
        }
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_ok) {
            if (mOnAddFriendListener != null) {
                //回调监听器的addFriend方法
                mOnAddFriendListener.addFriend(friendAdapter.getFriends());
            }
        }
    }

    private onAddFriendListener mOnAddFriendListener;

      public interface onAddFriendListener {
        void addFriend(List<Friend> friendList);
    }

    //点击了删除按钮，就触发这里的onDeleteClick方法
    public void onDeleteClick(int position) {
        mFriendList.remove(position);
        //通知是适配器发生了数据变更
        friendAdapter.notifyDataSetChanged();
    }
}
