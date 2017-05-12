package com.alexanderivanets.filmie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubeThumbnailView;

/**
 * Created by root on 07.04.17.
 */

public class YoutubeBlockHolder extends RecyclerView.ViewHolder {
    YouTubeThumbnailView mYouTubeThumbnailView;
    ImageView mYouTubeBlockStat;
    TextView mYouTubeBlockHead;

    public YoutubeBlockHolder(View itemView){
        super(itemView);
        //mYouTubeThumbnailView = new YouTubeThumbnailView(itemView.getContext());
        mYouTubeThumbnailView = (YouTubeThumbnailView)itemView.findViewById(R.id.youtube_player_block);
        mYouTubeBlockHead = (TextView)itemView.findViewById(R.id.tv_youtubeblock_head);




    }

    public ImageView getmYouTubeBlockStat() {
        return mYouTubeBlockStat;
    }

    public TextView getmYouTubeBlockHead() {
        return mYouTubeBlockHead;
    }

    public void setmYouTubeBlockHead(TextView mYouTubeBlockHead) {
        this.mYouTubeBlockHead = mYouTubeBlockHead;
    }

    public void setmYouTubeBlockStat(ImageView mYouTubeBlockStat) {
        this.mYouTubeBlockStat = mYouTubeBlockStat;
    }

    public void setYouTubePlayer(YouTubeThumbnailView youTubePlayer) {
        this.mYouTubeThumbnailView = youTubePlayer;
    }

    public YouTubeThumbnailView getYouTubePlayer() {
        return mYouTubeThumbnailView;
    }

}
