package com.example.miss.Shuizhun;
import org.litepal.crud.DataSupport;
/**
 * Created by 赵朋小仙女 on 2019/4/14.
 */

public class Shuizhun_data extends DataSupport{
//这里用到数据库存储每一测站的信息,不过始终也没用上

    private int id;//记录是每一站的id
    private String cezhan;
    private double k1,k2;
    private double hhs,hhz,hhx;//后黑上，中，下
    private double qhs,qhz,qhx;//前黑上，中，下
    private double hhongz,qhongz;//后红中，前红中

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCezhan() {
        return cezhan;
    }

    public void setCezhan(String cezhan) {
        this.cezhan = cezhan;
    }

    public double getK1() {
        return k1;
    }

    public void setK1(double k1) {
        this.k1 = k1;
    }

    public double getK2() {
        return k2;
    }

    public void setK2(double k2) {
        this.k2 = k2;
    }

    public double getHhs() {
        return hhs;
    }

    public void setHhs(double hhs) {
        this.hhs = hhs;
    }

    public double getHhz() {
        return hhz;
    }

    public void setHhz(double hhz) {
        this.hhz = hhz;
    }

    public double getHhx() {
        return hhx;
    }

    public void setHhx(double hhx) {
        this.hhx = hhx;
    }

    public double getQhs() {
        return qhs;
    }

    public void setQhs(double qhs) {
        this.qhs = qhs;
    }

    public double getQhz() {
        return qhz;
    }

    public void setQhz(double qhz) {
        this.qhz = qhz;
    }

    public double getQhx() {
        return qhx;
    }

    public void setQhx(double qhx) {
        this.qhx = qhx;
    }

    public double getHhongz() {
        return hhongz;
    }

    public void setHhongz(double hhongz) {
        this.hhongz = hhongz;
    }

    public double getQhongz() {
        return qhongz;
    }

    public void setQhongz(double qhongz) {
        this.qhongz = qhongz;
    }

}
