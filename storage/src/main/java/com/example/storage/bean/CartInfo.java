package com.example.storage.bean;

/**
 * Created by lao on 2019/4/2
 */

public class CartInfo {
    public long rowid;
    public int xuhao;
    public long goods_id;
    public int count;
    public String update_time;

    public CartInfo() {
        rowid = 0;
        xuhao = 0;
        goods_id = 0;
        count = 0;
        update_time = "";
    }
}
