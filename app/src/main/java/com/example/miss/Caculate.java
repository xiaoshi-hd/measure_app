package com.example.miss;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by 赵朋小仙女 on 2019/4/3.
 */

public class Caculate {
    //region 度.分秒数据化为弧度参与计算
    public static double dmstohudu(double dms){
        double d,m,s;
        int i = 1;
        if (dms < 0)
        {
            i = -1;
            dms = Math.abs(dms);
        }
        d = Math.floor(dms);//向下取整，返回不大于该数的最大整数，返回double类型
        m = Math.floor(100*(dms - d));
        s = 100 * (100 * (dms - d) - m);
        return i * (d + m / 60 + s / 3600)*Math.PI /180;
    }
    //endregion
    //region 弧度转化为度.分秒的形式输出
    public static double hudutodms_no(double hudu) {
        double du, d, m, s, result;
        int i = 1;
        if (hudu < 0)
        {
            i = -1;
            hudu = Math.abs(hudu);
        }
        du = hudu * 180 / Math.PI;//转化为度，再进行度.分秒的转化
        d = Math.floor(du);
        m = Math.floor(60 * (du - d));
        s = 60 * (60 * (du - d) - m);
        result = d + m / 100 + s / 10000;
        if ((60 - s) < 0.01){//秒保留两位小数，实现分秒的60进制
            m = m + 1;
            if (60 - m == 0){
                result = d + 1;
            }
        }
        return Round(i * result,6);//保留了6位小数，以保证精度
    }
    //endregion
    //region 度、分、秒数据化为弧度参与计算,导线计算时用到
    public static double DMStohudu(double du, double fen, double miao){
        return (du + fen / 60 + miao / 3600) * Math.PI /180;
    }
    //endregion
    //region 弧度转化为度、分、秒的形式输出，导线计算时用到
    public static String hudutoDMS(double hudu) {
        double du, d, m, s, result;
        if (hudu > 2 * Math.PI) {
            hudu -= 2 * Math.PI;
        }
        if(hudu < 0) {
            hudu += 2 * Math.PI;
        }
        du = hudu * 180 / Math.PI;//转化为度，再进行度.分秒的转化
        d = Math.floor(du);
        m = Math.floor(60 * (du - d));
        s = 60 * (60 * (du - d) - m);
        result = d + m / 100 + s / 10000;
        if ((60 - s) < 0.01){//实现分秒的60进制
            m = m + 1;
            s = 0;
            if (60 - m == 0){
                result = d + 1;
                m = 0;
            }
        }
        return (int)d + "°" + (int)m + "′" + Round(s,1) + "″";
    }
    //endregion
    //region 弧度化秒，导线计算时用到
    public static double hudutos(double hudu) {
        double d, m, s;
        double du = hudu * 180 / Math.PI;
        d = Math.floor(du);
        m = Math.floor((du - d) * 60);
        s = Round(((du - d) * 60 - m) * 60, 1);
        return d * 3600 + m * 60 + s;//保留到0.1秒
    }
    //endregion
    //region 弧度转化为度.分秒的形式，用于方位角显示，角度不能小于0
    public static double hudutodms(double hudu) {
        double du, d, m, s, result;
        if (hudu > 2 * Math.PI) {
            hudu -= 2 * Math.PI;
        }
        if(hudu < 0) {
            hudu += 2 * Math.PI;
        }
        du = hudu * 180 / Math.PI;//转化为度，再进行度.分秒的转化
        d = Math.floor(du);
        m = Math.floor(60 * (du - d));
        s = 60 * (60 * (du - d) - m);
        result = d + m / 100 + s / 10000;
        if ((60 - s) < 0.01){//实现分秒的60进制
            m = m + 1;
            if (60 - m == 0){
                result = d + 1;
            }
        }
        return Round(result,6);//保留了6位小数，以保证精度
    }
    //endregion
    //region 方位角计算,返回值为0-2*pi的弧度值
    public static double fangweijiaojisuan(double x1, double y1, double x2, double y2) {//根据坐标计算方位角
        double a = 180 - 90 * Math.abs(y2 - y1 + Math.pow(10, -10)) / (y2 - y1 + Math.pow(10, -10)) - Math.atan((x2 - x1) / (y2 - y1 + Math.pow(10, -10))) * 180 / Math.PI;
        //加上10^-10可以保证y2 = y1时不会报错
        return (a * Math.PI / 180);
    }

