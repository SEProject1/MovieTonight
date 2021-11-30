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

public class SingleDataAdapter extends RecyclerView.Adapter<SingleDataAdapter.SingleItemRowHolder> {

    private ArrayList<SingleItem> itemsList;
    private LayoutInflater mInflate;
    private Context mContext;

    public SingleDataAdapter(Context context, ArrayList<SingleItem> itemsList) {
        this.itemsList = itemsList;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {
        String url = "https://image.tmdb.org/t/p/w500" + itemsList.get(i).getPoster_path();
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holder.imageView_m);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("click", String.valueOf(itemsList));
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("id", itemsList.get(holder.getAdapterPosition()).getId());
                intent.putExtra("title", itemsList.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("original_title", itemsList.get(holder.getAdapterPosition()).getOriginal_title());
                intent.putExtra("poster_path", itemsList.get(holder.getAdapterPosition()).getPoster_path());
                intent.putExtra("overview", itemsList.get(holder.getAdapterPosition()).getOverview());
                intent.putExtra("release_date", itemsList.get(holder.getAdapterPosition()).getRelease_date());
                intent.putExtra("vote_average", itemsList.get(holder.getAdapterPosition()).getVote_average());
                intent.putIntegerArrayListExtra("genre_ids", itemsList.get(holder.getAdapterPosition()).getGenre_ids());
                mContext.startActivity(intent);
                Log.d("Adapter", "Clcked: " + holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemsList.size();
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        public ImageView imageView_m;

        public SingleItemRowHolder(View itemView) {
            super(itemView);
            imageView_m = (ImageView) itemView.findViewById(R.id.imageView_m);
        }
    }
}
