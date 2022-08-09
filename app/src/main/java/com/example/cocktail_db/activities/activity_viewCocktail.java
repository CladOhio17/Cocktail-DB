package com.example.cocktail_db.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.cocktail_db.R;
import com.example.cocktail_db.fragments.loadingFragment;
import com.example.cocktail_db.fragments.searchFragment;
import com.example.cocktail_db.fragments.viewInfoFragment;
import com.example.cocktail_db.library.infoGrabber;
import com.example.cocktail_db.library.navMenuSetup;
import com.example.cocktail_db.library.randomCocktail;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cocktail_db.databinding.ViewCocktailBinding;

public class activity_viewCocktail extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    searchFragment fragment = new searchFragment();
    Fragment viewFragment = new viewInfoFragment();


    /**
     * @param savedInstanceState Saved State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        infoGrabber grab = new infoGrabber(this, id);
        setContentView(R.layout.view_cocktail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        TextView title = findViewById(R.id.title);
        title.setText("The Cocktail DB");


        //initializations for the drawer to work
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        ImageView imageView = (ImageView) findViewById(R.id.main_nav_menu);
        navigationView = (NavigationView) findViewById(R.id.main_nav);
        navMenuSetup setup = new navMenuSetup(navigationView,"View Cocktail","Nate Yach - 1.0");


        grab.execute();
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
                        finish();
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
}