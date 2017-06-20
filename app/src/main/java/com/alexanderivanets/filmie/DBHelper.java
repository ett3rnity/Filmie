package com.alexanderivanets.filmie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 07.05.17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "MyFilmListDB";

    public DBHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
     }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDBScript = "CREATE TABLE filmList ( " +
                "id INTEGER, " +
                "title TEXT, " +
                "genres TEXT, " +
                "overview TEXT, " +
                "duration TEXT, " +
                "releaseDate TEXT );";
        db.execSQL(createDBScript);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS filmList");

        this.onCreate(db);
    }


}
