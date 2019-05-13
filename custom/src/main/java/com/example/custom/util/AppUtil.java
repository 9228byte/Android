package com.example.custom.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.SparseIntArray;

import com.example.custom.bean.AppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * AppUtil
 *
 * @author lao
 * @date 2019/4/22
 */

public class AppUtil {

    // 获取已安装的应用信息队列
    public static ArrayList<AppInfo> getAppInfo(Context ctx, int type) {
        ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
        SparseIntArray siArray = new SparseIntArray();
        // 获得应用包管理器
        PackageManager pm = ctx.getPackageManager();
        // 获取系统中已经安装的应用列表
        List<ApplicationInfo> installList = pm.getInstalledApplications(
                PackageManager.PERMISSION_GRANTED);
        for (int i = 0; i < installList.size(); i++) {
            ApplicationInfo item = installList.get(i);
            // 去掉重复的应用信息
            if (siArray.indexOfKey(item.uid) >= 0) {
                continue;
            }
            //往siArray中添加一个应用编号,以便后续的去重复校验
            siArray.put(item.uid, 1);
            try {
                //获取应用的权限列表
                String [] permissions = pm.getPackageInfo(item.packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;
                if (permissions == null) {
                    continue;
                }
                boolean isQueryNetWord = false;
                for (String permission : permissions) {
                    //过滤那些具备上网权限的应用
                    if (permission.equals("android.permission.INTERNET")) {
                        isQueryNetWord = true;
                        break;
                    }
                }
                //类型0为表示所有应用，1表示联网应用
                if (type == 0 || (type == 1 && isQueryNetWord)) {
                    AppInfo app = new AppInfo();
                    app.uid = item.uid;
                    app.label = item.loadLabel(pm).toString();
                    app.package_name = item.packageName;
                    app.icon = item.loadIcon(pm);
                    appList.add(app);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

        }
        return appList;
    }


    //填充的完整信息，主要做两个事情其一补充应用的图标字段，其二是将列表按照流量排序
    public static ArrayList<AppInfo> fillAppInfo(Context ctx, final ArrayList<AppInfo> originArray) {
        ArrayList<AppInfo> fullArray = (ArrayList<AppInfo>) originArray.clone();
        PackageManager pm = ctx.getPackageManager();
        //从系统中获取已安装的应用列表
        List<ApplicationInfo> installList = pm.getInstalledApplications(PackageManager.PERMISSION_GRANTED);
        for (int i = 0; i < fullArray.size(); i++){
            AppInfo app = fullArray.get(i);
            for (ApplicationInfo item : installList) {
                if (app.uid == item.uid) {
                    //填充应用的图标信息，因为数据库没有保存图标的位置，所以取出来后还要补充图标数据
                    app.icon = item.loadIcon(pm);
                    break;
                }
            }
            fullArray.set(i, app);
        }
        //各应用按照流量大小排序
        Collections.sort(fullArray, new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo o1, AppInfo o2) {
                return (o1.traffic < o2.traffic) ? 1 : -1;
            }
        });
        return fullArray;
    }

}