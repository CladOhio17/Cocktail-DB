package com.example.cocktail_db.library;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cocktail_db.R;
import com.example.cocktail_db.activities.MainActivity;
import com.example.cocktail_db.fragments.loadingFragment;
import com.example.cocktail_db.fragments.mainList_Fragment;
import com.example.cocktail_db.fragments.searchFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class randomCocktail extends AsyncTask<Void, Void, Void> {

    MainActivity context;
    ArrayList<String> cocktailInfo = new ArrayList<>();
    main_listAdapter adapter;
    String result;
    loadingFragment fragment = new loadingFragment();

    public randomCocktail(MainActivity context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        FragmentManager fm =  context.getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_page_container, loadingFragment.class,null).commit();


    }
    /**
     * @param voids
     * @deprecated
     * @return
     */
    @Override
    protected Void doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        for (int x=0;x<15;x++){
            try {
                URL url = new URL("https://www.thecocktaildb.com/api/json/v1/1/random.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response =urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8 ) ;
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n" ) ;
                }
                result = sb.toString();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("drinks");
                for(int i = 0; i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    String str = object.getString("strDrink");

                    for (String temp: cocktailInfo){
                        if (temp == str){
                            object.remove("strDrink");
                        }
                    }
                    cocktailInfo.add(str);
                    Log.v("Cocktail:",str);

                }
                reader.close();
                response.close();
            } catch (Exception e){

            }finally {
                urlConnection.disconnect();
            }
        }

        FragmentManager fm = context.getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_page_container, mainList_Fragment.class,null).commit();
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);

        String str;
        adapter = new main_listAdapter(context,cocktailInfo);
        ListView listView = (ListView) context.findViewById(R.id.main_list);
        listView.setAdapter(adapter);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("drinks");
            for(int i = 0; i<jsonArray.length();i++){
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return;

    }
}
