package com.example.com.myselefweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.com.myselefweather.db.WeatherDB;
import com.example.com.myselefweather.model.City;
import com.example.com.myselefweather.model.County;
import com.example.com.myselefweather.model.Province;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/4/12.
 */
public class Utility {

    public synchronized  static boolean handleProvinceResponse(WeatherDB db, String response)
    {
        if(!TextUtils.isEmpty(response))
        {
            String[] allProvince = response.split(",");
            if(allProvince != null && allProvince.length > 0)
            {
                for (String p : allProvince)
                {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    db.saveProvince(province);
                }
                return  true;
            }
        }
        return  false;
    }

    public synchronized static  boolean handleCityResponse(WeatherDB db, String response, int provinceId)
    {
        if(!TextUtils.isEmpty(response))
        {
            String[] allCity = response.split(",");
           if(allCity != null && allCity.length > 0)
           {
               for ( String str : allCity ) {
                   String[] array = str.split("\\|");
                   City city =  new City();
                   city.setCityCode(array[0]);
                   city.setCityName(array[1]);
                   city.setProvinceId(provinceId);
                   db.saveCity(city);
               }
               return  true;
           }
        }
        return  false;
    }

    public synchronized static boolean handleCountyResponse(WeatherDB db, String response, int cityId)
    {
        if(!TextUtils.isEmpty(response))
        {
            String[] allCounty = response.split(",");
            if(allCounty != null && allCounty.length > 0)
            {
                for(String str : allCounty)
                {
                    String[] array = str.split("\\|");
                    County county =  new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    db.saveCounty(county);
                }
                return  true;
            }
        }
        return false;
    }

    public static void saveWeatherInfo(Context context, String cityName, String cityCode, String temp1,
                                       String temp2, String weatherDesp, String publishTime)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("city_code", cityCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weatherDesp", weatherDesp);
        editor.putString("publish", publishTime);
        editor.putString("current_data",format.format(new Date()));
        editor.commit();
    }

    public static void handleWeatherResponse(Context context, String response)
    {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String cityCode = weatherInfo.getString("cityid");
            String tem1 = weatherInfo.getString("temp1");
            String tem2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context,cityName, cityCode, tem1, tem2, weatherDesp, publishTime);

        }catch (Exception e)
        {}
    }
}
