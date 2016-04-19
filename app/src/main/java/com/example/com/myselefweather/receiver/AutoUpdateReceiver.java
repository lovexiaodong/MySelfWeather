package com.example.com.myselefweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.com.myselefweather.service.AutoUpateService;

/**
 * Created by Administrator on 2016/4/19.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AutoUpateService.class);
        context.startService(i);
    }
}
