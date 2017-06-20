package com.alexanderivanets.filmie;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by root on 19.06.17.
 */

public class AnalogCardViewHolder extends RecyclerView.ViewHolder {
    TextView mFilmName;
    ImageView mFilmStat;
    ImageView mFilmPic;
    TextView mFilmId;
    MaterialRatingBar mRatingBar;

    public AnalogCardViewHolder(View itemView,final String className) {
        super(itemView);

        mFilmName = (TextView)itemView.findViewById(R.id.analog_filmName);
        mFilmId = (TextView)itemView.findViewById(R.id.analog_filmId);
        mFilmPic = (ImageView)itemView.findViewById(R.id.analog_filmPic);
        mRatingBar = (MaterialRatingBar)itemView.findViewById(R.id.analog_ratingBar);

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
