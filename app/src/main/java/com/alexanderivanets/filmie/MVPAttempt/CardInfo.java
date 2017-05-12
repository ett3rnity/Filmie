package com.alexanderivanets.filmie.MVPAttempt;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Саня on 14.03.2017.
 */

public class CardInfo {

    private String mFilmName;
    private Bitmap mFilmPic;
    private Bitmap mFilmStat;
    private String mFilmId;
    private String mTrailerPath;

    public String getmTrailerPath() {
        return mTrailerPath;
    }

    public void setmTrailerPath(String mTrailerPath) {
        this.mTrailerPath = mTrailerPath;
    }

    public void setmFilmName(String mFileName) {
        this.mFilmName = mFileName;
    }


    public void setmFilmId(String mFilmId) {
        this.mFilmId = mFilmId;
    }

    public String getmFilmId() {
        return this.mFilmId;
    }

    public Bitmap getmFilmPic() {
        return this.mFilmPic;
    }

    public String getmFilmName() {
        return this.mFilmName;
    }

    public void setmFilmPic(Bitmap mFilmPic) {
        this.mFilmPic = mFilmPic;
    }

    public void setmFilmStat(Bitmap mFilmStat) {
        this.mFilmStat = mFilmStat;
    }

    public Bitmap getmFilmStat() {
        return this.mFilmStat;
    }

}
