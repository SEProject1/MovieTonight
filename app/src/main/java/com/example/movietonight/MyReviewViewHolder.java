package com.example.movietonight;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyReviewViewHolder extends RecyclerView.ViewHolder{
    TextView tvReviewMovieTitle,tvDate,tvReviewTitle,tvReview;
    ImageButton btnReviewDel;
    MyReviewViewHolder(Context context, View itemView){
        super(itemView);
        tvReviewMovieTitle=(TextView)itemView.findViewById(R.id.tvReviewMovieTitle);
        tvDate=(TextView) itemView.findViewById(R.id.tvDate);
        btnReviewDel=(ImageButton) itemView.findViewById(R.id.btnReviewDel);
        tvReviewTitle=(TextView) itemView.findViewById(R.id.tvReviewTitle);
        tvReview=(TextView) itemView.findViewById(R.id.tvReview);
    }
}
