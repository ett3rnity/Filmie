package com.alexanderivanets.filmie;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.alexanderivanets.filmie.network.popularmovies.FragmentPopular;
import com.alexanderivanets.filmie.network.upcoming.FragmentUpcoming;


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
* delete:
*
*
* */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentPopular.OnFragmentPopualarInteractionListener,FragmentUpcoming.OnFragmentUpcomingInteractionListener {

    FragmentManager fragmentManager;
    android.support.v4.app.Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Justify");
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add fab to every card to add film", Snackbar.LENGTH_LONG)
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

        fragment = null;
        Class fragmentClass = null;

        if (id == R.id.nav_popular) {
            // Handle the camera action
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);


            fragmentClass = FragmentPopular.class;

            try {
                fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
             fragmentManager = getSupportFragmentManager();
            ////transaction move to part,where it is done
            fragmentManager.beginTransaction().replace(R.id.fl_content,fragment).commit();



        } else if (id == R.id.nav_mostWanted) {

        } else if (id == R.id.nav_myMovieList) {

        } else if (id == R.id.nav_nowPlaying) {

        } else if (id == R.id.nav_searchForMovie) {

        } else if (id == R.id.nav_upcoming) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);


            fragmentClass = FragmentUpcoming.class;

            try {
                fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            fragmentManager = getSupportFragmentManager();
            ////transaction move to part,where it is done
            fragmentManager.beginTransaction().replace(R.id.fl_content,fragment).commit();
        }



        return true;
    }

    @Override
    public void onFragmentInteraction() {
        //fragmentManager.beginTransaction().replace(R.id.fl_content,fragment).commit();
    }





}
