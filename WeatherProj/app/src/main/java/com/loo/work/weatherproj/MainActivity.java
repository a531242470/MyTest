package com.loo.work.weatherproj;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.loo.work.weatherproj.adapter.WeaAdapter;
import com.loo.work.weatherproj.bean.Data;
import com.loo.work.weatherproj.bean.Forecast;
import com.loo.work.weatherproj.bean.MyJson;
import com.loo.work.weatherproj.db.DbHelper;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private TextView tv_city,tv_notice,tv_wendu;
    private String myJson;
    private ListView listView;
    private ConstraintLayout lin_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        try {
            setView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        Intent intent=getIntent();
        lin_main=findViewById(R.id.lin_main);
        myJson = intent.getStringExtra("myJson");
        listView=findViewById(R.id.listview);
tv_city=findViewById(R.id.tv_city);
tv_notice=findViewById(R.id.tv_notice);
tv_wendu=findViewById(R.id.tv_wendu);
tv_city.setText(intent.getStringExtra("cityName"));



    }






    public void setView() throws SQLException {
        if (getTodayDao().queryForAll().size()>0){
            List<Data> data = getTodayDao().queryForAll();
            Data today=data.get(0);
            tv_wendu.setText(today.getWendu()+"℃");
            tv_notice.setText(today.getGanmao());
        }
        if (getDao().queryForAll().size()>0){
            List<Forecast> forecasts=getDao().queryForAll();
            switch (forecasts.get(0).getType()){
                case "晴":
                 lin_main.setBackgroundResource(R.drawable.weather);
                    break;
                case "雷阵雨":
                    lin_main.setBackgroundResource(R.drawable.rain_background);
                    break;
                case "阵雨":
                    lin_main.setBackgroundResource(R.drawable.rain_background);
                    break;
                case "多云":
                    lin_main.setBackgroundResource(R.drawable.weather);
                    break;
                default:
                    lin_main.setBackgroundResource(R.drawable.weather);
                    break;
            }
            listView.setAdapter(new WeaAdapter(this,forecasts));
        }

        if (!myJson.isEmpty()) {
            getTodayDao().queryRaw("delete from tb_today");
            getTodayDao().queryRaw("update sqlite_sequence SET seq = 0 where name ='tb_today'");
            getDao().queryRaw("delete from tb_forecast");
            getDao().queryRaw("update sqlite_sequence SET seq = 0 where name ='tb_forecast'");

            Gson gson = new Gson();
            MyJson myJson = gson.fromJson(this.myJson, MyJson.class);
            Data data = myJson.getData();
            List<Forecast> forecasts = data.getForecast();
            Log.e("msg", forecasts.size() + "");


            tv_wendu.setText(data.getWendu()+"℃");
            tv_notice.setText(data.getGanmao());

            listView.setAdapter(new WeaAdapter(this,forecasts));
            getTodayDao().create(data);
            for (int i = 0; i < forecasts.size(); i++) {
                getDao().create(forecasts.get(i));
            }

            switch (forecasts.get(0).getType()){
                case "晴":
                    lin_main.setBackgroundResource(R.drawable.weather);
                    break;
                case "雷阵雨":
                    lin_main.setBackgroundResource(R.drawable.rain_background);
                    break;
                case "阵雨":
                    lin_main.setBackgroundResource(R.drawable.rain_background);
                    break;
                case "多云":
                    lin_main.setBackgroundResource(R.drawable.weather);
                    break;
                default:
                    lin_main.setBackgroundResource(R.drawable.weather);
                    break;
            }


        }

    }
    public DbHelper getDbHelper(){
        return DbHelper.getDbHelper(this);
    }
    public Dao<Forecast,Integer> getDao() throws SQLException {
        return getDbHelper().getDao(Forecast.class);
    }
    public Dao<Data,Integer> getTodayDao() throws SQLException {
        return getDbHelper().getDao(Data.class);
    }
}
