package com.example.com.myselefweather.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.myselefweather.R;
import com.example.com.myselefweather.db.WeatherDB;
import com.example.com.myselefweather.model.City;
import com.example.com.myselefweather.model.County;
import com.example.com.myselefweather.model.Province;
import com.example.com.myselefweather.util.HttpCallBackListener;
import com.example.com.myselefweather.util.HttpUtil;
import com.example.com.myselefweather.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class ChooseAreaActivity extends Activity {

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    private ProgressDialog dialog;

    private TextView titleView;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private WeatherDB db;
    private List<String> dataList = new ArrayList<>();

    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private int currentLever;
    private Province selectProvince;
    private City selectCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        titleView = (TextView) findViewById(R.id.title_text);
        listView = (ListView) findViewById(R.id.list_view );
        db = WeatherDB.GetInstance(this);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (currentLever == LEVEL_PROVINCE) {
                    selectProvince = provinceList.get(position);
                    queryCity();
                } else if (currentLever == LEVEL_CITY) {
                    selectCity = cityList.get(position);
                    queryCounty();
                }else{
                    County county = countyList.get(position);
                    Intent intent = new Intent(ChooseAreaActivity.this, WeatherActivity.class);
                    intent.putExtra(WeatherActivity.COUNTY_CODE, county.getCountyCode());
                    startActivity(intent);
                }
            }
        });
        queryProvinces();
    }

    private  void queryProvinces()
    {
        provinceList = db.loadProvinces();
        if(provinceList.size() > 0)
        {
            dataList.clear();
            for (Province p: provinceList ) {
                dataList.add(p.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleView.setText("中国");
        }
        else
        {
            queryFromServer(null, "province");
        }
        currentLever = LEVEL_PROVINCE;
    }

    private  void queryCity()
    {
        cityList = db.loadCities(selectProvince.getId());
        if(cityList.size() > 0)
        {
            dataList.clear();
            for (City p: cityList ) {
                dataList.add(p.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleView.setText(selectProvince.getProvinceName());

        }
        else
        {
            queryFromServer(selectProvince.getProvinceCode(), "city");
        }
        currentLever = LEVEL_CITY;
    }
    private  void queryCounty()
    {
        countyList = db.loadCounties(selectCity.getId());
        if(countyList.size() > 0)
        {
            dataList.clear();
            for (County p: countyList ) {
                dataList.add(p.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleView.setText(selectCity.getCityName());

        }
        else
        {
            queryFromServer(selectCity.getCityCode(), "county");
        }
        currentLever = LEVEL_COUNTY;
    }

    private void queryFromServer(String code, final String type)
    {
        String address ;
        if(!TextUtils.isEmpty(code))
        {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        }
        else
        {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        HttpUtil.sendRequest(address, new HttpCallBackListener() {
            @Override
            public void onFinsh(String response) {
                boolean result = false;
                if("province".equals(type))
                {
                    result = Utility.handleProvinceResponse(db, response);
                }
                else if("city".equals(type))
                {
                    result = Utility.handleCityResponse(db, response, selectProvince.getId());
                }
                else if("county".equals(type))
                {
                    result = Utility.handleCountyResponse(db,response, selectCity.getId());
                }
                if(result)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("province".equals(type))
                            {
                                queryProvinces();
                            }
                            else if("city".equals(type))
                            {
                                queryCity();
                            }
                            else if("county".equals(type))
                            {
                                queryCounty();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this, " Load failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showProgressDialog()
    {
        if(dialog == null)
        {
            dialog = new ProgressDialog(this);
            dialog.setMessage("System is loading...");
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.show();
    }

    private void closeProgressDialog()
    {
        if(dialog != null)
        {
            dialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if(currentLever == LEVEL_CITY)
        {
            queryProvinces();
        }
        else if(currentLever == LEVEL_COUNTY)
        {
            queryCity();
        }
        else
        {
            finish();
        }
    }
}
