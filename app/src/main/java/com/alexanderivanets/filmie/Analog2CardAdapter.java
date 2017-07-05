package com.alexanderivanets.filmie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexanderivanets.filmie.MVPAttempt.CardInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by root on 04.07.17.
 */

public class Analog2CardAdapter extends RecyclerView.Adapter<Analog2ViewHolder>{
    private List<CardInfo> cardList;
    private Context context;
    private String className;


    CardInfo ci;

    public Analog2CardAdapter(List<CardInfo> cardList, Context context, String className){

        this.cardList = cardList;
        this.context = context;
        this.className = className;
    }

    public void addInfoCardAdapter(List<CardInfo> cardList){
        this.cardList.addAll(cardList);
    }


    @Override
    public Analog2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(context).inflate(R.layout.cardview_analog,parent,false);
        return new Analog2ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Analog2ViewHolder holder, int position) {
        ci = cardList.get(position);

        holder.mFilmName.setText(ci.getmFilmName());
        holder.mFilmStat.setImageBitmap(ci.getmFilmStat());
        holder.mFilmId.setText(ci.getmFilmId());
        Picasso.with(context).load(cardList.get(position).getmTrailerPath()).fit().into(holder.mFilmPic);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
