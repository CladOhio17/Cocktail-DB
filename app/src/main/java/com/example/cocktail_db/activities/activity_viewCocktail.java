package com.example.cocktail_db.activities;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.example.cocktail_db.databinding.ViewCocktailBinding;

public class activity_viewCocktail extends AppCompatActivity {

    private ViewCocktailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ViewCocktailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Toolbar toolbar = binding.toolbar_main;
        //setSupportActionBar(toolbar);
       // CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
       // toolBarLayout.setTitle(getTitle());

        //TODO
        /* -Set a button to change from a heart to an "X" when cocktail is favorited
            Change text From "Add to Favs" to "Remove from favs"
            -All the back end from a database
         */
    }
}