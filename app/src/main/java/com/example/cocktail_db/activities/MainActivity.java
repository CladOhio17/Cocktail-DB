package com.example.cocktail_db.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cocktail_db.R;
import com.example.cocktail_db.fragments.loadingFragment;
import com.example.cocktail_db.library.main_contentView;
import com.example.cocktail_db.library.navMenuSetup;
import com.example.cocktail_db.fragments.searchFragment;
import com.example.cocktail_db.library.randomCocktail;
import com.example.cocktail_db.library.temp;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    /*TODO
       -Fix Coloring of nav menu
       -Implement a Class to make setting up Nav menu Easier
       -Back end for getting cocktails from DB
       -Display List of Cocktails
         */


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    randomCocktail retrieve;
    searchFragment fragment = new searchFragment();
    temp t = temp.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        retrieve = new randomCocktail(this);
        retrieve.execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        TextView title = findViewById(R.id.title);
        title.setText("The Cocktail DB");

        FragmentManager fm =  getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_page_container, loadingFragment.class,null).commit();


        //initializations for the drawer to work
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        ImageView imageView = (ImageView) findViewById(R.id.main_nav_menu);
        navigationView = (NavigationView) findViewById(R.id.main_nav);
        navMenuSetup setup = new navMenuSetup(navigationView,"Main Page","Nate Yach - 1.0");





        //a button on the tool bar to open the drawer
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //a click listener for the buttons within the drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.nav_close:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.nav_favourites:

                        return true;
                    case R.id.nav_home:
                        drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });





    }

    //inflates the toolbar menu onto the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    //buttons for the toolbar and their actions
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_button:

                FragmentManager fm =  getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.main_fragment,searchFragment.class,null).commit();

            default:
                return false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        retrieve.cancel(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //main_contentView view = new main_contentView();
        //view.inflateList(this,t.getArray());
    }
}
