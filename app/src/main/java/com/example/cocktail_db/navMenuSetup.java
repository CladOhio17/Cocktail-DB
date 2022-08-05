package com.example.cocktail_db;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
/*
 Sets up the nav menu so it can be reused for different activities
 */
public class navMenuSetup {

    /*
    @param nav - the navagation View from class that is calling the constructor
           title - a string containing the name of the current activity
           author and version - a string to be the author and version of the current activity
     */
    public navMenuSetup(NavigationView nav,String title,String authorAndVersion){

        Menu navMenu = nav.getMenu();
        MenuItem navTitle = navMenu.findItem(R.id.nav_title);
        MenuItem navAuthor = navMenu.findItem(R.id.nav_author_version);
        navTitle.setTitle(title);
        navAuthor.setTitle(authorAndVersion);
        navAuthor.setCheckable(false);
        navTitle.setChecked(true);

    }
}
