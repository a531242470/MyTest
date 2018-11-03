package com.loo.work.weatherproj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.loo.work.weatherproj.bean.Data;
import com.loo.work.weatherproj.bean.Forecast;

import java.sql.SQLException;

public class DbHelper extends OrmLiteSqliteOpenHelper {
    private DbHelper(Context context) {
        super(context, "weather.db", null, 1);
    }
    private static DbHelper dbHelper=null;

    public static synchronized DbHelper getDbHelper(Context context){
        if (dbHelper==null){
            dbHelper=new DbHelper(context);
        }
        return dbHelper;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Forecast.class);
            TableUtils.createTable(connectionSource, Data.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }
}
