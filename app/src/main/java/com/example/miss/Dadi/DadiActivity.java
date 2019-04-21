package com.example.miss.Dadi;

import android.content.Intent;
import android.graphics.Color;
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
import com.example.miss.Xianlu.XianluActivity;
import com.example.miss.Xianlu.Xianlu_exampleActivity;
import com.example.miss.Xianlu.Xianlu_explainActivity;

public class DadiActivity extends AppCompatActivity {

    //region 定义变量
    private Toolbar toolbar;
    private RadioGroup radioGroup;
    private RadioButton rd_Krassovsky,rd_ICGG_1975,rd_WGS_84,rd_CGCS2000;
    private EditText mB1,mL1,mB2,mL2,mA12,mA21,mS;
    private Button button;
    private boolean iszheng = true;
    private double a, b, c, f, e1, e2;//基本椭球参数
    private double B1,B2,L1,L2,A12,A21,S;//正反算要素变量
    //endregion

    //region 说明菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dadi,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.dadi_item1){
            Intent intent = new Intent(DadiActivity.this,Dadi_explainActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.dadi_item2){
            Intent intent = new Intent(DadiActivity.this,Dadi_exampleActivity.class);
            startActivity(intent);
        }
        return true;
    }
    //endregion
    protected void onResume() {
        super.onResume();
        //Log.i("Activity生命周期","OnResume方法调用");

        if (Dadi_exampleActivity.example_zheng) {
            //region 正算测试数据
            mB1.setText(String.valueOf(35.000022));
            mL1.setText(String.valueOf(90.000011));
            mA12.setText(String.valueOf(100.000033));
            mS.setText(String.format("%f",15000000.2));

            //endregion
        }
        if (Dadi_exampleActivity.example_fan) {
            //region 反算测试数据
            mB1.setText(String.valueOf(35.000022));
            mL1.setText(String.valueOf(90.000011));
            mA12.setText(String.valueOf(-30.292096));
            mS.setText(String.valueOf(215.590434));
            //endregion
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        //Log.i("Activity生命周期","onStop方法调用");
        Dadi_exampleActivity.example_zheng = false;
        Dadi_exampleActivity.example_fan = false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dadi);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不让自动弹出软键盘

        //region 标题栏加返回箭头
        toolbar = (Toolbar)findViewById(R.id.toolbar_dadi);
        toolbar.setTitle("大地主题解算");//设置Toolbar标题
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
        //region 找到控件
        rd_Krassovsky = (RadioButton) findViewById(R.id.dadi_Krassovsky);
        rd_ICGG_1975 = (RadioButton) findViewById(R.id.dadi_ICGG1975);
        rd_WGS_84 = (RadioButton) findViewById(R.id.dadi_WGS_84);
        rd_CGCS2000 = (RadioButton) findViewById(R.id.dadi_CGCS2000);

        mB1 = (EditText) findViewById(R.id.dadi_B1);
        mL1 = (EditText) findViewById(R.id.dadi_L1);
        mB2 = (EditText) findViewById(R.id.dadi_B2);
        mL2 = (EditText) findViewById(R.id.dadi_L2);
        mA12 = (EditText) findViewById(R.id.dadi_A12);
        mA21 = (EditText) findViewById(R.id.dadi_A21);
        mS = (EditText) findViewById(R.id.dadi_S);

        button = (Button)findViewById(R.id.dadi_button);
        //endregion
        //region 正算反算选择
        radioGroup = (RadioGroup)findViewById(R.id.dadi_rg1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup1, int checkedId) {
                RadioButton radioButton = (RadioButton) radioGroup1.findViewById(checkedId);//checkedId表示被选择的那个CheckedButton
                //Toast.makeText(DadiActivity.this,"你选择了" + radioButton.getText(),Toast.LENGTH_SHORT).show();
                mB1.setText("");mL1.setText("");mA12.setText("");mA21.setText("");
                mB2.setText("");mL2.setText("");mS.setText("");

                if (checkedId == R.id.dadi_fan){
                    mA12.setHint("B2");
                    mS.setHint("L2");
                    mB2.setHint("A12");
                    mL2.setHint("A21");
                    mA21.setHint("S");
                    iszheng = false;
                }
                else {
                    mB2.setHint("B2");
                    mL2.setHint("L2");
                    mA12.setHint("A12");
                    mA21.setHint("A21");
                    mS.setHint("S");
                    iszheng = true;
                }
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

                //region 正算
                if (iszheng == true) {

                    //region 数据导入
                    try {//运行try_catch时程序出现异常不会使程序崩溃
                        B1 = Caculate.dmstohudu(Double.parseDouble(mB1.getText().toString()));//传入数据,转换成double类型
                        L1 = Caculate.dmstohudu(Double.parseDouble(mL1.getText().toString()));
                        A12 = Caculate.dmstohudu(Double.parseDouble(mA12.getText().toString()));
                        S = Double.parseDouble(mS.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(DadiActivity.this, "请填写全部数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //Log.d("Activity生命周期", String.valueOf(A12));
                    //endregion
                    //region 将椭球面投影到平面
                    double u1 = 0, u2 = 0, m = 0, M = 0, l = 0;//球面元素变量
                    u1 = Math.atan(Math.sqrt(1.0 - e1) * Math.tan(B1));//由B1求u1，归化纬度
                    m = Math.asin(Math.cos(u1) * Math.sin(A12));//辅助量m,m取值在第一和第四象限，不能为负
                    if (m <= 0)
                    {
                        m = m + Math.PI * 2;
                    }
                    M = Math .atan(Math.tan(u1) / Math.cos(A12));//辅助量M,取值在一二象限,反三角函数定义域为-90~90
                    if (M <= 0)
                    {
                        M = M + Math.PI;
                    }
                    //将S化为σ
                    double k2 = 0, k4 = 0, k6 = 0, af = 0, bf = 0, cf = 0, d0 = 0, d1 = 0,ds = 0;//定义迭代变量，求边长归化量
                    k2 = e2 * Math.cos(m) * Math.cos(m);
                    k4 = k2 * k2;
                    k6 = k2 * k2 * k2;
                    af = Math.sqrt(1 + e2) * (1 - (k2) / 4 + (7 * k4) / 64 - (15 * k6) / 256) / a;//α
                    bf = k2 / 4 - k4 / 8 + (37 * k6) / 512;//β
                    cf = k4 / 128 - k6 / 128;//γ
                    d0 = af * S;//迭代初值σ0
                    do
                    {
                        d1 = af * S + bf * Math.sin(d0) * Math.cos(2 * M + d0) + cf * Math.sin(2 * d0) * Math.cos(4 * M + 2 * d0); //σ
                        ds = d1 - d0;
                        d0 = d1;
                    }
                    while(ds > Caculate.dmstohudu(0.00000001));//限差0.0001秒
                    //endregion
                    //region 在球面上进行计算
                    A21 = Math.atan(Math.tan(m) / Math.cos(M + d1));
                    if (A21 <= 0)
                    {
                        A21 = A21 + Math.PI;//这里由于反正切三角函数值域问题，算出来还是球面的A21
                    }
                    if (A12 <= Math.PI)
                    {
                        A21 = A21 + Math.PI;//计算椭球面的A21
                    }
                    u2 = Math.atan(-1 * Math.cos(A21) * Math.tan(M + d1));
                    B2 = Math.atan(Math.sqrt(1 + e2) * Math.tan(u2));
                    double r1 = 0, r2 = 0, rs = 0, kk2 = 0, kk4 = 0, e14 = 0, e16 = 0, aaf = 0, bbf = 0, ccf = 0;//定义球面计算变量
                    r1 = Math.atan(Math.sin(u1) * Math.tan(A12));
                    if (r1 <= 0)
                    {
                        r1 = r1 + Math.PI;
                    }
                    if (m >= Math.PI)
                    {
                        r1 = r1 + Math.PI;
                    }
                    r2 = Math.atan(Math.sin(u2) * Math.tan(A21));
                    if (r2 <= 0)
                    {
                        r2 = r2 + Math.PI;
                    }
                    if (m < Math.PI)
                    {
                        if ((M + d1) >= Math.PI)
                        {
                            r2 = r2 + Math.PI;
                        }
                    }
                    else
                    {
                        if ((M + d1) <= Math.PI)
                        {
                            r2 = r2 + Math.PI;
                        }
                    }
                    rs = r2 - r1;
                    //endregion
                    //region 将球面元素归算到椭球面
                    kk2 = e1 * Math.cos(m) * Math.cos(m);
                    kk4 = kk2 * kk2;
                    e14 = e1 * e1;
                    e16 = e1 * e1 * e1;
                    aaf = (e1 / 2 + e14 / 8 + e16 / 16) - e1 * (1 + e1) * kk2 / 16 + 3 * e1 * kk4 / 128;
                    bbf = e1 * (1 + e1) * kk2 / 16 - e1 * kk4 / 32;
                    ccf = e1 * kk4 / 256;
                    l = rs - Math.sin(m) * (aaf * d1 + bbf * Math.sin(d1) * Math.cos(2 * M + d1) + ccf * Math.sin(2 * d1) * Math.cos(4 * M + 2 * d1));
                    L2 = L1 + l;
                    //endregion
                    //region 数据导出
                    mB2.setText(String.valueOf(Caculate.hudutodms_no(B2)));
                    mL2.setText(String.valueOf(Caculate.hudutodms_no(L2)));
                    mA21.setText(String.valueOf(Caculate.hudutodms_no(A21)));
                    //endregion
                }
                //endregion
                //region 反算
                if (iszheng == false) {
                    //region 数据导入
                    try {//运行try_catch时程序出现异常不会使程序崩溃
                        B1 = Caculate.dmstohudu(Double.parseDouble(mB1.getText().toString()));//传入数据,转换成double类型
                        L1 = Caculate.dmstohudu(Double.parseDouble(mL1.getText().toString()));
                        B2 = Caculate.dmstohudu(Double.parseDouble(mA12.getText().toString()));
                        L2 = Caculate.dmstohudu(Double.parseDouble(mS.getText().toString()));
                    } catch (Exception e) {
                        Toast.makeText(DadiActivity.this, "请填写全部数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.d("Activity生命周期", String.valueOf(B2));
                    //endregion
                    //region 将椭球面投影到平面
                    double u1 = 0, u2 = 0, m = 0,m0 = 0,ms = 0, M = 0, l = 0,d0 = 0,d1 = 0,d = 0,ds = 0,r0 = 0,r = 0,dr = 0,A1 = 0;//球面元素变量
                    double kk2 = 0, kk4 = 0, e14 = 0, e16 = 0, aaf = 0, bbf = 0, ccf = 0;//定义计算球面经差λ的中间变量
                    double k2 = 0, k4 = 0, k6 = 0, af = 0, bf = 0, cf = 0;//定义迭代变量，求边长归化量
                    u1 = Math.atan(Math.sqrt(1 - e1) * Math.tan(B1));//归化纬度
                    u2 = Math.atan(Math.sqrt(1 - e1) * Math.tan(B2));
                    l = L2 - L1;//椭球面经差
                    d0 = Math.acos(Math.sin(u1) * Math.sin(u2) + Math.cos(u1) * Math.cos(u2) * Math.cos(l));//用椭球面l近似替代球面经差λ，求近似大圆弧长σ
                    m0 = Math.asin(Math.cos(u1) * Math.cos(u2) * Math.sin(l) / Math.sin(d0));
                    do
                    {
                        dr = 0.003351 * d0 * Math.sin(m0);//Δλ
                        r0 = l + dr;//λ0近似球面经差
                        d1 = d0 + Math.sin(m0) * dr;
                        m = Math.asin(Math.cos(u1) * Math.cos(u2) * Math.sin(r0) / Math.sin(d1));
                        ms = m - m0;
                        m0 = m;
                    }
                    while(Math.abs(ms) > Caculate.dmstohudu(0.0000001));//精度取值到0.001秒
                    A1 = Math.atan(Math.sin(r0) / (Math.cos(u1) * Math.tan(u2) - Math.sin(u1) * Math.cos(r0)));//求A12的近似值，为了计算M
                    if (A1 <= 0)
                    {
                        A1 = A1 + Math.PI;
                    }
                    if (m <= 0)
                    {
                        A1 = A1 + Math.PI;
                    }
                    M = Math.atan(Math.sin(u1) * Math.tan(A1) / Math.sin(m));
                    if (M <= 0)
                    {
                        M = M + Math.PI;
                    }
                    kk2 = e1 * Math.cos(m) * Math.cos(m);//计算球面经差λ的中间变量
                    kk4 = kk2 * kk2;
                    e14 = e1 * e1;
                    e16 = e1 * e1 * e1;
                    aaf = (e1 / 2 + e14 / 8 + e16 / 16) - e1 * (1 + e1) * kk2 / 16 + 3 * e1 * kk4 / 128;//α'
                    bbf = e1 * (1 + e1) * kk2 / 16 - e1 * kk4 / 32;//β'
                    ccf = e1 * kk4 / 256;//γ'
                    do
                    {
                        r = l + Math.sin(m) * (aaf * d1 + bbf * Math.sin(d1) * Math.cos(2 * M + d1) + ccf * Math.sin(2 * d1) * Math.cos(4 * M + 2 * d1));//λ球面经差
                        d = Math.acos(Math.sin(u1) * Math.sin(u2) + Math.cos(u1) * Math.cos(u2) * Math.cos(r));//球面大圆弧长σ
                        ds = d - d1;
                        d1 = d;
                    }
                    while(Math.abs(ds) > Caculate.dmstohudu(0.0000001));
                    //endregion
                    //region 在球面上进行解算A12,A21,不用归算
                    A12 = Math.atan(Math.sin(r) / (Math.cos(u1) * Math.tan(u2) - Math.sin(u1) * Math.cos(r)));
                    if (A12 <= 0)
                    {
                        A12 = A12 + Math.PI;
                    }
                    if (m <= 0)
                    {
                        A12 = A12 + Math.PI;
                    }
                    A21 = Math.atan(Math.sin(r) / (Math.sin(u2) * Math.cos(r) - Math.tan(u1) * Math.cos(u2)));
                    if (A21 <= 0)
                    {
                        A21 = A21 + Math.PI;
                    }
                    if (m >= 0)
                    {
                        A21 = A21 + Math.PI;
                    }
                    //endregion
                    //region 将球面元素归算到椭球面
                    k2 = e2 * Math.cos(m) * Math.cos(m);
                    k4 = k2 * k2;
                    k6 = k2 * k2 * k2;
                    af = Math.sqrt(1 + e2) * (1 - (k2) / 4 + (7 * k4) / 64 - (15 * k6) / 256) / a;//α
                    bf = k2 / 4 - k4 / 8 + (37 * k6) / 512;//β
                    cf = k4 / 128 - k6 / 128;//γ
                    S = (d - bf * Math.sin(d) * Math.cos(2 * M + d) - cf * Math.sin(2 * d) * Math.cos(4 * M + 2 * d)) / af;
                    //endregion
                    //region 数据导出
                    mB2.setText(String.valueOf(Caculate.hudutodms_no(A12)));
                    mL2.setText(String.valueOf(Caculate.hudutodms_no(A21)));
                    mA21.setText(String.format("%f",S));
                    //endregion
                }
                //endregion
            }
        });
    }
}
