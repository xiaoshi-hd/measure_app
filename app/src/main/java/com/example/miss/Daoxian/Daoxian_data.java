package com.example.miss.Daoxian;

import com.example.miss.Caculate;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/11.
 */

public class Daoxian_data {
//这里并没有用到数据库，使用的是静态变量和列表存储信息，起到数据库的作用，但是列表数据的清空比较头疼

    //记录转向信息和闭合差
    public static int k;
    //记录是否超限
    public static boolean warn_jiao;
    public static boolean warn_bian;
    public static double bhc;
    public static double xbhc;
    public static double ybhc;
    public static double xybhc;
    public static double sum_jiao,sum_X,sum_Y,sum_dis;
    //分别为起始边AB和结束边CD的方位角
    public static double fangwei_first;
    public static double fangwei_end;
    //分别为起始点和结束点的坐标
    public static Daoxian_data.ZuoBiao B;
    public static Daoxian_data.ZuoBiao C;

    //存储点号信息
    public static ArrayList<String> ceZhan = new ArrayList<>();
    //表示各站观测角,存储为弧度
    public static ArrayList<Double> guanCeJList = new ArrayList<>();
    //表示各站观测距离
    public static ArrayList<Double> disList = new ArrayList<>();

    //存储方位角
    public static ArrayList<Double> fangweijiao = new ArrayList<>();
    //存储坐标增量
    public static ArrayList<Double> Xzengliang = new ArrayList<>();
    public static ArrayList<Double> Yzengliang = new ArrayList<>();
    //平差结果,即各点坐标
    public static ArrayList<ZuoBiao> resultList = new ArrayList<>();

    //存储坐标
    public static class ZuoBiao{
        double x;
        double y;

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public ZuoBiao(double x, double y){
            this.x=x;
            this.y=y;
        }
    }

    //region 导线平差的代码部分
    public static void jisuan(){

        warn_jiao = false;
        warn_bian = false;
        fangweijiao.clear();
        Xzengliang.clear();
        Yzengliang.clear();
        resultList.clear();

        //region 方位角计算
        double n = 0;//存储加减360的信息
        fangweijiao.add(fangwei_first);
        for (int i = 0; i < guanCeJList.size(); i++)
        {
            double fangwei = fangweijiao.get(i) + k * guanCeJList.get(i) - k * Math.PI;
            if (fangwei > Math.PI * 2)
            {
                fangwei = fangwei - Math.PI * 2;
                n = n - Math.PI * 2;
            }
            else if (fangwei < 0)
            {
                fangwei = fangwei + Math.PI * 2;
                n = n + Math.PI * 2;
            }
            fangweijiao.add(fangwei);
        }
        //endregion
        //region 角度近似平差
        sum_jiao = 0;
        for (int i = 0; i < guanCeJList.size(); i++) {
             sum_jiao = guanCeJList.get(i) + sum_jiao;
        }
        bhc = fangwei_first + k * sum_jiao - k * guanCeJList.size() * Math.PI - fangwei_end + n;
        if (Caculate.hudutos(bhc) > 24 * Math.sqrt(guanCeJList.size()))
        {
            //Log.d("Activity生命周期", "角度闭合差超限！！！");
            warn_jiao = true;
        }
        else
        {
            fangweijiao.clear();//清空未经平差的方位角
            fangweijiao.add(fangwei_first);
            //Log.d("Activity生命周期1", String.valueOf(Caculate.hudutodms(fangwei_first)));
            for (int i = 0; i < guanCeJList.size(); i++)
            {
                double fangwei;
                fangwei = fangweijiao.get(i) + k * (guanCeJList.get(i) - k * bhc / guanCeJList.size()) - k * Math.PI;
                if (fangwei > Math.PI * 2)
                {
                    fangwei = fangwei - Math.PI * 2;
                }
                else if (fangwei < 0)
                {
                    fangwei = fangwei + Math.PI * 2;
                }
                fangweijiao.add(fangwei);
                //Log.d("Activity生命周期1", String.valueOf(Caculate.hudutodms(fangwei)));
            }
        }
        //endregion
        //region 计算坐标增量

        for (int i = 0; i < disList.size(); i++)
        {
            Xzengliang.add(disList.get(i) * Math.cos(fangweijiao.get(i + 1)));
            Yzengliang.add(disList.get(i) * Math.sin(fangweijiao.get(i + 1)));
            //Log.d("Activity生命周期2", String.valueOf(Xzengliang.get(i)));
            //Log.d("Activity生命周期2", String.valueOf(Yzengliang.get(i)));
        }
        sum_X = 0;
        for (int i = 0; i < Xzengliang.size(); i++) {
            sum_X = Xzengliang.get(i) + sum_X;
        }
        sum_Y = 0;
        for (int i = 0; i < Yzengliang.size(); i++) {
            sum_Y = Yzengliang.get(i) + sum_Y;
        }
        sum_dis = 0;
        for (int i = 0; i < disList.size(); i++) {
            sum_dis = disList.get(i) + sum_dis;
        }
        xbhc = B.getX() + sum_X - C.getX();
        ybhc = B.getY() + sum_Y - C.getY();;
        xybhc = Math.sqrt(xbhc * xbhc + ybhc * ybhc);

        if (xybhc / sum_dis > (1 / 5000d))//5000d必须加d，要不然算出来是0
        {
            //Log.d("Activity生命周期", "导线全长闭合差超限！！！");
            warn_bian = true;
        }
        //endregion
        //region 计算坐标
        resultList.add(B);

        for (int i = 0; i < disList.size(); i++)
        {
            resultList.add(new ZuoBiao(resultList.get(i).getX() + Xzengliang.get(i) - xbhc * disList.get(i) / sum_dis,
                    resultList.get(i).getY() + Yzengliang.get(i) - ybhc * disList.get(i) / sum_dis));
        }
        //resultList.add(C);
        //endregion
    }
    //endregion
}

