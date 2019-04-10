package com.example.senior.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.senior.R;
import com.example.senior.adapter.CalendarGridAdapter;

/**
 * Created by lao on 2019/4/10
 */

public class CalendarFragment extends Fragment {
    private static final String TAG = "CalendarFragment";
    protected View mView;
    protected Context mContext;
    private int mYear, mMonth;
    private GridView gv_calendar;

    public static CalendarFragment newInstance(int year, int month) {
        Bundle bundle= new Bundle();
        CalendarFragment fragment = new CalendarFragment();
        bundle.putInt("year" ,year);
        bundle.putInt("month", month);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        if (getArguments() != null) {
            mMonth = getArguments().getInt("month", 1);
            mYear = getArguments().getInt("year" , 2000);
        }
        mView = inflater.inflate(R.layout.fragment_calendar, container, false);
        gv_calendar = mView.findViewById(R.id.gv_calendar);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        CalendarGridAdapter adapter = new CalendarGridAdapter(mContext, mYear, mMonth ,1);
        gv_calendar.setAdapter(adapter);
    }
}
