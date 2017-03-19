package com.alexanderivanets.filmie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexanderivanets.filmie.network.popularmovies.MovieListPopular;
import com.alexanderivanets.filmie.network.popularmovies.PopularMoviesInitialization;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.bitmap;


/*
*
* connect->json->parse json->movieList->movie->card->card adapter->Activity
*connect: +
*json:+
* to do list:
* 1)picasso for picture downloads
* 2)connect cardview adapter with activity
* 3)add information from movieListPopular to every card
*
*
* */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MovieListPopular mlp;
    RecyclerView main_recyclerView;
    Bitmap poster;//in future rewrite it out of global var

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //hardcode
        PopularMoviesInitialization retreiveData = new PopularMoviesInitialization();
        mlp = retreiveData.getMovieListPopular();
        int i=0;
        //works nice,stores data into mlp
        main_recyclerView = (RecyclerView)findViewById(R.id.main_recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        main_recyclerView.setLayoutManager(llm);


        ArrayList<String> urlList = createUrlList(mlp.getResults().size());

        CardAdapter cardAdapter = new CardAdapter(createList(mlp.getResults().size()), urlList,
                getApplicationContext());

        main_recyclerView.setAdapter(cardAdapter);
        //


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //set info into card function
    public List<CardInfo> createList(int size){
        List<CardInfo> list = new ArrayList<CardInfo>();

        for(int i=0;i<size;i++){
            CardInfo ci = new CardInfo();


            ci.setmFilmName(mlp.getResults().get(i).getOriginalTitle());
            ci.setmFilmId(mlp.getResults().get(i).getId().toString());

            CardAdapter adapter =(CardAdapter) main_recyclerView.getAdapter();//hzhz

                ci.setmFilmStat(BitmapFactory.decodeResource(getResources(),R.drawable.star_notclicked));

            list.add(ci);
        }
        return list;
    }


    public ArrayList<String> createUrlList(int size){

            ArrayList<String> list = new ArrayList<>();

            for(int i=0;i<size;i++) {
                String picturePath = "http://image.tmdb.org/t/p/w300/" + mlp.getResults().get(i)
                        .getBackdropPath();

                list.add(picturePath);
            }
        return list;
    }



}
