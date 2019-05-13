package com.example.group.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group.R;

/**
 * BookCoverFragment
 *
 * @author lao
 * @date 2019/5/4
 */

public class BookCoverFragment extends Fragment {
    private View mView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_book_cover, container, false);
        return mView;
    }

}