    //endregion
    //region 距离计算
    public static double julijisuan(double x1, double y1, double x2, double y2){
        return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    }
    //endregion
    //region 四舍去五入函数
    /*
    public static double Round(double num, int nn){
    //这个程序有问题，301.545保留两位小数结果为301.55,301.555结果仍为301.55，不符合测量数据进位要求
    //主要原因是浮点数的精度问题,建议在需要高精度的时候,使用BigDecimal类进行数据处理
    //BigDecimal.ROUND_DOWN:直接省略多余的小数，比如1.28如果保留1位小数，得到的就是1.2
    //BigDecimal.ROUND_UP:直接进位，比如1.21如果保留1位小数，得到的就是1.3
    //BigDecimal.ROUND_HALF_UP:四舍五入，2.35保留1位，变成2.4
    //BigDecimal.ROUND_HALF_DOWN:四舍五入，2.35保留1位，变成2.3

        BigDecimal big = new BigDecimal(num);//先把double类型转换为BigDecimal类型，在利用方法setScale方法设置4位小数，按照四舍五入的格式
        num =  big.setScale(nn,  RoundingMode.HALF_UP).doubleValue();
        return num;
    }
    */ /*
    public static double Round(double d, int i)//标准的四舍五入函数
    {
        if (d >= 0)
        {
            d += 5 * Math.pow(10, -(i + 1));
        }
        else
        {
            d -= 5 * Math.pow(10, -(i + 1));
        }
        String str = String.valueOf(d);
        int Index = str.indexOf(".");//查找字符第一次出现的位置，返回索引值，索引从0开始，如301.550返回3
        if (str.length() - (Index + 1) > i)
        {
            str = str.substring(0, Index + 1 + i);//substring() 方法用于提取字符串中介于两个指定下标之间的字符。
        }
        return Double.valueOf(str);
    }
    */
    //从统计学的角度，“奇进偶舍”比“四舍五入”要科学，在大量运算时，它使舍入后的结果误差的均值趋于零，而不是像四舍五入那样逢五就入，
    // 导致结果偏向大数，使得误差产生积累进而产生系统误差，“奇进偶舍”使测量结果受到舍入误差的影响降到最低。
    public static double Round(double d, int i)//标准的四舍六入五留双函数
    {
        double ratio = Math.pow(10, i);
        double num = d * ratio;
        Log.d("Activity生命周期", String.valueOf(num));
        double mod = num % 1;
        //为了解决mod是double类型精度损失的情况，也就是第一种方法中的问题，用以下两行代码解决
        BigDecimal bg = new BigDecimal(mod);
        mod = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        double integer = Math.floor(num);
        double returnNum;
        Log.d("Activity生命周期", String.valueOf(mod));
        Log.d("Activity生命周期", String.valueOf(integer));
        if(mod > 0.5){
            returnNum=(integer + 1) / ratio;
        }
        else if(mod < 0.5){
            returnNum=integer / ratio;
        }
        else{
            returnNum=(integer % 2 == 0 ? integer : integer + 1) / ratio;
        }
        Log.d("Activity生命周期", String.valueOf(returnNum));
        return returnNum;
    }
    //endregion
    //region 生成指定长度字符串，不足位右补空格,不过实际情况跟format是一样的，没有用
    public static String formatStr(String str, int length) {
        int strLen;
        if (str == null) {
            strLen = 0;
        }else{
            strLen= str.length();
        }

        if (strLen == length) {
            return str;
        }
        else if (strLen < length) {
            int temp = length - strLen;
            String tem = "";
            for (int i = 0; i < temp; i++) {
                tem = tem + " ";
            }
            return str + tem;
        }
        else{
            return str;
            //return str.substring(0,length);//长度超了截取固定长度
        }
    }
    //endregion
}
