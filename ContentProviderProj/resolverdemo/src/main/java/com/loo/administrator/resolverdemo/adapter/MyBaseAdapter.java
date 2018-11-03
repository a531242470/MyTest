package com.loo.administrator.resolverdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

public abstract class MyBaseAdapter  extends BaseExpandableListAdapter{
    Context context;
    LayoutInflater layoutInflater;
    private List<String> groups;
    private    List<List<String>> childs;
    public MyBaseAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void addNewData(List<String> groups,List<List<String>> childs){
        this.groups=groups;
        this.childs=childs;
    }
    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childs.get(i).size();
    }

    @Override
    public String getGroup(int i) {
        return groups.get(i);
    }

    @Override
    public String getChild(int i, int i1) {
        return childs.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        return MyGroupView(i,view);
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return MyChildView(i,i1,view);
    }

    public abstract  View MyGroupView(int i,View view);
    public abstract View MyChildView(int i,int i1,View view);

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
