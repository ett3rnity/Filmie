package com.alexanderivanets.filmie.MVPAttempt.userfilmlist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.alexanderivanets.filmie.DBHelper;
import com.alexanderivanets.filmie.MVPAttempt.CardInfo;

import java.util.ArrayList;

/**
 * Created by root on 12.05.17.
 */

public class UserFilmListPresenter implements UserFilmListMVP.PInterface {

    private UserFilmListMVP.VtoPInterface view;
    DBHelper dbHelper;
    Context context;
    SQLiteDatabase db;

    public UserFilmListPresenter(UserFilmListView view, Context context){
        this.view = view;
        this.context = context;
    }

    @Override
    public void onAskInfo() {
        ArrayList<CardInfo> cardList = new ArrayList<>();
        dbHelper = new DBHelper(this.context);
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM filmList;",null);
        if(cursor.moveToFirst()){
            do{
                CardInfo cardInfo = new CardInfo();
                cardInfo.setmFilmId(cursor.getString(cursor.getColumnIndex("id")));
                cardInfo.setmTrailerPath("file:"+Environment.getExternalStorageDirectory()+
                        "/filmieDbPics/" + cursor.getString(cursor.getColumnIndex("id")) +".png");//to bitmap
                cardInfo.setmFilmName(cursor.getString(cursor.getColumnIndex("title")));
                cardList.add(cardInfo);
            }while (cursor.moveToNext());
            cursor.close();
        }
        view.onShowInfo(cardList);
    }


}
