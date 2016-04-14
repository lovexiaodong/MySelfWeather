package com.example.com.myselefweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.com.myselefweather.R;
import com.example.com.myselefweather.util.HttpCallBackListener;
import com.example.com.myselefweather.util.HttpUtil;
import com.example.com.myselefweather.util.Utility;

import org.w3c.dom.Text;

import static com.example.com.myselefweather.R.id.temp2;

/**
 * Copyright (c) 2016 Omnitracs, LLC. All rights reserved.
 * Confidential and Proprietary – Omnitracs, LLC
 * This software may be subject to U.S. and international export, re-export, or transfer
 * (“export”) laws.
 * Diversion contrary to U.S. and international laws is strictly prohibited.
 * <p/>
 * Created by William.Zhang on 2016/4/14.
 */
public class WeatherActivity extends Activity {

    public static final String COUNTY_CODE = "county_code";
    private LinearLayout weatherInfoLayout;
    private TextView cityName;
    private TextView publishText;
    private TextView weatherDespText;
    private TextView temp1Text;
    private TextView temp2Text;
    private TextView currentDateText;
    private Button switchCity;
    private Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weaher_layout);
        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info);
        cityName = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        currentDateText = (TextView) findViewById(R.id.current_data);
        String countyCode = getIntent().getStringExtra(COUNTY_CODE);
        if(!TextUtils.isEmpty(countyCode))
        {
            //// TODO: 2016/4/14  
            weatherInfoLayout.setVisibility(View.VISIBLE);
            cityName.setVisibility(View.VISIBLE);
        }
    }
    
    private void queryWeatherCode(String code)
    {
        String address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        queryFromServer(address, "countyCode");
    }

    private void queryFromServer(String address, final String type) {
        HttpUtil.sendRequest(address, new HttpCallBackListener() {
            @Override
            public void onFinsh(String response) {

                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2) {
                            String weatherCode = array[0];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if ("weatherCode".equals(type)) {
                    Utility.handleWeatherResponse(WeatherActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeaher();
                        }
                    });
                }

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void showWeaher() {

    }

    private void queryWeatherInfo(String weatherCode) {

        String address = "http://www.weather.com.cn/data/cityinfo" + weatherCode + ".xml";
        queryFromServer(address, "weatherCode");
    }

}
