package com.alexanderivanets.filmie;

import android.graphics.Bitmap;
import android.widget.TextView;

/**
 * Created by root on 01.04.17.
 */

public class FilmBlock {

    private Bitmap mBlockImg;
    private String mBlockHead;
    private String mBlockBody;

    public Bitmap getmBlockImg() {
        return mBlockImg;
    }

    public void setmBlockImg(Bitmap mBlockImg) {
        this.mBlockImg = mBlockImg;
    }

    public String getmBlockBody() {
        return mBlockBody;
    }

    public void setmBlockBody(String mBlockBody) {
        this.mBlockBody = mBlockBody;
    }

    public String getmBlockHead() {
        return mBlockHead;
    }

    public void setmBlockHead(String mBlockHead) {
        this.mBlockHead = mBlockHead;
    }
}
