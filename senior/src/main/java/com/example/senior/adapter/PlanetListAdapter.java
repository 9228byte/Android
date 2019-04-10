package com.example.senior.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senior.R;
import com.example.senior.bean.Planet;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by lao on 2019/4/6
 */

public class PlanetListAdapter extends BaseAdapter implements OnItemClickListener, OnItemLongClickListener{
    private Context mContext;
    private ArrayList<Planet> mPlanetList;

    //行星适配器的构造函数，传入上下文与行星队列
    public PlanetListAdapter(Context context, ArrayList<Planet> planet_list) {
        mContext = context;
        mPlanetList = planet_list;
    }

    //获取列表项的个数
    public int getCount() {
        return mPlanetList.size();
    }

    //获取列表项的数据
    public Object getItem(int arg0) {
        return mPlanetList.get(arg0);
    }

    //获取列表项的编号
    public long getItemId(int arg0) {
        return arg0;
    }

    //获取指定位置的列表视图
    public View getView(final int poastion, View convertViewm, ViewGroup parent) {
        ViewHolder holder;
        if (convertViewm == null) {     //转换视图为空
            holder = new ViewHolder();      //创建一个新的视图持有者
            //根据布局文件item_list,xml生成装换视图对象
            convertViewm = LayoutInflater.from(mContext).inflate(R.layout.item_list, null);
            holder.iv_cion = convertViewm.findViewById(R.id.iv_icon);
            holder.tv_name = convertViewm.findViewById(R.id.tv_name);
            holder.tv_desc = convertViewm.findViewById(R.id.tv_desc);
            //将视图持有者保存到装换视图当中
            convertViewm.setTag(holder);
        } else {    //转换视图非空
            holder = (ViewHolder) convertViewm.getTag();
        }
        Planet planet = mPlanetList.get(poastion);
        holder.iv_cion.setImageResource(planet.image);
        holder.tv_name.setText(planet.name);
        holder.tv_desc.setText(planet.desc);
        return convertViewm;
    }

    //定义一个视图持有者，以便重用列表项的视图资源
    public final class ViewHolder {
        public ImageView iv_cion;       //声明行星图片的图像视图对象
        public TextView tv_name;        //声明行星名称的文本视图对象
        public TextView tv_desc;        //声明行星描述的文本视图对象
    }

    //处理列表点击事件，由接口OnItemClickListener触发
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String desc = String.format("您点击了第%d个行星，它的名字是%s", position +1,
                mPlanetList.get(position).name);
        Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
    }

    //处理列表项长按事件，由接口OnItemLongClickListener触发
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String desc = String.format("您长按了第%d个行星，它的名字是%s", position+1,
                mPlanetList.get(position).name);
        Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
        return true;
    }

}
