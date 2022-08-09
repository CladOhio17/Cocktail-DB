package com.example.cocktail_db.library;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentManager;
import com.example.cocktail_db.R;
import com.example.cocktail_db.activities.activity_viewCocktail;
import com.example.cocktail_db.fragments.loadingFragment;
import com.example.cocktail_db.fragments.viewInfoFragment;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An AsyncTask to grab a json object from a url with given id
 */
public class infoGrabber extends AsyncTask<Void, Void, Void> {
    activity_viewCocktail context;
    int id;
    Bitmap bitmap;
    JSONObject jsonObject;
    JSONObject object;

    public infoGrabber(activity_viewCocktail context,int id){
        this.context = context;
        this.id = id;


    }

    /**
     * inflates a loading fragment
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        FragmentManager fm =  context.getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_page_container, loadingFragment.class,null).commit();
    }

    /**
     * gets json object from url where i is equal to id
     * @param voids
     * @return
     */
    @Override
    protected Void doInBackground(Void... voids) {

        HttpURLConnection urlConnection = null;
        HttpURLConnection urlConnection2 = null;
        String result;
        try{
        URL url = new URL("https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i="+id);
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
        jsonObject = new JSONObject(result);
        JSONArray jsonArray = jsonObject.getJSONArray("drinks");
        for (int x = 0; x < jsonArray.length(); x++) {
            object = jsonArray.getJSONObject(x);
            URL imgURL = new URL(object.getString("strDrinkThumb"));

            urlConnection2 = (HttpURLConnection) imgURL.openConnection();
            bitmap = BitmapFactory.decodeStream(urlConnection2.getInputStream());
            urlConnection2.disconnect();
        }
        reader.close();
        response.close();
    } catch (Exception e){
            Log.v("error",""+e);

    }finally {
        urlConnection.disconnect();

    }
        FragmentManager fm =  context.getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_page_container, viewInfoFragment.class,null).commit();
        return null;
    }

    /**
     * Sets the text on the fragment
     * @param unused
     */
    @Override
    protected void onPostExecute(Void unused) {
        int i = 1;

        String instruc = "";
        String infoString = "";

        ImageView img = context.findViewById(R.id.coktail_img);
        TextView title = context.findViewById(R.id.view_title);
        TextView IngredientsTitle = context.findViewById(R.id.ingredients_title);
        TextView infoTitle = context.findViewById(R.id.info_title);
        TextView InstructionsText= context.findViewById(R.id.instruction_text);
        TextView IngredientsText = context.findViewById(R.id.ingredients_text);
        TextView infoText = context.findViewById(R.id.view_info_text);
        TextView src = context.findViewById(R.id.img_source);

        try {

            img.setImageBitmap(bitmap);
            title.setText(object.getString("strDrink"));
            IngredientsTitle.setText("Ingredients");
            InstructionsText.setText(object.getString("strInstructions"));
            infoString = "Glass: "+object.getString("strGlass")+"\n\n"+
            "Category: "+object.getString("strCategory") + "\n\n"+ "Alcoholic: "+object.getString("strAlcoholic") + "\n\n";
            infoTitle.setText("Info");
            infoText.setText(infoString);

            for (i=1;i<15;i++){

                if (object.getString("strIngredient"+i)!= "null" && object.getString("strMeasure"+i) != "null"){
                    instruc += String.format("%s %s\n\n",object.getString("strMeasure"+i), object.getString("strIngredient"+i));
                }else if (object.getString("strIngredient"+i)== "null" && object.getString("strMeasure"+i) != "null"){
                    instruc += String.format("%s\n\n", object.getString("strIngredient"+i));
                }else{
                }

            }
            IngredientsText.setText(instruc);
            src.setText(object.getString("strDrinkThumb"));

        } catch (Exception e) {
            Log.v("error",""+e);
        }
    }
}
