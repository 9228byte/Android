package com.example.group.constant;

import com.example.group.R;

import java.util.ArrayList;

/**
 * ImageList
 *
 * @author lao
 * @date 2019/5/4
 */
public class ImageList {

    public static ArrayList<Integer> getDefault() {
        ArrayList<Integer> imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.banner_1);
        imageList.add(R.drawable.banner_2);
        imageList.add(R.drawable.banner_3);
        imageList.add(R.drawable.banner_4);
        imageList.add(R.drawable.banner_5);
        return imageList;
    }
}
