package com.alexanderivanets.filmie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by root on 01.04.17.
 */

public class FilmBlockHolder extends RecyclerView.ViewHolder {
    ImageView mFilmBlockStat;
    TextView mFilmBlockHead;
    TextView mFilmBlockBody;

    public FilmBlockHolder(View itemView){
        super(itemView);

        mFilmBlockBody = (TextView)itemView.findViewById(R.id.tv_filmblock_body);
        mFilmBlockHead = (TextView)itemView.findViewById(R.id.tv_filmblock_head);
        //mFilmBlockStat = (ImageView) itemView.findViewById(R.id.iv_filmblock_stat);
    }

    public ImageView getmFilmBlockStat() {
        return mFilmBlockStat;
    }

    public TextView getmFilmBlockBody() {
        return mFilmBlockBody;
    }

    public TextView getmFilmBlockHead() {
        return mFilmBlockHead;
    }

    public void setmFilmBlockBody(TextView mFilmBlockBody) {
        this.mFilmBlockBody = mFilmBlockBody;
    }

    public void setmFilmBlockHead(TextView mFilmBlockHead) {
        this.mFilmBlockHead = mFilmBlockHead;
    }

    public void setmFilmBlockStat(ImageView mFilmBlockStat) {
        this.mFilmBlockStat = mFilmBlockStat;
    }

}
