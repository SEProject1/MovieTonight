package com.example.movietonight;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.RecyclerViewHolders>{

    private ArrayList<Movie> mMovieList;
    private LayoutInflater mInflate;
    private Context mContext;

    //constructor
    public SearchAdapter(Context context, ArrayList<Movie> itemList) {
        this.mContext = context;
        this.mInflate = LayoutInflater.from(context);
        this.mMovieList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.list_item, parent, false);
        RecyclerViewHolders viewHolder = new RecyclerViewHolders(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders holder, int position) {
        //포스터만
        String url = "https://image.tmdb.org/t/p/w500" + mMovieList.get(position).getPoster_path();
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("click", String.valueOf(mMovieList));
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("title", mMovieList.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("original_title", mMovieList.get(holder.getAdapterPosition()).getOriginal_title());
                intent.putExtra("poster_path", mMovieList.get(holder.getAdapterPosition()).getPoster_path());
                intent.putExtra("overview", mMovieList.get(holder.getAdapterPosition()).getOverview());
                intent.putExtra("release_date", mMovieList.get(holder.getAdapterPosition()).getRelease_date());
                intent.putExtra("vote_average", mMovieList.get(holder.getAdapterPosition()).getVote_average());
                intent.putIntegerArrayListExtra("genre_ids", mMovieList.get(holder.getAdapterPosition()).getGenre_ids());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.mMovieList.size();
    }


    //뷰홀더 - 따로 클래스 파일로 만들어도 된다.
    public static class RecyclerViewHolders extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

}