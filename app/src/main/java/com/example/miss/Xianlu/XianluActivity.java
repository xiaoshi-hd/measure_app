package com.example.miss.Xianlu;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.miss.Caculate;
import com.example.miss.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class XianluActivity extends AppCompatActivity {
    Button mbutton; //变量声明，在控件变量前加一个m
    RadioGroup mradioGroup;
    RadioButton mradioButton,rd_zuo,rd_you;
    EditText mqhz_zhuanghao,mqhz_X,mqhz_Y;
    EditText mjd_zhuanghao,mjd_X,mjd_Y;
    EditText mzhuangju,mbanjing,mzhuanjiao,mLs1,mLs2;
    EditText mzuo,myou;
    private Toolbar toolbar;

    double qhz_zhuanghao,qhz_X,qhz_Y;
    double jd_zhuanghao,jd_X,jd_Y;
    double zhuangju,banjing,zhuanjiao,Ls1,Ls2;
    double zuo,you;

    int k = 1;//记录转向信息
    int n = 3;//保留小数位数
    double fangwei;//起点方位角
    double q1, q2, p1, p2, T1, T2, LY, LH, E, DH;//计算曲线要素
    double KZH, KHY, KQZ, KYH, KHZ;//主点里程桩号
    double XZH, YZH, XHY, YHY, XQZ, YQZ, XYH, YYH, XHZ, YHZ,HYT,QZT,YHT,HZT;//主点坐标,切线方位角

    public static List<Double> zhuanghao = new ArrayList<Double>();
    public static List<Double> fangweijiao = new ArrayList<Double>();
    public static List<Double> Xzuobiao = new ArrayList<Double>();
    public static List<Double> Yzuobiao = new ArrayList<Double>();
    public static List<Double> leftX = new ArrayList<Double>();
    public static List<Double> leftY = new ArrayList<Double>();
    public static List<Double> rightX = new ArrayList<Double>();
    public static List<Double> rightY = new ArrayList<Double>();

    public static String str;//设置静态变量传出计算结果
    public static String str1;

    private void findcontrol(){//找到控件
        mqhz_zhuanghao = (EditText) findViewById(R.id.xianlu_ed_qhz_zhuanghao);
        mqhz_X = (EditText) findViewById(R.id.xianlu_ed_qhz_X);
        mqhz_Y = (EditText) findViewById(R.id.xianlu_ed_qhz_Y);
        mjd_zhuanghao = (EditText) findViewById(R.id.xianlu_ed_jd_zhuanghao);
        mjd_X = (EditText) findViewById(R.id.xianlu_ed_jd_X);
        mjd_Y = (EditText) findViewById(R.id.xianlu_ed_jd_Y);
        mzhuangju = (EditText) findViewById(R.id.xianlu_ed_jd_zhuangju);
        mbanjing = (EditText) findViewById(R.id.xianlu_ed_jd_banjing);
        mzhuanjiao = (EditText) findViewById(R.id.xianlu_ed_jd_zhuanjiao);
        mLs1 = (EditText) findViewById(R.id.xianlu_ed_jd_Ls1);
        mLs2 = (EditText) findViewById(R.id.xianlu_ed_jd_Ls2);
        mzuo = (EditText) findViewById(R.id.xianlu_ed_bz_zuo);
        myou = (EditText) findViewById(R.id.xianlu_ed_bz_you);
    }
    private void chushihua(){//初始化数据，方便调试
        zhuanghao.clear();//每次运行需要清空列表数据
        fangweijiao.clear();
        Xzuobiao.clear();
        Yzuobiao.clear();
        leftX.clear();
        leftY.clear();
        rightX.clear();
        rightY.clear();

    }
    private double dmstohudu(double dms){//度.分秒数据化为弧度
        double d,m,s;
        d = Math.floor(dms);//向下取整，返回不大于该数的最大整数，返回double类型
        m = Math.floor(100*(dms - d));
        s = 100 * (100 * (dms - d) - m);
        return (d + m / 60 + s / 3600)*Math.PI /180;
    }
    private double Round(double num, int nn){//四舍五入函数
        BigDecimal big = new BigDecimal(num);//先把double类型转换为BigDecimal类型，在利用方法setScale方法设置4位小数，按照四舍五入的格式
        num =  big.setScale(nn,  RoundingMode.HALF_UP).doubleValue();//保留了6位小数，以保证精度
        return num;
    }
    private double hudutodms(double hudu) {//弧度转化为度.分秒的形式输出
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
    private double fangweijiaojisuan(double x1, double y1, double x2, double y2) {//根据坐标计算方位角
        double a = 180 - 90 * Math.abs(y2 - y1 + Math.pow(10, -10)) / (y2 - y1 + Math.pow(10, -10)) - Math.atan((x2 - x1) / (y2 - y1 + Math.pow(10, -10))) * 180 / Math.PI;
        //加上10^-10可以保证y2 = y1时不会报错
        return (a * Math.PI / 180);
    }
    //region 说明菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.xianlu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.xianlu_item){
            Intent intent = new Intent(XianluActivity.this,Xianlu_explainActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.xianlu_item2){
            Intent intent = new Intent(XianluActivity.this,Xianlu_exampleActivity.class);
            startActivity(intent);
        }
        return true;
    }
    //endregion
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Activity生命周期","OnResume方法调用");

        if (Xianlu_exampleActivity.example) {
            //region 测试数据
            /*mqhz_zhuanghao.setText(String.valueOf(2845.918));
            mqhz_X.setText(String.valueOf(4040357.574));
            mqhz_Y.setText(String.valueOf(495491.689));
            mjd_zhuanghao.setText(String.valueOf(3628.633));
            mjd_X.setText(String.valueOf(4041133.369));
            mjd_Y.setText(String.valueOf(495595.535));
            mzhuangju.setText(String.valueOf(10));
            mbanjing.setText(String.valueOf(1100));
            mzhuanjiao.setText(String.valueOf(32.38497));
            mLs1.setText(String.valueOf(120));
            mLs2.setText(String.valueOf(120));
            mzuo.setText(String.valueOf(12.25));
            myou.setText(String.valueOf(12.25));*/
            //endregion
            //region 测试数据1
            mqhz_zhuanghao.setText(String.valueOf(72057.074));
            mqhz_X.setText(String.valueOf(24996.5767));
            mqhz_Y.setText(String.valueOf(69553.7061));
            mjd_zhuanghao.setText(String.valueOf(73078.9877));
            mjd_X.setText(String.valueOf(25233.3799));
            mjd_Y.setText(String.valueOf(68559.6076));
            mzhuangju.setText(String.valueOf(10));
            mbanjing.setText(String.valueOf(3504.4));
            mzhuanjiao.setText(String.valueOf(30.0511));
            mLs1.setText(String.valueOf(160));
            mLs2.setText(String.valueOf(160));
            mzuo.setText(String.valueOf(12.25));
            myou.setText(String.valueOf(12.25));
            //endregion
            //region 测试数据2
            /*mqhz_zhuanghao.setText(String.valueOf(155224.535));
            mqhz_X.setText(String.valueOf(33670.847));
            mqhz_Y.setText(String.valueOf(51097.981));
            mjd_zhuanghao.setText(String.valueOf(155910.908));
            mjd_X.setText(String.valueOf(34105.000));
            mjd_Y.setText(String.valueOf(51629.600));
            mzhuangju.setText(String.valueOf(10));
            mbanjing.setText(String.valueOf(2000));
            mzhuanjiao.setText(String.valueOf(30));
            mLs1.setText(String.valueOf(300));
            mLs2.setText(String.valueOf(300));
            mzuo.setText(String.valueOf(12.25));
            myou.setText(String.valueOf(12.25));*/
            //endregion
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Activity生命周期","onStop方法调用");
        Xianlu_exampleActivity.example = false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xianlu);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不让自动弹出软键盘
        findcontrol();
        toolbar = (Toolbar)findViewById(R.id.toolbar_xianlu);
        toolbar.setTitle("测量程序");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Log.i("Activity生命周期","OnCreate方法调用");
        //region 曲线转向显示
        mradioGroup = (RadioGroup) findViewById(R.id.xianlu_rg1);
        mradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                mradioButton = (RadioButton) group.findViewById(checkedId);//checkedId表示被选择的那个CheckedButton
                Toast.makeText(XianluActivity.this,"你选择了" + mradioButton.getText(),Toast.LENGTH_SHORT).show();
            }
        });
        //endregion

        mbutton = (Button) findViewById(R.id.xianlu_button);//为button设置监听事件
        mbutton.setOnClickListener(new View.OnClickListener() {//设置button的监听事件
            @Override
            public void onClick(View v) {
                chushihua();

                //region 转向选择
                rd_you = (RadioButton) findViewById(R.id.xianlu_rd1);
                rd_zuo = (RadioButton) findViewById(R.id.xianlu_rd2);
                if (rd_zuo.isChecked()){
                    k = -1;
                }
                if (rd_you.isChecked()){
                    k = 1;
                }
                Log.d("Activity生命周期",String.valueOf(k));
                //endregion

                //region 输入数据检查，不能为空字符
                try{//运行try_catch时程序出现异常不会使程序崩溃
                    qhz_zhuanghao = Double.parseDouble(mqhz_zhuanghao.getText().toString());//传入数据,转换成double类型
                    qhz_X = Double.parseDouble(mqhz_X.getText().toString());
                    qhz_Y= Double.parseDouble(mqhz_Y.getText().toString());
                    jd_zhuanghao = Double.parseDouble(mjd_zhuanghao.getText().toString());
                    jd_X = Double.parseDouble(mjd_X.getText().toString());
                    jd_Y = Double.parseDouble(mjd_Y.getText().toString());
                    zhuangju = Double.parseDouble(mzhuangju.getText().toString());
                    banjing = Double.parseDouble(mbanjing.getText().toString());
                    zhuanjiao = Caculate.dmstohudu(Double.parseDouble(mzhuanjiao.getText().toString()));
                    Ls1 = Double.parseDouble(mLs1.getText().toString());
                    Ls2 = Double.parseDouble(mLs2.getText().toString());
                    zuo = Double.parseDouble(mzuo.getText().toString());
                    you = Double.parseDouble(myou.getText().toString());
                }
                catch(Exception e){
                    Toast.makeText(XianluActivity.this, "请填写全部数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                //endregion
                //Log.d("Activity生命周期",String.valueOf(hudutodms(fangweijiaojisuan(0,0,90,90))));



                //region 曲线要素计算
                p1 = Round(Ls1 * Ls1 / 24 / banjing, n);//曲线要素
                p2 = Round(Ls2 * Ls2 / 24 / banjing, n);
                q1 = Round(Ls1 / 2 - Math.pow(Ls1, 3) / 240 / Math.pow(banjing, 2), n);
                q2 = Round(Ls2 / 2 - Math.pow(Ls2, 3) / 240 / Math.pow(banjing, 2), n);
                T1 = Round((banjing + p1) * Math.tan(zhuanjiao / 2) + q1 - (p1 - p2) / Math.sin(zhuanjiao), n);
                T2 = Round((banjing + p2) * Math.tan(zhuanjiao / 2) + q2 - (p1 - p2) / Math.sin(zhuanjiao), n);
                LY = Round(banjing * zhuanjiao - (Ls1 + Ls2) / 2, n);
                LH = Round(LY + Ls1 + Ls2, n);
                E = Round((banjing + (p1 + p2) / 2) / Math.cos(zhuanjiao / 2) - banjing, n);
                DH = Round(T1 + T2 - LH, n);
                //endregion
                //Log.d("Activity生命周期",String.valueOf(LH));
                //Log.d("Activity生命周期",String.valueOf(E));
                //Log.d("Activity生命周期",String.valueOf(DH));

                //region 主点里程桩号
                KZH = Round(jd_zhuanghao - T1, n);//主点里程桩号
                KHY = Round(KZH + Ls1, n);
                KQZ = Round(KZH + LH / 2, n);
                KYH = Round(KZH + LY + Ls1, n);
                KHZ = Round(KZH + LH, n);
                //endregion

                //region 计算桩号
                double zhuanghao1;//作为中间变量存储桩号
                zhuanghao1 = qhz_zhuanghao - qhz_zhuanghao % zhuangju;
                zhuanghao.add(qhz_zhuanghao);
                while (true)
                {
                    zhuanghao1 += zhuangju;
                    zhuanghao1 = Round(zhuanghao1,n);
                    if (zhuanghao1 > KHZ)
                    {
                        zhuanghao.add(KHZ);
                        break;
                    }
                    zhuanghao.add(zhuanghao1);
                    if (zhuanghao1 == Math.floor(KZH / zhuangju) * zhuangju)
                    {
                        zhuanghao.add(KZH);
                        continue;
                    }
                    if (zhuanghao1 == Math.floor(KHY / zhuangju) * zhuangju)
                    {
                        zhuanghao.add(KHY);
                        continue;
                    }
                    if (zhuanghao1 == Math.floor(KQZ / zhuangju) * zhuangju)
                    {
                        zhuanghao.add(KQZ);
                        continue;
                    }
                    if (zhuanghao1 == Math.floor(KYH / zhuangju) * zhuangju)
                    {
                        zhuanghao.add(KYH);
                        continue;
                    }
                }
                //endregion

                fangwei = Caculate.fangweijiaojisuan(qhz_X,qhz_Y,jd_X,jd_Y);

                //region 4等分复化辛普生公式计算曲线中桩坐标
                //首先需要定义曲线段主点的曲率，通过曲率计算切线方位角，进而求出坐标
                List<Double> fenduanfangwei = new ArrayList<Double>();
                double QA, QT, ZA, iA, iT,iX,iY;//起终点曲率和切线方位角
                double H;
                for (int i = 0; i < zhuanghao.size(); i++)//遍历所有里程桩号
                {
                    //region 直线段
                    if (zhuanghao.get(i) <= KZH)//当KHZ=QKZH时，也就是直线段为0时，HZ的坐标等于QHZ的坐标
                    {
                        Xzuobiao.add(Round(qhz_X + (zhuanghao.get(i) - zhuanghao.get(0)) * Math.cos(fangwei), n));
                        Yzuobiao.add(Round(qhz_Y + (zhuanghao.get(i) - zhuanghao.get(0)) * Math.sin(fangwei), n));
                        fangweijiao.add(fangwei);//存储弧度值
                        XZH = Xzuobiao.get(i);
                        YZH = Yzuobiao.get(i);
                    }
                    //endregion
                    //region ZH--HY段
                    if (Ls1 == 0)//Ls1 = 0的情况
                    {
                        HYT = fangwei;
                        XHY = XZH;
                        YHY = YZH;
                    }
                    if (zhuanghao.get(i) > KZH && zhuanghao.get(i) <= KHY)//当Ls1=0时，KZH=KHY，以下程序不会被执行,即Ls1！=0的情况
                    {
                        QA = 0;
                        QT = fangwei;
                        ZA = k * 1.0 / banjing;
                        iA = QA + (ZA - QA) * (zhuanghao.get(i) - KZH) / (KHY - KZH);
                        iT = QT + (iA + QA) * (zhuanghao.get(i) - KZH) / 2.0;
                        fangweijiao.add(iT);
                        HYT = iT;

                        fenduanfangwei.clear();
                        fenduanfangwei.add(iT);

                        H = (zhuanghao.get(i) - KZH) / 4;
                        for (double j = KZH + H / 2.0; j < zhuanghao.get(i); j += H / 2.0)
                        {
                            if (fenduanfangwei.size() < 8)//KZH,KZH+H/2,KZH+H,KZH+3H/2,KZH+2H,KZH+5H/2,KZH+3H,KZH+7H/2-----共8个元素
                            {
                                iA = QA + (ZA - QA) * (j - KZH) / (KHY - KZH);
                                iT = QT + (iA + QA) * (j - KZH) / 2.0;
                                fenduanfangwei.add(iT);
                            }
                        }
                        double sinA = 0,cosA = 0,sinB = 0,cosB = 0;//A为2K等分点的切线方位角，B为K等分点的切线方位角
                        for (int j = 0; j < fenduanfangwei.size(); j++)
                        {
                            if (j % 2 == 1)//循环4次
                            {
                                sinA += Math.sin(fenduanfangwei.get(j));
                                cosA += Math.cos(fenduanfangwei.get(j));
                            }
                            else
                            {
                                if (j > 1)//循环3次
                                {
                                    sinB += Math.sin(fenduanfangwei.get(j));
                                    cosB += Math.cos(fenduanfangwei.get(j));
                                }
                            }
                        }
                        //用辛普生公式计算坐标
                        iX = XZH + H * (Math.cos(QT) + 4 * cosA + 2 * cosB + Math.cos(HYT)) / 6;
                        iY = YZH + H * (Math.sin(QT) + 4 * sinA + 2 * sinB + Math.sin(HYT)) / 6;
                        Xzuobiao.add(Round(iX, 3));
                        Yzuobiao.add(Round(iY, 3));
                        XHY = Round(iX, 3);
                        YHY = Round(iY, 3);
                    }
                    //endregion
                    //region HY--YH
                    if (zhuanghao.get(i) > KHY && zhuanghao.get(i) <= KYH)
                    {
                        QA = k * 1.0 / banjing;
                        QT = HYT;
                        ZA = k * 1.0 / banjing;
                        iA = QA + (ZA - QA) * (zhuanghao.get(i) - KHY) / (KYH - KHY);
                        iT = QT + (iA + QA) * (zhuanghao.get(i) - KHY) / 2.0;
                        fangweijiao.add(iT);
                        YHT = iT;

                        fenduanfangwei.clear();
                        fenduanfangwei.add(iT);

                        H = (zhuanghao.get(i) - KHY) / 4;
                        for (double j = KHY + H / 2.0; j < zhuanghao.get(i); j += H / 2.0)
                        {
                            if (fenduanfangwei.size() < 8)//KZH,KZH+H/2,KZH+H,KZH+3H/2,KZH+2H,KZH+5H/2,KZH+3H,KZH+7H/2-----共8个元素
                            {
                                iA = QA + (ZA - QA) * (j - KHY) / (KYH - KHY);
                                iT = QT + (iA + QA) * (j - KHY) / 2.0;
                                fenduanfangwei.add(iT);
                            }
                        }
                        double sinA = 0, cosA = 0, sinB = 0, cosB = 0;//A为2K等分点的切线方位角，B为K等分点的切线方位角
                        for (int j = 0; j < fenduanfangwei.size(); j++)
                        {
                            if (j % 2 == 1)//循环4次
                            {
                                sinA += Math.sin(fenduanfangwei.get(j));
                                cosA += Math.cos(fenduanfangwei.get(j));
                            }
                            else
                            {
                                if (j > 1)//循环3次
                                {
                                    sinB += Math.sin(fenduanfangwei.get(j));
                                    cosB += Math.cos(fenduanfangwei.get(j));
                                }
                            }
                        }
                        //用辛普生公式计算坐标
                        iX = XHY + H * (Math.cos(QT) + 4 * cosA + 2 * cosB + Math.cos(YHT)) / 6;
                        iY = YHY + H * (Math.sin(QT) + 4 * sinA + 2 * sinB + Math.sin(YHT)) / 6;
                        Xzuobiao.add(Round(iX, 3));
                        Yzuobiao.add(Round(iY, 3));
                        XYH = Caculate.Round(iX, 3);
                        YYH = Round(iY, 3);
                    }
                    //endregion
                    //region YH--HZ段
                    if (Ls2 == 0)//Ls2 = 0的情况
                    {
                        HZT = YHT;
                        XHZ = XYH;
                        YHZ = YYH;
                    }
                    if (zhuanghao.get(i) > KYH && zhuanghao.get(i) <= KHZ)
                    {
                        QA = k * 1.0 / banjing;
                        QT = YHT;
                        ZA = 0;
                        iA = QA + (ZA - QA) * (zhuanghao.get(i) - KYH) / (KHZ - KYH);
                        iT = QT + (iA + QA) * (zhuanghao.get(i) - KYH) / 2.0;
                        fangweijiao.add(iT);
                        HZT = iT;

                        fenduanfangwei.clear();
                        fenduanfangwei.add(iT);

                        H = (zhuanghao.get(i) - KYH) / 4;
                        for (double j = KYH + H / 2.0; j < zhuanghao.get(i); j += H / 2.0)
                        {
                            if (fenduanfangwei.size() < 8)//KZH,KZH+H/2,KZH+H,KZH+3H/2,KZH+2H,KZH+5H/2,KZH+3H,KZH+7H/2-----共8个元素
                            {
                                iA = QA + (ZA - QA) * (j - KYH) / (KHZ - KYH);
                                iT = QT + (iA + QA) * (j - KYH) / 2.0;
                                fenduanfangwei.add(iT);
                            }
                        }
                        double sinA = 0, cosA = 0, sinB = 0, cosB = 0;//A为2K等分点的切线方位角，B为K等分点的切线方位角
                        for (int j = 0; j < fenduanfangwei.size(); j++)
                        {
                            if (j % 2 == 1)//循环4次
                            {
                                sinA += Math.sin(fenduanfangwei.get(j));
                                cosA += Math.cos(fenduanfangwei.get(j));
                            }
                            else
                            {
                                if (j > 1)//循环3次
                                {
                                    sinB += Math.sin(fenduanfangwei.get(j));
                                    cosB += Math.cos(fenduanfangwei.get(j));
                                }
                            }
                        }
                        //用辛普生公式计算坐标
                        iX = XYH + H * (Math.cos(QT) + 4 * cosA + 2 * cosB + Math.cos(HZT)) / 6;
                        iY = YYH + H * (Math.sin(QT) + 4 * sinA + 2 * sinB + Math.sin(HZT)) / 6;
                        Xzuobiao.add(Round(iX, 3));
                        Yzuobiao.add(Round(iY, 3));
                        XHZ = Round(iX, 3);
                        YHZ = Round(iY, 3);
                    }
                    //endregion
                } //endregion

                //region 边桩计算
                for (int i = 0; i < Xzuobiao.size(); i++)
                {
                    leftX.add(Round(Xzuobiao.get(i) + zuo * Math.cos(fangweijiao.get(i) - Math.PI / 2),3));
                    leftY.add(Round(Yzuobiao.get(i) + zuo * Math.sin(fangweijiao.get(i) - Math.PI / 2),3));
                    rightX.add(Round(Xzuobiao.get(i) + you * Math.cos(fangweijiao.get(i) + Math.PI / 2), 3));
                    rightY.add(Round(Yzuobiao.get(i) + you * Math.sin(fangweijiao.get(i) + Math.PI / 2), 3));
                }
                //endregion

                //region 数据导出
                str = "主点要素:" + "\n" + "p1 = " + p1 + "   " + " p2 = " + p2 + "\n" + "q1 = " + q1 + "   " + " q2 = " + q2 + "\n" + "T1 = " + T1 + "   " + " T2 = " + T2 + "\n\n";
                str += "圆曲线长 Ly = " + LY + "\n" + "平曲线 L = " + LH + "\n" + "外矢距 E = " + E + "\n" + "切曲差 D = " + DH + "\n\n";
                str += "主点里程桩号：\n" + "KZH = " + KZH + "   " + " KHY = " + KHY + "\n" + "KQZ = " + KQZ + "   " + " KYH = " + KYH + "\n" + "KHZ = " + KHZ + "\n\n";

                str1 = null;
                str1 = String.format("%-20s", "桩号") + String.format("%-20s", "X坐标") + String.format("%-20s", "Y坐标") + String.format("%-20s", "切线方位角(d.ms)")
                        + String.format("%-20s", "左边桩X") + String.format("%-20s", "左边桩Y") + String.format("%-20s", "右边桩X") + String.format("%-20s", "右边桩Y") + "\n\n";
                for (int i = 0; i < zhuanghao.size(); i++)
                {
                    str1 += String.format("%-20.3f", zhuanghao.get(i)) + String.format("%-25.3f", Xzuobiao.get(i)) + String.format("%-20.3f", Yzuobiao.get(i)) + String.format("%-20f", Caculate.hudutodms(fangweijiao.get(i)));
                    str1 += String.format("%-20.3f", leftX.get(i)) + String.format("%-20.3f", leftY.get(i)) + String.format("%-20.3f", rightX.get(i)) + String.format("%-20.3f",rightY.get(i)) + "\n";
                }
                //endregion

                Intent intent = new Intent(XianluActivity.this,Xianlu_resultActivity.class);
                startActivity(intent);
            }
        });
    }
}
