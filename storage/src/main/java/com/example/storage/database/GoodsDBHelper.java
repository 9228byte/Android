package com.example.storage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.storage.bean.GoodsInfo;
import java.util.ArrayList;


/**
 * Created by lao on 2019/4/2
 */

public class GoodsDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "GoodsDBHelper";
    private static final String DB_NAME = "goods.db";
    private static final String TABLE_NAME = "goods_info";
    private static final int DB_VERSION = 1;
    private static GoodsDBHelper mHelper = null;
    private SQLiteDatabase mDB = null;

    private GoodsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private GoodsDBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    public  static GoodsDBHelper getInstance(Context context, int version) {
        if (version > 0 && mHelper == null) {
            mHelper = new GoodsDBHelper(context, version);
        } else if (mHelper == null) {
            mHelper = new GoodsDBHelper(context);
        }
        return mHelper;
    }

    public SQLiteDatabase openReadLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getReadableDatabase();
        }
        return mDB;
    }

    public SQLiteDatabase openWriteLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getWritableDatabase();
        }
        return mDB;
    }

    public void closeLink() {
        if (mDB != null && mDB.isOpen()) {
            mDB.close();
            mDB = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: ");
        String drop_sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        Log.d(TAG, "drop_sql:" + drop_sql);
        db.execSQL(drop_sql);
        String create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "name VARCHER NOT NULL," + "desc VARCHER NOT NULL,"
                + "price FLOAT NOT NULL," + "thumb_path VARCHER NOT NULL,"
                + "pic_path VARCHER NOT NULL"
                + ");";
        Log.d(TAG, "craate_sql:" + create_sql);
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //未实现
    }

    public int delete(String condition) {
        return mDB.delete(TABLE_NAME, condition, null);
    }

    public int deleteAll() {
        return mDB.delete(TABLE_NAME, "1=1", null);
    }

    public long insert(GoodsInfo info) {
        ArrayList<GoodsInfo> infoArray = new ArrayList<GoodsInfo>();
        infoArray.add(info);
        return insert(infoArray);
    }

    public long insert(ArrayList<GoodsInfo> infoArray) {
        Log.d(TAG, "insert: ");
        long result = -1;
        for (GoodsInfo info : infoArray) {
            //存在相同记录则更新
            if (info.rowid > 0) {
                String condition = String.format("rowid='%d'" + info.rowid);
                update(info, condition);
                result = info.rowid;
                continue;
            }
            ContentValues cv = new ContentValues();
            cv.put("name", info.name);
            cv.put("desc", info.desc);
            cv.put("price", info.price);
            cv.put("thumb_path", info.thumb_path);
            cv.put("pic_path", info.pic_path);
            //执行插入动作，返回行号
            result = mDB.insert(TABLE_NAME, "", cv);
            if (result == -1) { //添加成功返回行号，失败返回-1
                return result;
            }
        }
        return result;
    }

    public int update(GoodsInfo info, String condition) {
        ContentValues cv = new ContentValues();
        cv.put("name", info.name);
        cv.put("desc", info.desc);
        cv.put("price", info.price);
        cv.put("thumb_path", info.thumb_path);
        cv.put("pic_path", info.pic_path);
        //执行更新，返回更新记录条目
        return mDB.update(TABLE_NAME, cv, condition, null);
    }

    public int update(GoodsInfo info) {
        return update(info, "rowid=" + info.rowid);
    }

    //g根据条件查询记录，并返回结果数据队列
    public ArrayList<GoodsInfo> query(String condition) {
        String sql = String.format("select rowid,_id,name,desc,price,thumb_path,pic_path" +
                " from %s where %s;",TABLE_NAME, condition);
        Log.d(TAG, "query_sql:" + sql);
        ArrayList<GoodsInfo> infoArray = new ArrayList<GoodsInfo>();
        Cursor cursor = mDB.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            GoodsInfo info = new GoodsInfo();
            info.rowid = cursor.getLong(0);
            info.xuhao = cursor.getInt(1);
            info.name = cursor.getString(2);
            info.desc = cursor.getString(3);
            info.price = cursor.getFloat(4);
            info.thumb_path = cursor.getString(5);
            info.pic_path = cursor.getString(6);
            infoArray.add(info);
        }
        cursor.close();
        return infoArray;
    }

    public GoodsInfo queryById(long rowid) {
        GoodsInfo info = null;
        ArrayList<GoodsInfo> infoArray = query(String.format("rowid='%d'" ,rowid));
        if (infoArray.size() > 0) {
            info = infoArray.get(0);
        }
        return info;
    }
}
