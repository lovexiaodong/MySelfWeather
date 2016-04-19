package com.example.com.myselefweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.example.com.myselefweather.util.HttpCallBackListener;
import com.example.com.myselefweather.util.HttpUtil;
import com.example.com.myselefweather.util.Utility;

/**
 * Created by Administrator on 2016/4/19.
 */
public class AutoUpateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        }).start();

        AlarmManager manager  = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 8 * 60 * 60 * 1000;
        long systemAlarm = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(AutoUpateService.this, AutoUpateService.class);
        PendingIntent pi = PendingIntent.getBroadcast(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME, systemAlarm, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String city_code = preferences.getString("city_code", "");
        String address = "http:www.weather.com.cn/data/cityinfo/" + city_code + ".html";
        HttpUtil.sendRequest(address, new HttpCallBackListener() {
            @Override
            public void onFinsh(String response) {
                Utility.handleWeatherResponse(AutoUpateService.this, response);
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }


}
