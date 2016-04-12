package com.example.com.myselefweather.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/4/12.
 */
public class HttpUtil {
    public static void sendRequest(final String address, final HttpCallBackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(address);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(8000);
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(8000);
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null)
                    {
                        builder.append(line);
                    }
                    if(listener != null)
                    {
                        listener.onFinsh(builder.toString());
                    }
                } catch (Exception e) {
                   if(listener != null)
                   {
                       listener.onError(e);
                   }
                }
            }
        }).start();
    }
}
