package com.example.cocktail_db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    /*TODO
       -Fix Coloring of nav menu
       -Implement a Class to make setting up Nav menu Easier
       -Back end for getting cocktails from DB
       -Display List of Cocktails
         */

    searchFragment fragment = new searchFragment();
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        TextView title = findViewById(R.id.title);
        title.setText("Cocktails");

        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        ImageView imageView = (ImageView) findViewById(R.id.main_nav_menu);
        navigationView = (NavigationView) findViewById(R.id.main_nav);
        navMenuSetup setup = new navMenuSetup(navigationView,"Main Page","Nate Yach - 1.0");


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }
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
