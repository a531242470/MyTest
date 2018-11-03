package com.loo.work.weatherproj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loo.work.weatherproj.R;
import com.loo.work.weatherproj.bean.Forecast;

import java.util.List;

public class WeaAdapter extends BaseAdapter {
    Context context;
    List<Forecast> forecasts;
    LayoutInflater layoutInflater;

    public WeaAdapter(Context context, List<Forecast> forecasts) {
        this.context = context;
        this.forecasts = forecasts;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return forecasts.size();
    }

    @Override
    public Object getItem(int i) {
        return forecasts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=new ViewHolder();
        if (view==null){
            view=layoutInflater.inflate(R.layout.items_list,null);
            viewHolder.icon=view.findViewById(R.id.image_icon);
            viewHolder.date=view.findViewById(R.id.tv_date);
            viewHolder.max=view.findViewById(R.id.tv_max);
            viewHolder.min=view.findViewById(R.id.tv_min);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        switch (forecasts.get(i).getType()){
            case "晴":
                viewHolder.icon.setImageResource(R.drawable.sunny);
                break;
            case "雷阵雨":
                viewHolder.icon.setImageResource(R.drawable.rain);
                break;
            case "阵雨":
                viewHolder.icon.setImageResource(R.drawable.rain);
                break;
            case "多云":
                viewHolder.icon.setImageResource(R.drawable.cloudy);
                break;
                default:
                    viewHolder.icon.setImageResource(R.drawable.sunny);
                break;
        }
        viewHolder.date.setText(forecasts.get(i).getDate());
        viewHolder.max.setText(forecasts.get(i).getHigh());
        viewHolder.min.setText(forecasts.get(i).getLow());
        return view;
    }
    public class ViewHolder{
        private ImageView icon;
        private TextView date;
        private TextView max;
        private TextView min;
    }
}
