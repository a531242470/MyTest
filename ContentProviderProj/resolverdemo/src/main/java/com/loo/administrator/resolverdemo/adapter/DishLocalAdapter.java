package com.loo.administrator.resolverdemo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.loo.administrator.resolverdemo.R;

public class DishLocalAdapter extends MyBaseAdapter {
    public DishLocalAdapter(Context context) {
        super(context);
    }

    @Override
    public View MyGroupView(int i, View view) {
        view=View.inflate(context, R.layout.items_group,null);
        TextView textView=view.findViewById(R.id.text_view_group);
        textView.setText(getGroup(i));
        return view;
    }

    @Override
    public View MyChildView(int i, int i1, View view) {
        view=View.inflate(context,R.layout.items_child,null);
        TextView textView=view.findViewById(R.id.text_view_child);
        textView.setText(getChild(i,i1));

        return view;
    }
}
