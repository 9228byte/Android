package com.example.storage;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.storage.util.DateUtil;

/**
 * @author lao
 * @create 2019/4/1
 */


public class MenuOptionActivity extends Activity implements OnClickListener {
    private TextView tv_option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_option);
        tv_option = findViewById(R.id.tv_option);
        findViewById(R.id.btn_option).setOnClickListener(this);
        setRandomTime();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_option) {
            //注意：如果当前页面继承自APPCompatActivity,并且appcompat版本不低于22.1.0
            //那么调用openOptionsMenu方法将不会弹出菜单。这应该是Android的一个bug
            openOptionsMenu();
        }
    }

    //在选项的菜单选中时调用
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //从menu_option.xml中构建布局
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    //在选项菜单的菜单项选中时调用

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();  //获取菜单项的编号
        if (id == R.id.menu_change_time) {
            setRandomTime();
        } else if (id == R.id.menu_change_color) {
            tv_option.setTextColor(getRandomColor());
        } else if (id == R.id.menu_change_bg) {
            tv_option.setBackgroundColor(getRandomColor());
        }
        return true;
    }

    private void setRandomTime() {
        String desc = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss") + " 这里是菜单显示文本";
        tv_option.setText(desc);
    }
    private int[] mColorArray = {
            Color.BLACK, Color.WHITE, Color.RED, Color.YELLOW, Color.GREEN,
            Color.BLUE, Color.CYAN, Color.MAGENTA, Color.GRAY, Color.DKGRAY
    };

    //随机颜色
    private int getRandomColor() {
        int random = (int) (Math.random() * 10 % 10);
        return mColorArray[random];
    }
}
