package com.alexanderivanets.filmie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;

/**
 * Created by root on 07.04.17.
 */

public class YoutubeBlockHolder extends RecyclerView.ViewHolder {
    YouTubePlayer youTubePlayer;
    ImageView mYouTubeBlockStat;
    TextView mYouTubeBlockHead;

    public YoutubeBlockHolder(View itemView){
        super(itemView);
        youTubePlayer = (YouTubePlayer)itemView.findViewById(R.id.youtube_player_block);
        mYouTubeBlockHead = (TextView)itemView.findViewById(R.id.tv_youtubeblock_head);
    }
}
