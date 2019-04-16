package com.example.miss.Daoxian;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.miss.Caculate;
import com.example.miss.R;

import java.util.List;

/**
 * Created by 赵朋小仙女 on 2019/4/11.
 */

public class stationAdapter extends RecyclerView.Adapter<stationAdapter.LinearViewHolder> {

    private List<Station> stationList;
    //传入外部的数据
    public stationAdapter (List<Station> stationList){
        this.stationList = stationList;
    }
    @Override
    public stationAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_station_item,parent,false));
    }

    @Override//设置显示内容
    public void onBindViewHolder(stationAdapter.LinearViewHolder holder, int position) {
        Station station = stationList.get(position);
        holder.ceZhan.setText("测站:"+station.getCeZhan());
        holder.dis.setText("水平距离："+station.getDis() + "m");
        holder.guanCeJ.setText("观测角："+ Caculate.hudutoDMS(station.getGuanCeJ()));
    }

    @Override//返回长度
    public int getItemCount() {//返回的是现实item的个数
        return stationList.size();
    }
    //找到控件
    class LinearViewHolder extends RecyclerView.ViewHolder {
        private TextView ceZhan;
        private TextView guanCeJ;
        private TextView dis;

        public LinearViewHolder (View itemView){
            super(itemView);
            ceZhan = itemView.findViewById(R.id.station_ceZhan);
            guanCeJ = itemView.findViewById(R.id.station_guanCeJ);
            dis = itemView.findViewById(R.id.station_dis);
        }
    }
}
