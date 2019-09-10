package com.example.network.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.network.activity.MainApplication;
import com.example.network.http.HttpRequestUtil;
import com.example.network.http.tool.HttpReqData;
import com.example.network.http.tool.HttpRespData;
import com.example.network.util.BitmapUtil;
import com.example.network.util.DateUtil;

/**
 * GetImageCodeTask
 *
 * @author lao
 * @date 2019/5/18
 */
public class GetImageCodeTask extends AsyncTask<Void, Void, String> {
    private final static String TAG = "GetImageCodeTask";
//    private String mImageCodeUrl = "http://222.77.181.14/ValidateCode?time=";
    private String mImageCodeUrl = "http://yx12.fjjcjy.com/Public/Control/GetValidateCode?time=";

    public GetImageCodeTask() {
        super();
    }

    @Override
    protected String doInBackground(Void... voids) {
        //为验证码地址添加一个随机串（以当前时间模拟随机串）
        String url = mImageCodeUrl + DateUtil.getNowDateTime();
        Log.d(TAG, "image url=" + url);
        //创建一个HTTP请求对象
        HttpReqData req_date = new HttpReqData(url);
        //发送HTTP请求信息，并获得HTTP应答对象
        HttpRespData resp_data = HttpRequestUtil.getImage(req_date);
        String path = BitmapUtil.getCachePath(MainApplication.getInstance()) + DateUtil.getNowDateTime() + ".jpg";
        BitmapUtil.saveBitmap(path, resp_data.bitmap, "jpg", 80);
        Log.d(TAG, "image_path" + path);
        return path;
    }

    //线程已完成处理
    @Override
    protected void onPostExecute(String path) {
        mListener.onGetCode(path);
    }

    private OnImageCodeListener mListener;

    public void setOnImageListener(OnImageCodeListener mListener) {
        this.mListener = mListener;
    }

    //定义一个获取图片验证码的监听器
    public interface OnImageCodeListener {
        void onGetCode(String path);
    }
}
