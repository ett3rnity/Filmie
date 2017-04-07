package com.alexanderivanets.filmie;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alexanderivanets.filmie.network.TMDBApi;
import com.alexanderivanets.filmie.network.popularmovies.MovieListPopular;
import com.alexanderivanets.filmie.network.selectedmovie.SelectedFilmInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilmInfoActivity extends AppCompatActivity implements FragmentFilmInfo.OnFragmentInteractionListener {

    String filmId;
    FragmentManager fragmentManager;
    android.support.v4.app.Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        //hardcode
        Intent intent = getIntent();
        filmId = intent.getStringExtra("id");


        fragment =null;
        Class fragmentClass = null;
        fragmentClass = FragmentFilmInfo.class;

        Bundle bundle = new Bundle();
        String sendFilmId = filmId;
        bundle.putString("id",sendFilmId);

        try {
            fragment = (android.support.v4.app.Fragment)fragmentClass.newInstance();
            fragment.setArguments(bundle);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }



        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_content_filminfo,fragment).commit();

        //
        // tv_filminfo_filmid = (TextView)findViewById(R.id.tv_filminfo_filmid);


    }


    @Override
    public void onFragmentInteraction() {

    }
}
