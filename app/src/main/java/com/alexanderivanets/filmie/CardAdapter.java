package com.alexanderivanets.filmie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Саня on 14.03.2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private List<CardInfo> cardList;
    private List<String> urlList;
    private Context context;


    CardInfo ci;


    public CardAdapter(List<CardInfo> cardList, List<String> urlList, Context context) {

        this.cardList = cardList;
        this.urlList = urlList;
        this.context = context;
    }

    public void addInfoCardAdapter(List<CardInfo> cardList, List<String> urlList){
        this.cardList.addAll(cardList);
        this.urlList.addAll(urlList);
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

        Picasso.with(context).load(urlList.get(position)).fit().into(holder.mFilmPic);

        holder.mFilmStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // change picture after clicking->saving to marked
            }
        });



    }




    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
