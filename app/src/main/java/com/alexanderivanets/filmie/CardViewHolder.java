package com.alexanderivanets.filmie;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Саня on 14.03.2017.
 */

public class  CardViewHolder extends RecyclerView.ViewHolder {
    TextView mFilmName;
    ImageView mFilmStat;
    ImageView mFilmPic;
    TextView mFilmId;

    public CardViewHolder(View itemView, final String className) {
        super(itemView);

        mFilmName = (TextView) itemView.findViewById(R.id.card_view_tv_filmName);
        mFilmStat = (ImageView) itemView.findViewById(R.id.card_view_iv_filmStat);
        mFilmPic = (ImageView) itemView.findViewById(R.id.card_view_iv_filmPic);
        mFilmId = (TextView) itemView.findViewById(R.id.card_view_tv_filmId);



        mFilmPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),FilmInfoActivity.class);
                String filmId = mFilmId.getText().toString();
                intent.putExtra("id",filmId);
                intent.putExtra("className",className);
                v.getContext().startActivity(intent);

            }
        });
    }


}
