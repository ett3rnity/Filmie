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
 * Created by Саня on 14.03.2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private List<CardInfo> cardList;
    private Context context;


    CardInfo ci;


    public CardAdapter(List<CardInfo> cardList, Context context) {

        this.cardList = cardList;
        this.context = context;
    }

    public void addInfoCardAdapter(List<CardInfo> cardList){
        this.cardList.addAll(cardList);
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cardview, parent, false);

        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        ci = cardList.get(position);
        holder.mFilmPic.setImageBitmap(ci.getmFilmPic());
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
