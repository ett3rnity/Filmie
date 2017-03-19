package com.alexanderivanets.filmie;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Саня on 14.03.2017.
 */

public class CardViewHolder extends RecyclerView.ViewHolder {
    TextView mFilmName;
    ImageView mFilmStat;
    ImageView mFilmPic;

    public CardViewHolder(View itemView) {
        super(itemView);

        mFilmName = (TextView)itemView.findViewById(R.id.card_view_tv_filmName);
        mFilmStat = (ImageView)itemView.findViewById(R.id.card_view_iv_filmStat);
        mFilmPic = (ImageView)itemView.findViewById(R.id.card_view_iv_filmPic);
    }





}
