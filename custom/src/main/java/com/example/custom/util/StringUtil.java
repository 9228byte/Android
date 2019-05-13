package com.example.custom.util;

/**
 * StringUtil
 *
 * @author lao
 * @date 2019/4/24
 */
public class StringUtil {
    //保留小数点后多少位
    public static String formatWithString(Double value, int digit) {
        String format = String.format("%%.%df", digit);
        return String.format(format, value);
    }

    //格式化流量数据/文件大小。输出已字节为单位的长整数
    public static String formatData(long data) {
        String result = "";
        if (data > 1024 * 1024) {
            result  = String.format("%sM", formatWithString(data / 1024.0 / 1024.0, 1));
        } else if (data > 1024) {
            result = String.format("%sK", formatWithString(data / 1024.0, 1));
        } else {
            result  = String.format("%sB", "" + data);
        }
        return result;
    }

}
