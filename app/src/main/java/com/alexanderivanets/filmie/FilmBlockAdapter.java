package com.alexanderivanets.filmie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;
import java.util.Random;

/**
 * Created by root on 01.04.17.
 */

public class FilmBlockAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements YouTubeThumbnailView.OnInitializedListener{
    private List<Object> filmBlockList;
    private final int infoBlock = 0, youtubeBlock = 1;
    private Context context;

    private YouTubeThumbnailView.OnInitializedListener onInitializedListener;
    private YouTubeThumbnailLoader thumbnailLoader;


    String filmId;

    public FilmBlockAdapter(List<Object> filmBlockList, Context context){
        this.filmBlockList = filmBlockList;
        this.context = context;

    }

    public void addToAdapter(List<Object> filmBlockListAdd){
        filmBlockList.addAll(filmBlockListAdd);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch(viewType){
            case infoBlock:
                View v1 = inflater.inflate(R.layout.activity_film_block,parent,false);
                viewHolder = new FilmBlockHolder(v1);
                break;

            case youtubeBlock:
                View v2 = inflater.inflate(R.layout.activity_youtube_block,parent,false);
                viewHolder = new YoutubeBlockHolder(v2);
                break;

            default:
                View v3 = inflater.inflate(R.layout.activity_film_block,parent,false);
                viewHolder = new FilmBlockHolder(v3);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch(viewHolder.getItemViewType()){

            case infoBlock:
                FilmBlockHolder vh1 = (FilmBlockHolder) viewHolder;
                configureFilmBlock(vh1,position);
                break;

            case youtubeBlock:
                YoutubeBlockHolder vh2 = (YoutubeBlockHolder) viewHolder;
                configureYouTubeBlock(vh2,position);
                break;

            default:
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(filmBlockList.get(position) instanceof FilmBlock){
            return infoBlock;
        }
        else if(filmBlockList.get(position) instanceof YoutubeBlock){
            return youtubeBlock;
        }

        return -1;
    }

    @Override
    public int getItemCount() {
        return filmBlockList.size();
    }

    public void configureFilmBlock(FilmBlockHolder viewHolder, int position){
        ///fb = filmBlockList.get(position);
        FilmBlock filmBlock = (FilmBlock)filmBlockList.get(position);

        // //holder.mFilmBlockStat.setImageBitmap(fb.getmBlockImg());

        viewHolder.mFilmBlockHead.setText(filmBlock.getmBlockHead());
        viewHolder.mFilmBlockBody.setText(filmBlock.getmBlockBody());
    }

    public void configureYouTubeBlock(YoutubeBlockHolder viewHolder, int position){
        YoutubeBlock youtubeBlock = (YoutubeBlock)filmBlockList.get(position);


        filmId = ((YoutubeBlock) filmBlockList.get(position)).getmYTVideoId();

        viewHolder.mYouTubeBlockHead.setText(youtubeBlock.getmYTHead());
        viewHolder.mYouTubeThumbnailView.initialize(Config.YOUTUBE_API_KEY,this);
        viewHolder.mYouTubeThumbnailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(context,YouTubePlayerActivity.class);
                intent.putExtra("trailerId",filmId);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

        thumbnailLoader = youTubeThumbnailLoader;
        thumbnailLoader.setVideo(filmId);
        youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                thumbnailLoader.release();
            }

            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

        });

    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

    }


}
