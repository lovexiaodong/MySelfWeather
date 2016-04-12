package com.example.com.myselefweather.util;

import android.text.TextUtils;

import com.example.com.myselefweather.db.WeatherDB;
import com.example.com.myselefweather.model.City;
import com.example.com.myselefweather.model.County;
import com.example.com.myselefweather.model.Province;

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
}
