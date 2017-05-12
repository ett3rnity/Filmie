package com.alexanderivanets.filmie;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alexanderivanets.filmie.MVPAttempt.FilmInfo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FilmInfoActivity extends AppCompatActivity implements FragmentFilmInfo.OnFragmentInteractionListener {

    String filmId;
    String imageLink;
    FilmInfo filmInfo;
    boolean alreadyInDb;
    boolean deleteFromDb;
    boolean firstFabClick;
    FragmentManager fragmentManager;
    android.support.v4.app.Fragment fragment;
    DBHelper dbHelper;
    SQLiteDatabase userFilmDB;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alreadyInDb = false;
        deleteFromDb = false;
        firstFabClick = false;
        dbHelper = new DBHelper(this);
        userFilmDB = dbHelper.getWritableDatabase();


        fab = (FloatingActionButton) findViewById(R.id.fab);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        //hardcode
        Intent intent = getIntent();
        filmId = intent.getStringExtra("id");

        fabInteractions();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabClicked();
            }
        });


        fragment =null;
        Class fragmentClass = null;
        fragmentClass = FragmentFilmInfo.class;

        Bundle bundle = new Bundle();
        String sendFilmId = filmId;
        boolean isIntoDb = alreadyInDb;
        bundle.putBoolean("isIntoDb",isIntoDb);
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


    }




    @Override
    public void onInfoDownloaded(int mId, String mTitle, String mGenres, String mOverview, String mDuration, String mReleaseDate, String backDropPath) {
        filmInfo = new FilmInfo(String.valueOf(mId), mTitle, mGenres, mOverview, mDuration, mReleaseDate);
        imageLink = backDropPath;
    }

    public void fabInteractions(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM filmList;",null);

        if(cursor != null){
            if(cursor.moveToFirst()){

                do{
                    Log.v("--IN DB--",cursor.getString(cursor.getColumnIndex("title")));
                    if(cursor.getInt(cursor.getColumnIndex("id")) == Integer.valueOf(filmId)){
                        firstFabClick = true;
                        alreadyInDb = true;
                    }
                }while(cursor.moveToNext());
            }
            cursor.close();
            }

        if(alreadyInDb){
            fab.setImageResource(R.drawable.heart_checked_32px);
        }
        else{
            fab.setImageResource(R.drawable.heart_unchecked_32px);
        }
    }


    public void onFabClicked(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(!deleteFromDb){
            fab.setImageResource(R.drawable.heart_unchecked_32px);
            deleteFromDb = true;
        }
        else{
            if(!firstFabClick) {
                onFilmAddToList(db, filmInfo);
                firstFabClick = true;
            }
            fab.setImageResource(R.drawable.heart_checked_32px);
            deleteFromDb = false;
            alreadyInDb = true;
        }

    }


    public void onFilmAddToList(SQLiteDatabase db, FilmInfo filmInfo){
        String insertToDBScript = "INSERT INTO filmList VALUES ( '" +
                filmInfo.getId() + "', '"
                + filmInfo.getTitle() + "', '"
                + filmInfo.getGenre() + "', '"
                + filmInfo.getOverview() +"', '"
                + filmInfo.getDuration() + "', '"
                + filmInfo.getReleaseDate() + "' );";
        db.execSQL(insertToDBScript);

        try {
            onAddFilmCoverGet(filmInfo.getId());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onFilmDeleteFromList(SQLiteDatabase db, String deleteId){
        Cursor cursor = db.rawQuery("SELECT * FROM filmList;",null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    if (cursor.getInt(cursor.getColumnIndex("id"))==Integer.valueOf(deleteId)){
                        db.execSQL("DELETE FROM filmList WHERE id = '"+deleteId+"';");
                    }
                }while(cursor.moveToNext());
                cursor.close();
            }
        }
    }

    public void onAddFilmCoverGet(final String imageId) throws FileNotFoundException {
        File imageFolder = new File(Environment.getExternalStorageDirectory()+"/filmieDbPics");
        if(!imageFolder.exists()){
            imageFolder.mkdirs();
        }

        Picasso.with(getApplicationContext()).load(Config.STANDART_IMAGE_PATH + "/w780/" +
        imageLink).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                File imageFile = new File(Environment.getExternalStorageDirectory() + "/filmieDbPics/" +
                        imageId +".png");

                FileOutputStream fos = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(alreadyInDb&& deleteFromDb){
            File imageFile = new File(Environment.getExternalStorageDirectory() + "/filmieDbPics/" +
                    filmId +".png");
            imageFile.delete();

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            onFilmDeleteFromList(db,filmId);
        }
    }
}
