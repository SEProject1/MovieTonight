package com.example.movietonight;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FollowingListViewHolder extends RecyclerView.ViewHolder {
    TextView tvFollowingNickName;
    Button btnUnfollow;
    ImageView ivFollwingUserPic;
    public FollowingListViewHolder(Context context, @NonNull View itemView) {
        super(itemView);
        tvFollowingNickName=itemView.findViewById(R.id.tvFollowingNickName);
        btnUnfollow=itemView.findViewById(R.id.btnUnfollow);
        ivFollwingUserPic=itemView.findViewById(R.id.ivFollwingUserPic);
    }
}
