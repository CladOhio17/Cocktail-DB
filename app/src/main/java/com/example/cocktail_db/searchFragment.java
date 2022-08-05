package com.example.cocktail_db;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class searchFragment extends Fragment {
    Fragment fragment = this;

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
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                FragmentManager fm =  getActivity().getSupportFragmentManager();
                getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                return false;
            }
        });
    }
}
