package com.example.cocktail_db.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.cocktail_db.R;
import com.example.cocktail_db.activities.MainActivity;
import com.example.cocktail_db.activities.activity_viewCocktail;
import com.example.cocktail_db.fragments.mainList_Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class main_contentView extends AppCompatActivity {
    main_listAdapter adapter;
    ArrayList<String> cocktailInfo = new ArrayList<>();
    temp t = temp.getInstance();
    int id;

    public void inflateList(Activity context, ArrayList<String> Cocktail){

        SharedPreferences sharedPreferences = context.getSharedPreferences("cocktailData", Context.MODE_PRIVATE);
        adapter = new main_listAdapter(context,cocktailInfo);
        JSONObject jsonObject = null;
        SharedPreferences.Editor Edit = sharedPreferences.edit();

        ListView listView = (ListView) context.findViewById(R.id.main_list);
        listView.setAdapter(adapter);

        try {
            for (int i = 0; i<Cocktail.size();i++) {
                Edit.putInt("size", Cocktail.size()).commit();
                jsonObject = new JSONObject(Cocktail.get(i));
                JSONArray jsonArray = jsonObject.getJSONArray("drinks");
                for (int x = 0; x < jsonArray.length(); x++) {
                    JSONObject object = jsonArray.getJSONObject(x);
                    String str = object.getString("strDrink");
                    id = Integer.parseInt(object.getString("idDrink"));
                    Edit.putString("ID"+i,object.getString("idDrink")).commit();
                    for (String temp : cocktailInfo) {
                        if (temp == str) {
                            object.remove("strDrink");
                        }
                    }
                    cocktailInfo.add(str);
                    adapter.notifyDataSetChanged();
                    t.setArray(cocktailInfo);

                }
            }
            } catch (JSONException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int ID = 0;
                Intent intent = new Intent(context, activity_viewCocktail.class);
                int size = sharedPreferences.getInt("size",0);
                for (int j = 0; j <size ; j++) {
                    if (j==i){
                       ID = Integer.parseInt(sharedPreferences.getString("ID"+j,null));
                    }
                }
                intent.putExtra("ID",ID);
                context.startActivity(intent);
            }
        });
    }


}
