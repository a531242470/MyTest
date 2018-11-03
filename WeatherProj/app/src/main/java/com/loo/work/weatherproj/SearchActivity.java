package com.loo.work.weatherproj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.j256.ormlite.dao.Dao;
import com.loo.work.weatherproj.bean.CityName;
import com.loo.work.weatherproj.bean.Data;
import com.loo.work.weatherproj.bean.Forecast;
import com.loo.work.weatherproj.bean.MyJson;
import com.loo.work.weatherproj.config.Config;
import com.loo.work.weatherproj.db.DbHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    private EditText edit_city;
    private Button btn_search;
    private String myJson;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initEven();
    }

    private void initEven() {
        btn_search.setOnClickListener(new View.OnClickListener() {

            private String city_code;

            @Override
            public void onClick(View view) {
                final String city = edit_city.getText().toString();
                if (city.isEmpty()) {
                    Toast.makeText(SearchActivity.this, "城市不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Request.Builder cityidBuilder = new Request.Builder();
                cityidBuilder.url(Config.cityUrl);
                Request cityRequest = cityidBuilder.build();
                Call cityCall = client.newCall(cityRequest);
                cityCall.enqueue(new Callback() {


                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SearchActivity.this, "无网络连接", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String cityJson = response.body().string();

                        Gson gson = new Gson();
                        JsonParser parser = new JsonParser();
                        JsonArray jsonArray = parser.parse(cityJson).getAsJsonArray();
                        ArrayList<CityName> cityNameArrayList = new ArrayList<>();
                        for (JsonElement city : jsonArray) {
                            CityName cityName = gson.fromJson(city, CityName.class);
                            cityNameArrayList.add(cityName);
                        }
                        for (int i = 0; i < cityNameArrayList.size(); i++) {
                            if (cityNameArrayList.get(i).getCity_name().equals(city)) {
                                city_code = cityNameArrayList.get(i).getCity_code();
                                break;
                            } else {
                                city_code = "9999999999999";
                            }
                        }


                        Request.Builder builder = new Request.Builder();
                        builder.url(Config.BaseUrl + city_code);
                        Request request = builder.build();
                        Call ncall = client.newCall(request);
                        ncall.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SearchActivity.this, "无法连接网络", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                myJson = response.body().string();
                                Gson gson = new Gson();
                                MyJson myJson1 = gson.fromJson(SearchActivity.this.myJson, MyJson.class);
                                if (myJson1.getMessage().equals("Success !")) {
                                    Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                                    intent.putExtra("myJson", SearchActivity.this.myJson);
                                    intent.putExtra("cityName", edit_city.getText().toString());
                                    startActivity(intent);
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(SearchActivity.this, "城市名称错误", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }
                        });


                    }
                });


            }
        });
    }

    private void initView() {
        edit_city = findViewById(R.id.edit_city);
        btn_search = findViewById(R.id.btn_search);

    }


}
