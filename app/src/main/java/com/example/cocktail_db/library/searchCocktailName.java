package com.example.cocktail_db.library;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cocktail_db.R;
import com.example.cocktail_db.fragments.loadingFragment;
import com.example.cocktail_db.fragments.mainList_Fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class searchCocktailName extends AsyncTask<Void, Void, Void> {

    Fragment context;
    ArrayList<String> cocktailInfo = new ArrayList<>();
    main_listAdapter adapter;
    String result;
    String name;
    loadingFragment fragment = new loadingFragment();
    main_contentView contentView =  new main_contentView();
    public searchCocktailName(Fragment context, String name){
        this.context = context;
        this.name = name;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        FragmentManager fm =  context.getActivity().getSupportFragmentManager();
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

        try {
            URL url = new URL("https://www.thecocktaildb.com/api/json/v1/1/search.php?s="+name);
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

            cocktailInfo.add(result);
            reader.close();
            response.close();
        } catch (Exception e){

        }finally {
            urlConnection.disconnect();
        }

        FragmentManager fm = context.getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_page_container, mainList_Fragment.class,null).commit();
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);

        contentView.inflateList(context.getActivity(),cocktailInfo);

        return;

    }
}
