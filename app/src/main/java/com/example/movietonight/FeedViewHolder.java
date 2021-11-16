package com.example.movietonight;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedViewHolder extends RecyclerView.ViewHolder {
    ImageView ivProfilePic;
    TextView tvNickname,tvMovieTitle,tvMovieGenre,tvReview,tvLike,tvDislike;

    public FeedViewHolder(Context context, @NonNull View itemView) {
        super(itemView);
        ivProfilePic=itemView.findViewById(R.id.ivProfilePic);
        tvNickname=itemView.findViewById(R.id.tvNickname);
        tvMovieTitle=itemView.findViewById(R.id.tvMovieTitle);
        tvMovieGenre=itemView.findViewById(R.id.tvMovieGenre);
        tvReview=itemView.findViewById(R.id.tvReview);
        tvLike=itemView.findViewById(R.id.tvLike);
        tvDislike=itemView.findViewById(R.id.tvDislike);

    }
}


