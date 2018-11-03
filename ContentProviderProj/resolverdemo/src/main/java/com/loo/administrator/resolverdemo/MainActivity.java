package com.loo.administrator.resolverdemo;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loo.administrator.resolverdemo.adapter.DishLocalAdapter;
import com.loo.administrator.resolverdemo.model.DishData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private ExpandableListView exlist_view;
    private List<DishData> dishDataList;
    private List<List<String>> dishChild;
    private ContentResolver resolver;
    private Cursor cursor;
    private DishData dishData;
    private List<String> dishGroup;
    private List<String> childItem;
    private DishLocalAdapter dishLocalAdapter;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver = getContentResolver();
        initView();
          Log.e("msg","jl");
        exlist_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            private String deleteName;
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final long packedPosition = exlist_view.getExpandableListPosition(position);
                final int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
                final int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);
                if (childPosition != -1) {
                    AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                    deleteName = dishChild.get(groupPosition).get(childPosition);
                    builder.setTitle("警告");
                    builder.setMessage("确定删除["+deleteName+"]吗");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String deleteId="";
                            for (int i = 0; i< dishDataList.size(); i++){
                                if (dishDataList.get(i).getDish_name()== deleteName){
                                    deleteId= dishDataList.get(i).getDish_id();
                                }
                            }
                       int result = resolver.delete(Uri.parse("content://com.imooc.menuprovider"),null,new String[]{deleteId});
                        if (result>0) {
                            Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            initView();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                        }
                    });
                    builder.setNegativeButton("取消",null);
                    builder.show();
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    public void initView() {
        cursor = resolver.query(Uri.parse("content://com.imooc.menuprovider"),null,null,null,null);
        textView=findViewById(R.id.text_new);
        exlist_view=findViewById(R.id.exlist_view);
        dishDataList = new ArrayList<>();
        dishGroup = new ArrayList<String>();
        while (cursor.moveToNext()){
            String id= cursor.getString(cursor.getColumnIndex("dish_id"));
            String name= cursor.getString(cursor.getColumnIndex("dish_name"));
            String type= cursor.getString(cursor.getColumnIndex("dish_type"));
            if (!dishGroup.contains(type)){
                dishGroup.add(type);
            }

            dishData =new DishData(id,name,type);
            dishDataList.add(dishData);

        }
        dishChild = new ArrayList<>();
        for (int n = 0; n< dishGroup.size(); n++) {
            childItem =new ArrayList<>();
        for (int i = 0; i< dishDataList.size(); i++){
                if (dishDataList.get(i).getDish_type().equals(dishGroup.get(n))){
                  //  childItem.add(dishDataList.get(i).getDish_id());
                    childItem.add(dishDataList.get(i).getDish_name());
                }

            }
            dishChild.add(childItem);
        }

        dishLocalAdapter = new DishLocalAdapter(this);
        dishLocalAdapter.addNewData(dishGroup, dishChild);
        exlist_view.setAdapter(dishLocalAdapter);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddActivity.class));
            }
        });
    }
}
