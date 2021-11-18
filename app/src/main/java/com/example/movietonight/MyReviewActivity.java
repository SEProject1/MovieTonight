package com.example.movietonight;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyReviewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyReviewAdapter adapter;
    ImageButton btnBack;
    ArrayList<MyReview> myReviews=new ArrayList<MyReview>();//리뷰목록을 저장

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myreview_recycler);
        recyclerView = (RecyclerView)findViewById(R.id.myReivew_recycler);
        btnBack=(ImageButton)findViewById(R.id.btnBackMyReview);//뒤로가기 버튼

        recyclerView.setLayoutManager(new LinearLayoutManager
                (this, RecyclerView.VERTICAL, false)) ;
        adapter = new MyReviewAdapter();
        getMyReviews();//db에서 리뷰정보 불러오기
        recyclerView.setAdapter(adapter);
        setMyReviews();//리사이클러뷰에 아이템추가
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //뒤로가기, 인텐트 종료
                finish();
            }
        });
    }

    public void getMyReviews() {//db에서 영화정보를 가져오는 메서드
        for (int i = 0; i < 10; i++) {
            String reviewMovieTitle = i + "번영화";
            String date="2021.11."+i;
            String reviewTitle=i+"번 제목";
            MyReview item=new MyReview(reviewMovieTitle,date,reviewTitle);
            myReviews.add(item);//리뷰리스트에 아이템 저장
        }
    }
    public void setMyReviews(){//아이템 추가 메서드
        for (int i=0;i<myReviews.size();i++){
            adapter.setMyReviewList(myReviews.get(i));
        }
    }
}
