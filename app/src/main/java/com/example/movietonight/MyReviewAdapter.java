package com.example.movietonight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewViewHolder>{
    private ArrayList<MyReview> myReviewList=null;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference("UserAccount");
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    MyReviewAdapter(){
        myReviewList=new ArrayList<>();
    }
    @NonNull
    @Override
    public MyReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=(LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.myreview_item,parent,false);
        MyReviewViewHolder viewHolder=new MyReviewViewHolder(context,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyReviewViewHolder holder, int position) {
        MyReview item=myReviewList.get(position);//리스트 안의position위치의 MyReview객체 꺼내기
        String mt=item.getMovieTitle();//MyReview객체의 영화 이름 받아오기
        String date= item.getDate();//MyReview객체에서 날짜 받기
        String reviewTitle= item.getReviewTitle();//리뷰의 제목받기
        String review=item.getReview();
        holder.tvReviewMovieTitle.setText(mt);//홀더에 영화 제목 설정
        holder.tvDate.setText(date);//홀더에 날짜 설정
        holder.tvReviewTitle.setText(reviewTitle);
        holder.tvReview.setText(review);

        holder.btnReviewDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db에서 해당 리뷰 삭제
                databaseReference.child(user.getUid()).child("Review").child(mt).
                        removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(view.getContext(),"리뷰가 삭제되었습니다.",Toast.LENGTH_LONG).show();
                    }
                });
                myReviewList.remove(holder.getAdapterPosition());//UI에서 해당 item삭제
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(),myReviewList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return myReviewList.size();
    }
    public void setMyReviewList(MyReview data){//데이터 추가
        myReviewList.add(data);
    }
    public void clearMyReviewList(){
        myReviewList.clear();
    }
}
