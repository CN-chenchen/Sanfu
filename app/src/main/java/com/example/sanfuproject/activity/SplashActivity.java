package com.example.sanfuproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.example.sanfuproject.R;
import com.example.sanfuproject.activity.entity.Classify;
import com.example.sanfuproject.activity.entity.Home;
import com.example.sanfuproject.activity.utils.JsonUtils;

import static com.example.sanfuproject.activity.utils.Constants.category;
import static com.example.sanfuproject.activity.utils.Constants.classifyStr;
import static com.example.sanfuproject.activity.utils.Constants.datas;
import static com.example.sanfuproject.activity.utils.Constants.homeStr;

/**
 * 闪屏页
 *
 * @param
 */
public class SplashActivity extends AppCompatActivity {

    Intent it = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        initData();
    }

    private void initData() {
        if (JsonUtils.isNetworkConnected(getBaseContext()) == true) {
            new Thread() {
                @Override
                public void run() {
                    String homejson = JsonUtils.loadJson(homeStr);
                    Home h = JSON.parseObject(homejson, Home.class);
                    datas = h.getMsg().getIndex();

                    String classifyJson = JsonUtils.loadJson(classifyStr);
                    Classify classify = JsonUtils.parseJson(classifyJson);
                    category = classify.getMsg().getCategory();
                }
            }.start();
        }
    }

    private void init() {
        SharedPreferences sp = getSharedPreferences("args", Context.MODE_PRIVATE);
        boolean isGuid = sp.getBoolean("isGuid", false);
        //已经引导过
        if (isGuid) {
            it = new Intent(this, MainActivity.class);
        } else {
            it = new Intent(this, GuideActivity.class);
        }
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(3000);
                    startActivity(it);
                } catch (Exception e) {
                }
            }
        }.start();
    }
}














