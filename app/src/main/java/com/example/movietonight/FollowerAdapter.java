package com.example.movietonight;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FollowerAdapter extends RecyclerView.Adapter <FollowerViewHolder>{
    private ArrayList<FollowingList> myFollowerList=null;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://movietonight-78dfc.appspot.com");
    private StorageReference storageReference = storage.getReference();
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
        storageReference.child(idToken+"/").child("profileimg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext()).load(uri).into(holder.ivFollowerUserPic);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myFollowerList.size();
    }
    public void setMyFollowerList(FollowingList data){
        myFollowerList.add(data);
    }
}
