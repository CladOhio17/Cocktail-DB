package com.example.cocktail_db.library;

import android.widget.ListView;

import java.util.ArrayList;

public class temp {
    ArrayList<String> list;
    private static temp instance;
    ListView view;
    public temp(){

    }
    public void setArray(ArrayList<String> list){
        this.list = list;
    }
    public static temp getInstance(){
        instance = new temp();
        return instance;
    }
    public ArrayList getArray(){
        return list;
    }
    public void clearArray(){
        for (int i = 0; i < list.size() ; i++) {
            list.remove(i);
        }
    }
    public void setView(ListView view){
        this.view = view;
    }
    public ListView getView(){
        return view;
    }
}
