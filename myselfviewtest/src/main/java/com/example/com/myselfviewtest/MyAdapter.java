package com.example.com.myselfviewtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 */
public class MyAdapter extends BaseAdapter{

    private List<String> list;
    private LayoutInflater inflater;

    public MyAdapter(Context context, List<String> list)
    {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_layout, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setBackgroundResource(R.drawable.test1);
        viewHolder.textView.setText(list.get(position));
        return convertView;
    }

    public class ViewHolder
    {
        public TextView textView;
        public ImageView imageView;
    }
}
