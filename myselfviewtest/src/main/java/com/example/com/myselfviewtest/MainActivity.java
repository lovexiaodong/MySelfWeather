package com.example.com.myselfviewtest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> list = new ArrayList<>();
    private MyAdapter adapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        listView = (ListView) findViewById(R.id.listView);
        for(int i = 1; i < 30; i++)
        {
            list.add("xiaodong" + i);
        }
        adapter = new MyAdapter(this,list);
        listView.setAdapter(adapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i("MainActivity", "onScroll: firstVisibleItem = " + firstVisibleItem);
                Log.i("MainActivity", "onScroll: visibleItemCount = " + visibleItemCount);
                Log.i("MainActivity", "onScroll: totalItemCount = " + totalItemCount);
            }
        });

    }

}
