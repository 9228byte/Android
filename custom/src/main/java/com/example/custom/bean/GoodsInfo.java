package com.example.custom.bean;

import com.example.custom.R;

import java.util.ArrayList;

/**
 * Created by lao on 2019/4/3
 */

public class GoodsInfo {
    public long rowid;  //行号
    public int xuhao;   //序号
    public String name; //名称
    public String desc;     //描述
    public float price;     //价格
    public String thumb_path;   //小图保存路径
    public String pic_path;     //大图保存路径
    public int thumb;       //小图的资源编号
    public int pic;     //大图的资源编号

    public GoodsInfo() {
        rowid = 0L;
        xuhao = 0;
        name  = "";
        desc = "";
        price = 0;
        thumb_path = "";
        pic_path = "";
        thumb = 0;
        pic = 0;
    }

    //声明一个手机商品的名称数组
    private static String[] mNameArray = {
            "Iphone8", "Mate10", "小米6", "OPPO R11", "vivo X9s", "魅族Pro 6S"
    };

    //声明一个手机商品的描述数据
    private static String[] mDescArray = {
            "Apple iPhone8 256G 玫瑰金色 全网通4G手机",
            "华为 HUAWEI Mate10 6GB+128GB 全网通（香槟金）",
            "小米 MI6 全网通版 6GB+128GB 亮白色",
            "OPPO R11 4GB+64GB 全网通4G智能手机 玫瑰金",
            "vivo X9s 4GB+64GB 全网通4G拍照手机 玫瑰金",
            "魅族 PRO6S 4GB+64GB 全网通公开版 星空黑 全网通4G手机"
    };

    //声一个手机商品价格数组
    private static float[] mPriceArray = { 6888, 3999, 2999, 2899, 2689, 2098};

    //声明一个手机商品的小图数组
    private static int[] mThumbArray = {
            R.drawable.iphone_s, R.drawable.huawei_s, R.drawable.xiaomi_s,
            R.drawable.oppo_s, R.drawable.vivo_s, R.drawable.meizu_s
    };

    //声明一个手机商品的大图数组
    private static int[] mPicArray = {
            R.drawable.iphone, R.drawable.huawei, R.drawable.xiaomi,
            R.drawable.oppo, R.drawable.vivo, R.drawable.meizu
    };

    //获取默认的手机信息列表
    public static ArrayList<GoodsInfo> getDefaultList() {
        ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
        for (int i = 0; i < mNameArray.length; i++) {
            GoodsInfo info = new GoodsInfo();
            info.name = mNameArray[i];
            info.desc = mDescArray[i];
            info.price = mPriceArray[i];
            info.thumb = mThumbArray[i];
            info.pic = mPicArray[i];
            goodsList.add(info);
        }
        return goodsList;
    }
}
