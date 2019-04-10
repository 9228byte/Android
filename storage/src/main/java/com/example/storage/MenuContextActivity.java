package com.example.storage;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.storage.util.DateUtil;

public class MenuContextActivity extends AppCompatActivity implements OnClickListener{
    private static String TAG = "MenuContextActivity";
    private TextView tv_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_context);
        tv_context = findViewById(R.id.tv_context);
        findViewById(R.id.btn_context).setOnClickListener(this);
        setRandomTime();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_context) {
            Log.d(TAG, "onClick: ");
            registerForContextMenu(v);  //给按钮btn_context注册上下文菜单
            openContextMenu(v);     //显示打开上下文菜单
            unregisterForContextMenu(v);    //给按钮btn_context注销上下文菜单
        }
    }

    //页面恢复十调用
    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        //给文本视图tv_context注册上下文菜单
        //注册之后，只要按长按该控件,App就会自动打开上下文菜单
        registerForContextMenu(tv_context);
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        //给文本视图tv_context注销上下文菜单
        unregisterForContextMenu(tv_context);
        super.onResume();
    }

    //在上下文菜单的菜单页面创建时调用
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Log.d(TAG, "onCreateContextMenu: ");
        //从menu_option.xml中构建菜单页面布局
        getMenuInflater().inflate(R.menu.menu_option, menu);
    }

    //在上下文菜单的 菜单选项选中时调用
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(TAG, "onContextItemSelected: ");
        int id = item.getItemId();  //获取菜单项的编号
        if (id == R .id.menu_change_time) {
            setRandomTime();
        } else if (id == R.id.menu_change_color) {
            tv_context.setTextColor(getRandomColor());
        } else if (id == R.id.menu_change_bg) {
            tv_context.setBackgroundColor(getRandomColor());
        }
        return true;
    }

    private void setRandomTime() {
        Log.d(TAG, "setRandomTime: ");
        String desc = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss" + " 这里是显菜单示文本");
        tv_context.setText(desc);
    }

    private int[]mColorArray = {
            Color.BLACK, Color.WHITE, Color.RED, Color.YELLOW, Color.GREEN,
            Color.BLUE, Color.CYAN, Color.MAGENTA, Color.GRAY, Color.DKGRAY
    };

    private int getRandomColor() {
        Log.d(TAG, "getRandomColor: ");
        int random = (int) (Math.random() * 10 % 10);
        return mColorArray[random];
    }
}
