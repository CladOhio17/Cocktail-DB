package com.example.cocktail_db.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cocktail_db.R;

import java.util.ArrayList;

public class main_listAdapter extends BaseAdapter{

    private ArrayList<String> data;
    Context context;

    public main_listAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;

    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View newView;
        LayoutInflater inflater =  LayoutInflater.from(context);

        newView = inflater.inflate(R.layout.main_list_item,viewGroup,false);
        TextView message = (TextView) newView.findViewById(R.id.main_cocktail_title);
        message.setText(getItem(i).toString());
        return newView;
    }
}
