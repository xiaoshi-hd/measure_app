package com.example.miss;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by 赵朋小仙女 on 2019/4/3.
 */

public class Caculate {
    public static double dmstohudu(double dms){//度.分秒数据化为弧度
        double d,m,s;
        d = Math.floor(dms);//向下取整，返回不大于该数的最大整数，返回double类型
        m = Math.floor(100*(dms - d));
        s = 100 * (100 * (dms - d) - m);
        return (d + m / 60 + s / 3600)*Math.PI /180;
    }
    public static double hudutodms(double hudu) {//弧度转化为度.分秒的形式输出
        double du, d, m, s, result;//因为是方位角的计算，要考虑小于0时的情况
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
    public static double hudutos(double hudu) {
        double d, m, s;
        double du = hudu * 180 / Math.PI;
        d = Math.floor(du);
        m = Math.floor((du - d) * 60);
        s = Round(((du - d) * 60 - m) * 60, 1);
        return d * 3600 + m * 60 + s;//保留到0.1秒
    }
    public static double Round(double num, int nn){//四舍五入函数
        BigDecimal big = new BigDecimal(num);//先把double类型转换为BigDecimal类型，在利用方法setScale方法设置4位小数，按照四舍五入的格式
        num =  big.setScale(nn,  RoundingMode.HALF_UP).doubleValue();//保留了6位小数，以保证精度
        return num;
    }
    public static double fangweijiaojisuan(double x1, double y1, double x2, double y2) {//根据坐标计算方位角
        double a = 180 - 90 * Math.abs(y2 - y1 + Math.pow(10, -10)) / (y2 - y1 + Math.pow(10, -10)) - Math.atan((x2 - x1) / (y2 - y1 + Math.pow(10, -10))) * 180 / Math.PI;
        //加上10^-10可以保证y2 = y1时不会报错
        return (a * Math.PI / 180);
    }
    public static double julijisuan(double x1, double y1, double x2, double y2){
        return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    }
}
