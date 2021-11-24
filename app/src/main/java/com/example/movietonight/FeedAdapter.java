package com.example.movietonight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder>{
    private ArrayList<Feed> feedData=null;
    private FirebaseUser firebaseUser;


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
        SimpleDateFormat transFormat=new SimpleDateFormat("yyyy/MM/dd");
        Feed item=feedData.get(position);//리스트 안의position위치의 feed객체 꺼내기
        String nickName=item.getNickName();
        String movieTitle=item.getMovieTitle();
        String genre=item.getGenre();
        String review=item.getReview();
        String reviewTitle=item.getReviewTitle();
        String like=item.getLike();
        String dislike=item.getDislike();
        String date=transFormat.format(item.getMdate());
        //holder.ivProfilePic.setImageResource();//프로필설정
        holder.tvNickname.setText(nickName);
        holder.tvMovieTitle.setText(movieTitle);
        holder.tvMovieGenre.setText(genre);
        holder.tvReview.setText(review);
        holder.tvLike.setText(like);
        holder.tvDislike.setText(dislike);
        holder.tvReviewTitle.setText(reviewTitle);
        holder.tvDate.setText(date);

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db에 like수 +1
                int updated_like= Integer.parseInt((String)holder.tvLike.getText())+1;
                holder.tvLike.setText(Integer.toString(updated_like));//Ui에 like+1
                addNotification(item.getReviewTitle(), item.getNickName()); //좋아요 알림
            }
        });
        holder.btnDisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db에 Dislike수 +1
                int updated_dislike= Integer.parseInt((String)holder.tvDislike.getText())+1;
                holder.tvDislike.setText(Integer.toString(updated_dislike));//Ui에 like+1
            }
        });

    }

    private void addNotification(String reviewTitle, String nickName){
        HashMap<String, Object> map = new HashMap<>();

        map.put("userid", nickName);
        map.put("text", "내 리뷰에 '좋아요'가 눌렸습니다.");
        map.put("reviewId", reviewTitle);
        map.put("isReview", true);

        FirebaseDatabase.getInstance().getReference().child("Notification").child(firebaseUser.getUid()).push().setValue(map);

    }


    @Override
    public int getItemCount() {
        return feedData.size();
    }
    public void setFeedData(Feed data){//데이터 추가
        feedData.add(data);
    }
}