package com.egt.citysdktest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import newcity56.Listener.OnBindResultListener;
import newcity56.Listener.OnBindSendResultListener;
import newcity56.Listener.OnSignListener;
import newcity56.Listener.OnSignQueryListener;
import newcity56.entity.Device_Data;
import newcity56.entity.TransportDeviceBean;
import newcity56.helper.BindQueryHelper;
import newcity56.helper.BindSendHelper;
import newcity56.helper.OrderSignHelper;
import newcity56.helper.SignQueryHelper;

public class MainActivity extends AppCompatActivity {// BluetoothResultListener,
    String TAG = "MainActivity";
    private BindSendHelper bsh2222;
    private BindQueryHelper bqh1111;
    private OrderSignHelper osh444;
    private SignQueryHelper sqh333;
    private Button mBtnSerch1, mBtnBindOrder2, mBtnSign44, mBtnSign3;
    private EditText mEt;
    private TextView mTitleTv;
    private TransportDeviceBean mSignQueryResult;
    private TransportDeviceBean mBindQueryResult;
    private ProgressDialog materialDialog;
    private int ErroCode100 = 100;// 输入蓝牙sn错误
    private int ErroCode101 = 101;//未发现蓝牙设备
    private int ErroCode102 = 102;//service = null连接失败...稍后再试
    private int ErroCode103 = 103;//超时
    private int ErroCode104 = 104;//连接中断
    private int ErroCode105 = 105;//温度计时差相差五分钟
    private int ErroCode106 = 106;//温度计返回数据错误，请重新连接
    private int ErroCode107 = 107;//连接错误
    private int ErroCode108 = 108;//不允许重复绑定运单
    private int ErroCode109 = 109;//预签收失败
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitleTv = (TextView) findViewById(R.id.titleTv);
        mBtnSerch1 = (Button) findViewById(R.id.conn_btn1);
        mBtnBindOrder2 = (Button) findViewById(R.id.conn_btn2);
        mBtnSign44 = (Button) findViewById(R.id.conn_btn4);
        mBtnSign3 = (Button) findViewById(R.id.conn_btn3);
        mEt = (EditText) findViewById(R.id.mCodeEt);
        materialDialog = new ProgressDialog(this);
        materialDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//转盘
        materialDialog.setCancelable(false);
        materialDialog.setMessage("数据加载中...");
        materialDialog.setTitle("提示");
        bqh1111 = new BindQueryHelper(this, new OnBindResultListener() {
            @Override
            public void onBegin() {
                Log.e("onBegin", "初始化操作,eg:show diag()");
                materialDialog.show();
            }

            @Override
            public void onSucceed(TransportDeviceBean mBoxList) {
                if (mBoxList != null) {
                    mBindQueryResult = mBoxList;
                    mTitleTv.setText(mBoxList.toString());
                }
                materialDialog.dismiss();
            }

            @Override
            public void onFail(int erroCode) {
                mTitleTv.setText(erroCode + "");
                materialDialog.dismiss();
            }

            @Override
            public void onFinaly() {
                Log.e("onFinaly", "流程结束,eg:diaglog.dissmiss");
                materialDialog.dismiss();
            }
        });
        bsh2222 = new BindSendHelper(this, new OnBindSendResultListener() {
            @Override
            public void onBegin() {
                Log.e("onBegin", "初始化操作,eg:show diag()");
                materialDialog.show();
            }

            @Override
            public void onSucceed(String succeCode) {
                Log.e("succeCode", succeCode + "");// succecode = 1 成功，其他失败
                mTitleTv.setText(succeCode + "");
                materialDialog.dismiss();
            }

            @Override
            public void onFail(int erroCode) {
                Log.e("fail", erroCode + "");
                mTitleTv.setText(erroCode + "");
                materialDialog.dismiss();
            }

            @Override
            public void onFinaly() {
                Log.e("onFinaly", "diaglog.dismiss");
                materialDialog.dismiss();
            }
        });
        osh444 = new OrderSignHelper(this, new OnSignListener() {
            @Override
            public void onBegin() {
                Log.e("onBegin", "初始化签收,eg:show diag()");
                materialDialog.show();
            }

            @Override
            public void onDateSucceed(List<Device_Data> resultList) {
                mTitleTv.setText(resultList.toString());
                materialDialog.dismiss();
            }

            @Override
            public void onFail(int erroCode) {
                Log.e("fail", erroCode + "");
                mTitleTv.setText(erroCode + "");
                materialDialog.dismiss();
            }

            @Override
            public void onFinaly(String s ) {
                Log.e("onFinaly", s+"");
                materialDialog.dismiss();
            }
        });
        sqh333 = new SignQueryHelper(this, new OnSignQueryListener() {
            @Override
            public void onBegin() {
                Log.e("onBegin", "初始化签收,eg:show diag()");
                materialDialog.show();
            }

            @Override
            public void onDateSucceed(TransportDeviceBean resultList) {
                mSignQueryResult = resultList;
                mTitleTv.setText(resultList.toString());
                materialDialog.dismiss();
            }

            @Override
            public void onFail(int erroCode) {
                Log.e("fail", erroCode + "");
                mTitleTv.setText(erroCode + "");
                materialDialog.dismiss();
            }

            @Override
            public void onFinaly() {
                Log.e("onFinaly", "diaglog.dismiss");
                materialDialog.dismiss();
            }
        });
        mBtnBindOrder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bsh2222.cnDeviceBle(mEt.getText().toString(), "201801011234", mBindQueryResult.getBindDataIndex(), "admin", new Date(), mBindQueryResult.getBindDate());//new Date() , bindDate
                //参数 1设备编号 2,运单编号 ,3 绑定查询返回的数据索引 4,登录用户名 英文字母(汉字乱码),5 运单创建时间 6,设备绑定时间
            }
        });

        mBtnSerch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bqh1111.cnDeviceBle(mEt.getText().toString());//参数为 设备编号
            }
        });
        mBtnSign44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                osh444.cnDeviceBle(mEt.getText().toString(), "201801011234", mSignQueryResult.getSignDataIndex(), "admin", new Date(), mBindQueryResult.getBindDate());//new Date() , bindDate
                //参数 1设备编号 2,运单编号 ,3 签收查询返回的数据索引 4,登录用户名 英文字母(汉字乱码),5 运单结束时间 6,运单绑定时间
            }
        });
        mBtnSign3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqh333.cnDeviceBle(mEt.getText().toString());//参数为 设备编号
            }
        });
    }


}
