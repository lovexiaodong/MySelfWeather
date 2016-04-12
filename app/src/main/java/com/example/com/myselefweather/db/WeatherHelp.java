package com.example.com.myselefweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/4/12.
 */
public class WeatherHelp extends SQLiteOpenHelper {

    private static final String CREATE_PROVINCE = "create table Provices ("
            + "id integer primary key autoincrement, "
            + "province_name text, "
            + "province_code text)";
    private static final String CREATE_CITY = "create table City ("
            + "id integer primary key autoincrement, "
            + "city_name text, "
            + "city_code text, "
            + "province_id integer)";
    private static final String    CREATE_COUNTTY = "create table County ("
            + "id integer primary key autoincrement, "
            + "county_name text, "
            + "county_code text, "
            + "city_id integer)";

    public WeatherHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
