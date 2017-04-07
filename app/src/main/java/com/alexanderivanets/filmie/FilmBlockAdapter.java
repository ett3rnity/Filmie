package com.alexanderivanets.filmie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by root on 01.04.17.
 */

public class FilmBlockAdapter extends RecyclerView.Adapter<FilmBlockHolder> {
    private List<FilmBlock> filmBlockList;
    private Context context;

    FilmBlock fb;

    public FilmBlockAdapter(List<FilmBlock> filmBlockList, Context context){
        this.filmBlockList = filmBlockList;
        this.context = context;
    }

    @Override
    public FilmBlockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_film_block,parent,false);
        return new FilmBlockHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FilmBlockHolder holder, int position) {

        fb = filmBlockList.get(position);
        //holder.mFilmBlockStat.setImageBitmap(fb.getmBlockImg());
        holder.mFilmBlockHead.setText(fb.getmBlockHead());
        holder.mFilmBlockBody.setText(fb.getmBlockBody());


    }

    @Override
    public int getItemCount() {
        return filmBlockList.size();
    }
}
