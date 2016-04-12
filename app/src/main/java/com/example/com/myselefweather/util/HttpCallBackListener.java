package com.example.com.myselefweather.util;

/**
 * Created by Administrator on 2016/4/12.
 */
public interface HttpCallBackListener {
    void onFinsh(String response);
    void onError(Exception e);
}
