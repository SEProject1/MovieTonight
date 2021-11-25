package com.example.movietonight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FollowerAdapter extends RecyclerView.Adapter <FollowerViewHolder>{
    private ArrayList<FollowingList> myFollowerList=null;

    public FollowerAdapter (){
        myFollowerList=new ArrayList<>();
    }
    @NonNull
    @Override
    public FollowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=(LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.follower_item,parent,false);
        FollowerViewHolder viewHolder=new FollowerViewHolder(context,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerViewHolder holder, int position) {
        FollowingList item=myFollowerList.get(position);
        String nickName=item.getNickName();
        String idToken=item.getIdToken();
        holder.tvFollowerNickName.setText(nickName);

    }

    @Override
    public int getItemCount() {
        return myFollowerList.size();
    }
    public void setMyFollowerList(FollowingList data){
        myFollowerList.add(data);
    }
}
