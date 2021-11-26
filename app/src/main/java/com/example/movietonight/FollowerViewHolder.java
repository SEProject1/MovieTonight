package com.example.movietonight;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowerViewHolder extends RecyclerView.ViewHolder {
    TextView tvFollowerNickName;
    CircleImageView ivFollowerUserPic;
    public FollowerViewHolder(Context context, @NonNull View itemView) {
        super(itemView);
        tvFollowerNickName=itemView.findViewById(R.id.tvFollowerNickName);
        ivFollowerUserPic=itemView.findViewById(R.id.ivFollowerUserPic);
    }
}
