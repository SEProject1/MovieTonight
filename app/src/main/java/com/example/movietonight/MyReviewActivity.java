package com.example.movietonight;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MyReviewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyReviewAdapter adapter;
    private ImageButton btnBack;
    private ArrayList<MyReview> myReviews=new ArrayList<MyReview>();//리뷰목록을 저장
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference("UserAccount");
    private FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myreview_recycler);
        recyclerView = (RecyclerView)findViewById(R.id.myReivew_recycler);
        btnBack=(ImageButton)findViewById(R.id.btnBackMyReview);//뒤로가기 버튼
        String uid=user!=null? user.getUid():null;
        adapter = new MyReviewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager
                (this, RecyclerView.VERTICAL, false)) ;

        getMyReviews();//db에서 리뷰정보 불러오기

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //뒤로가기, 인텐트 종료
                finish();
            }
        });
    }

    public void getMyReviews() {//db에서 영화정보를 가져오는 메서드
        //db에서 나의 리뷰 가져옴
        databaseReference.child(user.getUid()).child("Review").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()){
                    HashMap<String,Object> reviewMap= (HashMap<String, Object>) s.getValue();

                    String reviewMovieTitle = (String) reviewMap.get("mtitle");
                    String date=(String) reviewMap.get("mdate");
                    String reviewTitle= (String) reviewMap.get("rtitle");
                    String review=(String)reviewMap.get("rcontent");
                    MyReview item=new MyReview(reviewMovieTitle,date,reviewTitle,review);
                    myReviews.add(item);//리뷰리스트에 아이템 저장
                }
                setMyReviews();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setMyReviews(){//아이템 추가 메서드
        for (int i=0;i<myReviews.size();i++){
            adapter.setMyReviewList(myReviews.get(i));
        }
        recyclerView.setAdapter(adapter);
    }
}
