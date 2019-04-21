package com.example.miss.Zuobiao;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miss.Caculate;
import com.example.miss.Dadi.DadiActivity;
import com.example.miss.R;
import com.example.miss.Xianlu.Xianlu_exampleActivity;
import com.example.miss.Xianlu.Xianlu_explainActivity;

public class ZuobiaoActivity extends AppCompatActivity {

    //region 定义变量
    private Toolbar toolbar;
    private RadioButton rd_Krassovsky,rd_ICGG_1975,rd_WGS_84,rd_CGCS2000;
    private RadioButton rd_6,rd_3;
    private EditText ed11,ed12,ed21,ed22,ed31,ed32;
    private TextView t1,t2;
    private Button button;
    private double a, b, c, f, e1, e2;//基本椭球参数
    private boolean BLH_XYZ,XYZ_BLH,BLH_xyH,xyH_BLH;//记录用户选择的转换方式
    private double B,L,H,X,Y,Z,x,y;
    private int k;//存储3度6度带信息
    //endregion

    //region 说明菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.zuobiao,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.zuobiao_item1){
            Intent intent = new Intent(ZuobiaoActivity.this,Zuobiao_explainActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.zuobiao_item2){
            Intent intent = new Intent(ZuobiaoActivity.this,Zuobiao_exampleActivity.class);
            startActivity(intent);
        }
        return true;
    }
    //endregion

    //region 使用示例数据
    protected void onResume() {
        super.onResume();
        //Log.i("Activity生命周期","OnResume方法调用");

        if (Zuobiao_exampleActivity.example_dadi_zhijiao) {
            //region 正算测试数据
            ed11.setText(String.valueOf(33.4455666));
            ed21.setText(String.valueOf(77.1122333));
            ed31.setText(String.valueOf(5555.66));
            //endregion
        }
        if (Zuobiao_exampleActivity.example_zhijiao_dadi) {
            //region 反算测试数据
            ed11.setText(String.valueOf(1177888.777));
            ed21.setText(String.valueOf(5166777.888));
            ed31.setText(String.valueOf(3544555.666));
            //endregion
        }
        if (Zuobiao_exampleActivity.example_dadi_gaosi) {
            //region 反算测试数据
            ed11.setText(String.valueOf(35.264038));
            ed21.setText(String.valueOf(115.085122));
            ed31.setText(String.valueOf(100));
            //endregion
        }
        if (Zuobiao_exampleActivity.example_gaosi_dadi) {
            //region 反算测试数据
            ed11.setText(String.valueOf(3354874.257));
            ed21.setText(String.format("%f",20500386.567));
            ed31.setText(String.valueOf(100));
            //endregion
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        //Log.i("Activity生命周期","onStop方法调用");
        Zuobiao_exampleActivity.example_dadi_zhijiao = false;
        Zuobiao_exampleActivity.example_zhijiao_dadi = false;
        Zuobiao_exampleActivity.example_dadi_gaosi = false;
        Zuobiao_exampleActivity.example_gaosi_dadi = false;
    }
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zuobiao);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不让自动弹出软键盘

        //region 找到控件
        rd_Krassovsky = (RadioButton) findViewById(R.id.zuobiao_Krassovsky);
        rd_ICGG_1975 = (RadioButton) findViewById(R.id.zuobiao_ICGG1975);
        rd_WGS_84 = (RadioButton) findViewById(R.id.zuobiao_WGS_84);
        rd_CGCS2000 = (RadioButton) findViewById(R.id.zuobiao_CGCS2000);
        rd_6 = (RadioButton) findViewById(R.id.zuobiao_6dudai);
        rd_3 = (RadioButton) findViewById(R.id.zuobiao_3dudai);

        ed11 = (EditText) findViewById(R.id.zuobiao_ed11);
        ed12 = (EditText) findViewById(R.id.zuobiao_ed12);
        ed21 = (EditText) findViewById(R.id.zuobiao_ed21);
        ed22 = (EditText) findViewById(R.id.zuobiao_ed22);
        ed31 = (EditText) findViewById(R.id.zuobiao_ed31);
        ed32 = (EditText) findViewById(R.id.zuobiao_ed32);

        t1 = (TextView)findViewById(R.id.zuobiao_t1) ;
        t2 = (TextView)findViewById(R.id.zuobiao_t2) ;

        button = (Button)findViewById(R.id.zuobiao_button);
        //endregion

        //region 标题栏加返回箭头
        toolbar = (Toolbar)findViewById(R.id.toolbar_zuobiao);
        toolbar.setTitle("坐标转换");//设置Toolbar标题
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
        //endregion

        //region 下拉菜单
        Resources res =getResources();
        String[] choose = res.getStringArray(R.array.translate);//将translate中内容添加到数组choose中
        final Spinner spinner = (Spinner) findViewById(R.id.spacer);//获取到spacer
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,choose);//创建Arrayadapter适配器
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//通过此方法为下拉列表设置点击事件
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = spinner.getItemAtPosition(i).toString();
                //Toast.makeText(ZuobiaoActivity.this,"你选择了" + text,Toast.LENGTH_SHORT).show();
                //Log.d("Activity生命周期", String.valueOf(i));
                //每次选择之前清空上次选择信息
                BLH_xyH = false;
                BLH_XYZ = false;
                XYZ_BLH = false;
                xyH_BLH = false;
                ed11.setText("");
                ed21.setText("");
                ed31.setText("");
                ed12.setText("");
                ed22.setText("");
                ed32.setText("");
                if (i == 0){
                    t1.setText("大地坐标");
                    t2.setText("空间直角坐标");
                    ed11.setHint("B(dms)");
                    ed21.setHint("L(dms)");
                    ed31.setHint("H(m)");
                    ed12.setHint("X(m)");
                    ed22.setHint("Y(m)");
                    ed32.setHint("Z(m)");
                    BLH_XYZ = true;
                }
                else if (i == 1){
                    t1.setText("空间直角坐标");
                    t2.setText("大地坐标");
                    ed11.setHint("X(m)");
                    ed21.setHint("Y(m)");
                    ed31.setHint("Z(m)");
                    ed12.setHint("B(dms)");
                    ed22.setHint("L(dms)");
                    ed32.setHint("H(m)");
                    XYZ_BLH = true;
                }
                else if (i == 2){
                    t1.setText("大地坐标");
                    t2.setText("高斯平面坐标");
                    ed11.setHint("B(dms)");
                    ed21.setHint("L(dms)");
                    ed31.setHint("H(m)");
                    ed12.setHint("x(m)");
                    ed22.setHint("y(m)");
                    ed32.setHint("H(m)");
                    BLH_xyH = true;
                }
                else if (i == 3){
                    t1.setText("高斯平面坐标");
                    t2.setText("大地坐标");
                    ed11.setHint("x(m)");
                    ed21.setHint("y(m)");
                    ed31.setHint("H(m)");
                    ed12.setHint("B(dms)");
                    ed22.setHint("L(dms)");
                    ed32.setHint("H(m)");
                    xyH_BLH = true;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //endregion

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //region 椭球选择
                if (rd_Krassovsky.isChecked()){
                    a = 6378245;//长半轴
                    //b = 6356863.018773;//短半轴
                    //c = 6399698.90178271;//极曲率半径
                    //f = 1 / 298.3;//扁率
                    e1 = 0.0066934216230;//第一偏心率的平方
                    e2 = 0.0067385254147;//第二偏心率的平方
                }
                else if (rd_ICGG_1975.isChecked()){
                    a = 6378140;
                    //b = 6356755.288157;
                    //c = 6399596.65198801;
                    //f = 1 / 298.257;
                    e1 = 0.0066943849996;
                    e2 = 0.0067395018195;
                }
                else if (rd_WGS_84.isChecked()){
                    a = 6378137;
                    //b = 6356752.314245;
                    //c = 6399596.625758;
                    //f = 1 / 298.257223563;
                    e1 = 0.006694379990;
                    e2 = 0.006739496742;
                }
                else if (rd_CGCS2000.isChecked()){
                    a = 6378137;
                    //b = 6356752.314140;
                    //c = 6399596.625864;
                    //f = 1 / 298.207222101;
                    e1 = 0.006694380022;
                    e2 = 0.006739496775;
                }
                //endregion
                //region 分带选择
                if (rd_6.isChecked()){
                    k = 1;
                }
                if (rd_3.isChecked()){
                    k = 0;
                }
                //endregion
                //region 数据导入
                try{
                    if(BLH_XYZ){
                        B = Caculate.dmstohudu(Double.parseDouble(ed11.getText().toString()));
                        L = Caculate.dmstohudu(Double.parseDouble(ed21.getText().toString()));
                        H = Double.parseDouble(ed31.getText().toString());
                    }
                    if(XYZ_BLH){
                        X = Double.parseDouble(ed11.getText().toString());
                        Y = Double.parseDouble(ed21.getText().toString());
                        Z = Double.parseDouble(ed31.getText().toString());
                    }
                    if(BLH_xyH){
                        B = Double.parseDouble(ed11.getText().toString());
                        L = Double.parseDouble(ed21.getText().toString());
                        H = Double.parseDouble(ed31.getText().toString());
                    }
                    if(xyH_BLH){
                        x = Double.parseDouble(ed11.getText().toString());
                        y = Double.parseDouble(ed21.getText().toString());
                        H = Double.parseDouble(ed31.getText().toString());
                    }
                }
                catch (Exception e){
                    Toast.makeText(ZuobiaoActivity.this, "请正确填写全部数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                //endregion
                //region 坐标转换
                //region 大地转直角
                if(BLH_XYZ) {
                    double N = a / Math.sqrt(1 - e1 * Math.sin(B) * Math.sin(B));//法线长
                    X = (N + H) * Math.cos(B) * Math.cos(L);
                    Y = (N + H) * Math.cos(B) * Math.sin(L);
                    Z = (N * (1 - e1) + H) * Math.sin(B);

                    ed12.setText(String.format("%f",Caculate.Round(X,6)));
                    ed22.setText(String.format("%f",Caculate.Round(Y,6)));
                    ed32.setText(String.format("%f",Caculate.Round(Z,6)));
                }
                //endregion
                //region 直角转大地
                if(XYZ_BLH){
                    double B0, B1, Bs;//迭代初值,TAN B0
                    double N;
                    //L = Math.Atan(Y / X);//L的值域为-90~90度
                    L = Math.atan2(Y, X);//atan2值域为-180~180度，可以很好地表示反正切值
                    if (L < 0)
                    {
                        L = Math.PI * 2 + L;
                    }

                    B0 = Z / Math.sqrt(X * X + Y * Y);
                    do
                    {
                        B1 = (Z + a * e1 * B0 /Math.sqrt(1 + (1 - e1) * B0 * B0)) / Math.sqrt(X * X + Y * Y);
                        Bs = B1 - B0;
                        B0 = B1;
                    }
                    while (Math.abs(Bs) > Math.tan(Caculate.dmstohudu(0.00000001)));//限差为0.0001秒
                    B = Math.atan(B1);

                    N = a / Math.sqrt(1 - e1 * Math.sin(B) * Math.sin(B));
                    H = Math.sqrt(X * X + Y * Y) / Math.cos(B) - N;

                    L = Caculate.hudutodms(L);
                    B = Caculate.hudutodms(B);

                    ed12.setText(String.valueOf(B));
                    ed22.setText(String.valueOf(L));
                    ed32.setText(String.format("%f",Caculate.Round(H,6)));
                }
                //endregion
                //region 大地转高斯
                if(BLH_xyH){
                    //公式精确到0.001米
                    double a0, a2, a4, a6, a8;
                    double m0, m2, m4, m6, m8;
                    double yita2, t, N;
                    double X;//子午线弧长
                    double l,L0;//经差以及中央子午线
                    double n;// 辅助量
                    double daihao;//带号
                    //region 判断分带
                    if (k == 1)//6度带
                    {
                        if (L % 6 == 0)
                        {
                            daihao = (int)(L / 6);
                            L0 = 6 * daihao - 3;
                        }
                        else
                        {
                            daihao = (int)(L / 6) + 1;
                            L0 = 6 * daihao - 3;
                        }
                    }
                    else //3度带
                    {
                        if ((L - 1.5) % 3 == 0)
                        {
                            daihao = (int)((L - 1.5) / 3);//int 强制类型转换返回最接近0的整数部分，-0.1 返回 0，0.1 返回 0
                            L0 = 3 * daihao;
                        }
                        else
                        {
                            daihao = (int)Math.floor((L - 1.5) / 3) + 1;//math.floor 返回原类型小于原数值的整数部分-0.1 返回 -1， 0.1 返回 0
                            L0 = 3 * daihao;
                        }
                    }
                    //endregion
                    l = Caculate.dmstohudu(L) - Caculate.dmstohudu(L0);
                    B = Caculate.dmstohudu(B);
                    //L = Caculates.dmstohudu(L);
                    //region 计算子午线弧长
                    m0 = a * (1 - e1);
                    m2 = 3 * e1 * m0 / 2;
                    m4 = 5 * e1 * m2 / 4;
                    m6 = 7 * e1 * m4 / 6;
                    m8 = 9 * e1 * m6 / 8;

                    a0 = m0 + m2 / 2 + 3 * m4 / 8 + 5 * m6 / 16 + 35 * m8 / 128;
                    a2 = m2 / 2 + m4 / 2 + 15 * m6 / 32 + 7 * m8 / 16;
                    a4 = m4 / 8 + 3 * m6 / 16 + 7 * m8 / 32;
                    a6 = m6 / 32 + m8 / 16;
                    a8 = m8 / 128;
                    //子午线弧长计算公式2
                    X = a0 * B - a2 * Math.sin(2 * B) / 2 + a4 * Math.sin(4 * B) / 4 - a6 * Math.sin(6 * B) / 6 + a8 * Math.sin(8 * B) / 8;
                    //endregion
                    yita2 = e2 * Math.cos(B) * Math.cos(B);//η平方
                    t = Math.tan(B);
                    N = a / Math.sqrt(1 - e1 * Math.sin(B) * Math.sin(B));
                    //region 计算高斯平面坐标
                    n = Math.cos(B) * l;
                    x = X + N * t * (n * n / 2 + Math.pow(n, 4) * (5 - t * t + 9 * yita2 + 4 * yita2 * yita2) / 24 + Math.pow(n, 6) * (61 - 58 * t * t + Math.pow(t, 4)) / 720);
                    y = N * (n + Math.pow(n, 3) * (1 - t * t + yita2) / 6 + Math.pow(n, 5) * (5 - 18 * t * t + Math.pow(t, 4) + 14 * yita2 - 58 * yita2 * t * t) / 120);
                    y = y + 500000 + daihao * 1000000;//计算高斯通用坐标
                    //endregion
                    ed12.setText(String.format("%f",Caculate.Round(x,6)));
                    ed22.setText(String.format("%f",Caculate.Round(y,6)));
                    ed32.setText(String.format("%f",Caculate.Round(H,6)));
                }
                //endregion
                //region 高斯转大地
                if(xyH_BLH){
                    //公式精确到0.0001"
                    double a0, a2, a4, a6, a8;
                    double m0, m2, m4, m6, m8;
                    double Bfs,Bf0,Bfi,FBf;//定义迭代变量
                    double tf, yitaf2, Nf, Vf;
                    double l, L0;//经差以及中央子午线
                    double daihao;//带号
                    daihao = (int)(y / 1000000);
                    y = y - daihao * 1000000 - 500000;
                    //region 判断分带
                    if (k == 1)//6度带
                    {
                        L0 = 6 * daihao - 3;
                    }
                    else  //3度带
                    {
                        L0 = 3 * daihao;
                    }
                    //endregion
                    //region 求底点纬度
                    m0 = a * (1 - e1);
                    m2 = 3 * e1 * m0 / 2;
                    m4 = 5 * e1 * m2 / 4;
                    m6 = 7 * e1 * m4 / 6;
                    m8 = 9 * e1 * m6 / 8;

                    a0 = m0 + m2 / 2 + 3 * m4 / 8 + 5 * m6 / 16 + 35 * m8 / 128;
                    a2 = m2 / 2 + m4 / 2 + 15 * m6 / 32 + 7 * m8 / 16;
                    a4 = m4 / 8 + 3 * m6 / 16 + 7 * m8 / 32;
                    a6 = m6 / 32 + m8 / 16;
                    a8 = m8 / 128;
                    //底点纬度 当x = X时，x轴上点的纬度,用子午线弧长的公式倒推迭代出来
                    Bf0 = x / a0;
                    do
                    {
                        FBf = - a2 * Math.sin(2 * Bf0) / 2 + a4 * Math.sin(4 * Bf0) / 4 - a6 * Math.sin(6 * Bf0) / 6 + a8 * Math.sin(8 * Bf0) / 8;
                        Bfi = (x - FBf) / a0;
                        Bfs = Bfi - Bf0;
                        Bf0 = Bfi;
                    }
                    while (Math.abs(Bfs) > Caculate.dmstohudu(0.0000001));
                    //endregion
                    //region 求经纬度
                    yitaf2 = e2 * Math.cos(Bfi) * Math.cos(Bfi);
                    tf = Math.tan(Bfi);
                    Nf = a / Math.sqrt(1 - e1 * Math.sin(Bfi) * Math.sin(Bfi));
                    Vf = Math.sqrt(1 + e2 * Math.cos(Bfi) * Math.cos(Bfi));

                    B = Bfi - Vf * Vf * tf * (Math.pow((y / Nf),2) - (5 + 3 * tf * tf + yitaf2 - 9 * yitaf2 * tf * tf) * Math.pow((y / Nf), 4) / 12 + (61 + 90 * tf * tf + 45 * tf * tf) * Math.pow((y / Nf),6) / 360) / 2;
                    l = (y / Nf - (1 + 2 * tf * tf + yitaf2) * Math.pow((y / Nf), 3) / 6 + (5 + 28 * tf * tf + 24 * Math.pow(tf, 4) + 6 * yitaf2 + 8 * yitaf2 * tf * tf) * Math.pow((y / Nf), 5) / 120) / Math.cos(Bfi);
                    B = Caculate.hudutodms(B);
                    L = Caculate.hudutodms(l + Caculate.dmstohudu(L0));
                    //endregion
                    ed12.setText(String.valueOf(B));
                    ed22.setText(String.valueOf(L));
                    ed32.setText(String.format("%f",Caculate.Round(H,6)));
                }
                //endregion
                //endregion
            }
        });
    }
}
