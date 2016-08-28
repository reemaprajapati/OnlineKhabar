package com.example.otimus.myapplication.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.otimus.myapplication.DataModels.Entity;
import com.example.otimus.myapplication.DataModels.Entity1;
import com.example.otimus.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Otimus on 8/4/2016.
 */
public class CustomArrayAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<Entity> entityArrayList;
    Context context;

    public CustomArrayAdapter(Context contextList, ArrayList<Entity> list) {
        this.context = contextList;
        this.entityArrayList = list;
        layoutInflater = layoutInflater.from((contextList));


    }

//    public void addListToAdapter(ArrayList<Entity> alist){
//        //add list to current array list of data
//        entityArrayList.addAll(alist);
//        this.notifyDataSetChanged();
//    }

    @Override
    public int getCount() {
        return entityArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return entityArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
//        if(convertView==null){

        convertView = layoutInflater.inflate(R.layout.model, null);
        viewHolder = new ViewHolder();
        viewHolder.newstitle = (TextView) convertView.findViewById(R.id.news_title);

        viewHolder.images = (ImageView) convertView.findViewById(R.id.imageView);
        viewHolder.date = (TextView) convertView.findViewById(R.id.date);
//            convertView.setTag(viewHolder);
//
//        }
//
//        else {
//            viewHolder  = (ViewHolder) convertView.getTag();
//
//        }

        viewHolder.newstitle.setText(entityArrayList.get(position).getNewstitle());

        //  viewHolder.images.setImageResource(R.drawable.one);


        Picasso.with(context).load("http://192.168.1.2/khabar/images/" + entityArrayList.get(position).getImages()).into(viewHolder.images);

        Log.d("image", entityArrayList.get(position).getImages());
//     Log.d("date", entityArrayList.get(position).getDate());
        viewHolder.date.setText(entityArrayList.get(position).getDate());
        //   Log.d("date", entityArrayList.get(position).getDate());

//        viewHolder.images.setImageResource(entityArrayList.get(position).getImages());

        return convertView;
    }

    public static class ViewHolder {
        TextView newstitle;
        ImageView images;
        TextView date;


    }
}


