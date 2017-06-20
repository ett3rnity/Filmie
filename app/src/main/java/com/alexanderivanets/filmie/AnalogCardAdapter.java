package com.alexanderivanets.filmie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexanderivanets.filmie.MVPAttempt.CardInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by root on 19.06.17.
 */

public class AnalogCardAdapter extends RecyclerView.Adapter<AnalogCardViewHolder> {

    private ArrayList<CardInfo>cardList;
    private Context context;
    private String className;

    CardInfo ci;

    public AnalogCardAdapter(ArrayList<CardInfo>cardList, Context  context, String className){
        this.cardList = cardList;
        this.context = context;
        this.className = className;
    }

    public void addInfoToAdapter(ArrayList<CardInfo> cardList){
        this.cardList.addAll(cardList);
    }

    @Override
    public AnalogCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(context).inflate(R.layout.analog_cardview,parent,false);
        return new AnalogCardViewHolder(itemView,className);
    }

    @Override
    public void onBindViewHolder(AnalogCardViewHolder holder, int position) {
        ci = cardList.get(position);
        holder.mFilmId.setText(ci.getmFilmId());
        holder.mFilmName.setText(ci.getmFilmName());
        holder.mRatingBar.setRating(Float.valueOf(ci.getmFilmVote()));
        //holder.mFilmStat.setImageBitmap(ci.getmFilmStat());
        Picasso.with(context).load(ci.getmTrailerPath()).fit().into(holder.mFilmPic);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
