package com.example.muneeb.bloodbank;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Muneeb on 25/11/2017.
 */

public class ListAdapter extends ArrayAdapter<String> {
    ArrayList<String> name;
    ArrayList<String> blood;
    ArrayList<String> gender;
    ArrayList<Integer> img;
    ArrayList<ModelClass> data;
    Context context;
    int resource;
    LayoutInflater layoutInflater;
/*
    public ListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<String> name , ArrayList<String> blood, ArrayList<String> gender, ArrayList<Integer> img) {
        super(context, resource);

        this.context = context;
        this.resource= resource;
        this.img = img;
        this.blood = blood;
        this.name = name;
        this.gender = gender;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }*/

    public ListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<ModelClass> data) {
        super(context, resource);

        this.context = context;
        this.resource= resource;
        this.img = img;
        this.data = data;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = layoutInflater.inflate(resource,parent,false);

        ImageView pp = (ImageView) v.findViewById(R.id.imageView);
        TextView names = (TextView) v.findViewById(R.id.textView);
        TextView phones = (TextView) v.findViewById(R.id.textView2);
        TextView genders = (TextView) v.findViewById(R.id.textView3);

        // setting values

        /*names.setText(name.get(position));
        phones.setText(blood.get(position));
        genders.setText(gender.get(position));
        pp.setImageResource(img.get(position));*/

        ModelClass modelClass = data.get(position);

        names.setText(modelClass.getName());
        phones.setText(modelClass.getBloodGroup());
        genders.setText(modelClass.getGender());
        pp.setImageResource(modelClass.getImg());

        return v;
    }
}
