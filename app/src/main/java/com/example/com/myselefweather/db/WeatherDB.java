package com.example.com.myselefweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.com.myselefweather.model.City;
import com.example.com.myselefweather.model.County;
import com.example.com.myselefweather.model.Province;

import java.net.Authenticator;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Administrator on 2016/4/12.
 */
public class WeatherDB {

    public static final String DB_NAME = "weather";
    public static final int VERSION = 1;
    private static WeatherDB weatherDB;
    private SQLiteDatabase db;

    private WeatherDB(Context context) {
        WeatherHelp weatherHelp = new WeatherHelp(context, DB_NAME, null, VERSION);
        db = weatherHelp.getWritableDatabase();
    }

    public synchronized static WeatherDB GetInstance(Context context) {
        if (weatherDB == null) {
            weatherDB = new WeatherDB(context);
        }
        return weatherDB;
    }

    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues value = new ContentValues();
            value.put("province_name", province.getProvinceName());
            value.put("provinice_code", province.getProvinceCode());
            db.insert("Province", null, value);
        }
    }

    public List<Province> loadProvinces() {
        List<Province> list = new ArrayList<>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Province province = new Province();
            province.setId(cursor.getInt(cursor.getColumnIndex("id")));
            province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
            province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
            list.add(province);
        }
        return list;
    }

    public void saveCity(City city) {
        if (city != null) {
            ContentValues value = new ContentValues();
            value.put("id", city.getId());
            value.put("city_name", city.getCityName());
            value.put("city_code", city.getCityCode());
            value.put("province_id", city.getProvinceId());
            db.insert("City", null, value);
        }
    }

    public List<City> loadCities(int provinceId) {
        List<City> list = new ArrayList<>();

        Cursor cursor = db.query("City", null, "province_id = ?", new String[]{String.valueOf(provinceId)}, null, null, null);
        while (cursor.moveToNext()) {
            City city = new City();
            city.setId(cursor.getInt(cursor.getColumnIndex("id")));
            city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
            city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
            city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
            list.add(city);
        }
        return list;
    }

    public void saveCounty(County county)
    {
        if(county != null)
        {
            ContentValues value = new ContentValues();
            value.put("id", county.getId());
            value.put("county_name", county.getCountyName());
            value.put("county_code", county.getCountyCode());
            value.put("city_id", county.getCityId());
            db.insert("County", null, value);
        }
    }

    public List<County> loadCounties(int cityId)
    {
        List<County> list = new ArrayList<>();
        Cursor cursor = db.query("County", null, "city_id = ? ", new String[]{String.valueOf(cityId)}, null, null, null);
        while(cursor.moveToNext())
        {
            County county = new County();
            county.setId(cursor.getInt(cursor.getColumnIndex("id")));
            county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
            county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
            county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
            list.add(county);
        }
        return  list;
    }
}
