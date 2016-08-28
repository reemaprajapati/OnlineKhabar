package com.example.otimus.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.otimus.myapplication.DataModels.Entity1;
import com.example.otimus.myapplication.R;

import java.util.ArrayList;

/**
 * Created by Otimus on 8/8/2016.
 */
public  class CustomArrayAdapter1 extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<Entity1> centityArrayList;
    Context context;

    public CustomArrayAdapter1(Context contextList, ArrayList<Entity1> list){
        this.context=contextList;
        this.centityArrayList=list;
        layoutInflater=layoutInflater.from((contextList));


    }



    @Override
    public int getCount() {
        return centityArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return centityArrayList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        //        if(convertView==null){

        convertView = layoutInflater.inflate(R.layout.list_model, null);
        viewHolder = new ViewHolder();
        viewHolder.username = (TextView) convertView.findViewById(R.id.cusername);
        viewHolder.comment = (TextView) convertView.findViewById(R.id.ccomment);
        convertView.setTag(viewHolder);

        viewHolder.username.setText(centityArrayList.get(position).getUsername());
        viewHolder.comment.setText(centityArrayList.get(position).getComment());

        return convertView;
    }


    public static class ViewHolder{
        TextView username;

        TextView comment;
    }
}


