package com.example.senior.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.senior.R;

/**
 * Created by lao on 2019/4/8
 */

public class LaunchFragment extends Fragment {
    protected View mView;
    protected Context mContext;
    private int mPosition;
    private int mImageId;
    private int mCount = 4;     //图片数量

    public static LaunchFragment newInstance(int position, int image_id) {
        LaunchFragment fragment = new LaunchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putInt("image_id", image_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        if (getArguments() != null) {
            mPosition = getArguments().getInt("position", 0);
            mImageId = getArguments().getInt("image_id", 0);
        }
        mView = inflater.inflate(R.layout.item_launch, container, false);
        ImageView iv_launch = mView.findViewById(R.id.iv_launch);
        RadioGroup rg_indicate = mView.findViewById(R.id.rg_indicate);
        Button btn_start = mView.findViewById(R.id.btn_start);
        //设置引导页图片
        iv_launch.setImageResource(mImageId);
        for (int j = 0; j < mCount; j++) {
            RadioButton radio = new RadioButton(mContext);
            radio.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            radio.setButtonDrawable(R.drawable.launch_guide);
            radio.setPadding(10, 10, 10, 10);

            rg_indicate.addView(radio);
        }
        ((RadioButton)rg_indicate.getChildAt(mPosition)).setChecked(true);

        if (mPosition == mCount -1) {
            btn_start.setVisibility(View.VISIBLE);
            btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "欢迎开启美好生活", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return mView;
    }
}
