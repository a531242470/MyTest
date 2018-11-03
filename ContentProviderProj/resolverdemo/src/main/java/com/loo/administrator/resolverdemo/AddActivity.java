package com.loo.administrator.resolverdemo;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loo.administrator.resolverdemo.model.DishData;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends Activity {
      private Spinner spinner;
      private EditText edit_Text;
      private TextView text_back;
      private Button btn_save;
    private String type;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        final ContentResolver resolver=getContentResolver();
        Cursor cursor = resolver.query(Uri.parse("content://com.imooc.menuprovider"),null,null,null,null);
        List<String> dishGroup = new ArrayList<String>();
        spinner=findViewById(R.id.spinner);
        edit_Text=findViewById(R.id.edit_text);
        text_back=findViewById(R.id.text_back);
        btn_save=findViewById(R.id.btn_save);
        while (cursor.moveToNext()){
            String id= cursor.getString(cursor.getColumnIndex("dish_id"));
            String name= cursor.getString(cursor.getColumnIndex("dish_name"));
            String type= cursor.getString(cursor.getColumnIndex("dish_type"));
            if (!dishGroup.contains(type)){
                dishGroup.add(type);
            }
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dishGroup);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Log.e("msg","dd");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type=spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edit_Text.getText().toString();
                ContentValues values=new ContentValues();
                values.put("dish_name",name);
                values.put("dish_type",type);
                Uri uri=resolver.insert(Uri.parse("content://com.imooc.menuprovider"),values);
                long result= ContentUris.parseId(uri);
                Toast.makeText(AddActivity.this,"ID为:"+result+"的物品添加成功",Toast.LENGTH_SHORT).show();
            }

        });

     text_back.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             finish();
         }
     });


    }

}
