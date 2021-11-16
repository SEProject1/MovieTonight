package com.example.movietonight;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteListViewHolder extends RecyclerView.ViewHolder{
    ImageButton btnFavoriteMovieDel;
    TextView tvFavoriteMovieTitle;

   FavoriteListViewHolder(Context context, View itemView){
       super(itemView);
       btnFavoriteMovieDel=(ImageButton) itemView.findViewById(R.id.btnFavoriteMovieDel);
       tvFavoriteMovieTitle=(TextView) itemView.findViewById(R.id.tvFavoriteMovieTitle);
   }
}
