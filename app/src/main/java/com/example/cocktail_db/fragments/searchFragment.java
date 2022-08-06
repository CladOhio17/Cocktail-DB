package com.example.cocktail_db.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cocktail_db.R;
import com.example.cocktail_db.library.searchCocktailName;

public class searchFragment extends Fragment {
    Fragment fragment = this;
    searchCocktailName searchCocktail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View newView =  inflater.inflate(R.layout.search_fragment, container, false);




        // Inflate the layout for this fragment


        return newView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        SearchView searchView = (SearchView) view.findViewById(R.id.search_bar);
        searchView.setIconified(false);
        searchView.setQueryHint("Enter Cocktail Name");
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            /**
             * @param query the query text that is to be submitted
             * @return true if the query has been handled by the listener, false to let the
             * SearchView perform the default action.
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                FragmentManager fm =  getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.main_page_container, loadingFragment.class,null).commit();
                searchCocktail = new searchCocktailName(fragment, searchView.getQuery().toString());
                searchCocktail.execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }

        });
    }
}
