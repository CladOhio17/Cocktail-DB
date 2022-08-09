package com.example.cocktail_db.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cocktail_db.R;
import com.example.cocktail_db.activities.activity_viewCocktail;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Creates an array of strings from a json object and adds them to a list, saves id and list position
 * to a sharedpref so it can be recalled if needed
 */
public class main_contentView extends AppCompatActivity {
    main_listAdapter adapter;
    ArrayList<String> cocktailInfo = new ArrayList<>();
    int id;

    /**
     * @param context The activity that this method is adding to
     * @param Cocktail an array containing json objects
     * @param isMain if the context is coming from the main page (should be removed)
     */
    public void inflateList(Activity context, ArrayList<String> Cocktail, int isMain){

        SharedPreferences sharedPreferences = context.getSharedPreferences("cocktailData", Context.MODE_PRIVATE);
        adapter = new main_listAdapter(context,cocktailInfo);
        JSONObject jsonObject = null;
        SharedPreferences.Editor Edit = sharedPreferences.edit();

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
                    int row=i+x;

                    for (String temp : cocktailInfo) {
                        if (temp == str) {
                            object.remove("strDrink");
                        }
                    }
                    cocktailInfo.add(str);
                    adapter.notifyDataSetChanged();

                    if (isMain == 1){

                        Edit.putInt("MainSize", adapter.getCount()).commit();
                        Edit.putString("MainID"+row,object.getString("idDrink")).commit();
                    }else{
                        Edit.putInt("isMain", 0).commit();
                        Edit.putInt("size", adapter.getCount()).commit();
                        Edit.putString("ID"+row,object.getString("idDrink")).commit();
                    }


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
                int size;
                int isInitial = sharedPreferences.getInt("isMain",0);
                if (isInitial == 1){
                    size = sharedPreferences.getInt("MainSize",0);
                }else {
                    size = sharedPreferences.getInt("size",0);
                }
                for (int j = 0; j <size ; j++) {
                    if (j==i){
                        if (isInitial == 1){
                            ID = Integer.parseInt(sharedPreferences.getString("MainID"+j,null));

                        }else {
                            ID = Integer.parseInt(sharedPreferences.getString("ID"+j,null));

                        }

                    }
                }
                intent.putExtra("ID",ID);
                context.startActivity(intent);
            }
        });
    }


}
