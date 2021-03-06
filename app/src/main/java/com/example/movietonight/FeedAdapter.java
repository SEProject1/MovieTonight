package com.example.movietonight;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movietonight.Class.Notification;
import com.example.movietonight.Class.UserAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder>{
    private ArrayList<Feed> feedData=null;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference("UserAccount");
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://movietonight-78dfc.appspot.com");
    private StorageReference storageReference = storage.getReference();
    public FeedAdapter(){
        feedData=new ArrayList<>();
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=(LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.feed_item,parent,false);
        FeedViewHolder viewHolder=new FeedViewHolder(context,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUid = firebaseUser.getUid();
        //String currentNickname;
        SimpleDateFormat transFormat=new SimpleDateFormat("yyyy/MM/dd");
        Feed item=feedData.get(position);//????????? ??????position????????? feed?????? ?????????
        String nickName=item.getNickName();
        String movieTitle=item.getMovieTitle();
        String genre=item.getGenre();
        String review=item.getReview();
        String reviewTitle=item.getReviewTitle();
        String like=item.getLike();
        String dislike=item.getDislike();
        String date=transFormat.format(item.getMdate());
        String idToken=item.getIdToken();
        holder.tvNickname.setText(nickName);
        holder.tvMovieTitle.setText(movieTitle);
        holder.tvMovieGenre.setText(genre);
        holder.tvReview.setText(review);
        holder.tvLike.setText(like);
        holder.tvDislike.setText(dislike);
        holder.tvReviewTitle.setText(reviewTitle);
        holder.tvDate.setText(date);
        storageReference.child(idToken+"/").child("profileimg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext()).load(uri).into(holder.ivProfilePic);
            }
        });
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db??? like??? +1
                HashMap<String,Object> likeUpdate=new HashMap<String,Object>();
                likeUpdate.put("like",Integer.parseInt((String) holder.tvLike.getText())+1);
                databaseReference.child(idToken).child("Review").child(movieTitle).updateChildren(likeUpdate);
                int updated_like= Integer.parseInt((String)holder.tvLike.getText())+1;
                holder.tvLike.setText(Integer.toString(updated_like));//Ui??? like+1
                addNotification(item.getReviewTitle(), currentUid, idToken); //????????? ??????
            }
        });

        holder.btnDisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db??? Dislike??? +1
                HashMap<String,Object> dislikeUpdate=new HashMap<String,Object>();
                dislikeUpdate.put("dislike",Integer.parseInt((String) holder.tvDislike.getText())+1);
                databaseReference.child(idToken).child("Review").child(movieTitle).updateChildren(dislikeUpdate);
                int updated_dislike= Integer.parseInt((String)holder.tvDislike.getText())+1;
                holder.tvDislike.setText(Integer.toString(updated_dislike));//Ui??? like+1
            }
        });

    }

    private void addNotification(String reviewTitle, String currentUid, String idToken){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserAccount").child(currentUid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount userAccount = snapshot.getValue(UserAccount.class);
                Notification notification = new Notification();
                String usernickname = userAccount.getUserNickname();
                notification.setNickname(usernickname +"??????");
                notification.setUserid(firebaseUser.getEmail());
                notification.setText("??? ????????? '?????????'??? ???????????????.");
                notification.setReviewTitle(reviewTitle);
                FirebaseDatabase.getInstance().getReference().child("UserAccount").child(idToken).child("Noti").push().setValue(notification);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    @Override
    public int getItemCount() {
        return feedData.size();
    }
    public void setFeedData(Feed data){//????????? ??????
        feedData.add(data);
    }
}