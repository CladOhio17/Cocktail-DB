package com.example.cocktail_db.library;

import android.app.Activity;
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
    int id;

    public void inflateList(Activity context, ArrayList<String> Cocktail){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        adapter = new main_listAdapter(context,cocktailInfo);
        JSONObject jsonObject = null;

        ListView listView = (ListView) context.findViewById(R.id.main_list);
        listView.setAdapter(adapter);

        try {
            for (int i = 0; i<Cocktail.size();i++) {
                jsonObject = new JSONObject(Cocktail.get(i));
                JSONArray jsonArray = jsonObject.getJSONArray("drinks");
                for (int x = 0; x < jsonArray.length(); x++) {
                    JSONObject object = jsonArray.getJSONObject(x);
                    String str = object.getString("strDrink");
                    id = Integer.parseInt(object.getString("idDrink"));
                    Log.v("ID",""+object.getString("idDrink"));
                    for (String temp : cocktailInfo) {
                        if (temp == str) {
                            object.remove("strDrink");
                        }
                    }
                    cocktailInfo.add(str);
                    adapter.notifyDataSetChanged();

                }
            }
            } catch (JSONException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, activity_viewCocktail.class);
                intent.putExtra("ID",id);
                context.startActivity(intent);
            }
        });
    }


}
