package com.alexanderivanets.filmie;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.alexanderivanets.filmie.MVPAttempt.popular.PopularView;
import com.alexanderivanets.filmie.MVPAttempt.searchfilm.SearchFilmView;
import com.alexanderivanets.filmie.MVPAttempt.settings.SettingsActivity;
import com.alexanderivanets.filmie.MVPAttempt.upcoming.UpcomingView;
import com.alexanderivanets.filmie.MVPAttempt.userfilmlist.UserFilmListView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    FragmentManager fragmentManager;
    android.support.v4.app.Fragment fragment;
    android.support.v7.widget.SearchView searchView;
    Class fragmentClass;
    private boolean internetIsAlive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Justify");
        setSupportActionBar(toolbar);



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
        searchView = (android.support.v7.widget.SearchView)
                MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        switch(id){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_search:

                searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Fragment searchFragment;
                        searchFragment = new SearchFilmView().newInstance(query);
                        fragmentManager  =getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fl_content,searchFragment).commit();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        CheckInternetAvailibiality cia = new CheckInternetAvailibiality(getBaseContext());
        Thread t = new Thread(cia);
        t.start();
        try {
            t.join();
            internetIsAlive = cia.getState();
        } catch (InterruptedException e) {
            Log.v("Error"," while joining thread of checking the internet");
        }

        int id = item.getItemId();

        fragment = null;
        //Class fragmentClass;

        if (id == R.id.nav_popular) {
            // Handle the camera action
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);


            if(!internetIsAlive){
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return false;
            }

            fragmentClass = PopularView.class;

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
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);


            fragmentClass = UserFilmListView.class;

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

        } else if (id == R.id.nav_nowPlaying) {

        } else if (id == R.id.nav_genres) {


        } else if (id == R.id.nav_upcoming) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            if(!internetIsAlive){
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return false;
            }

            fragmentClass = UpcomingView.class;

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
    protected void onRestart() {
        if(fragmentClass == UserFilmListView.class){
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


        super.onRestart();

    }
}
