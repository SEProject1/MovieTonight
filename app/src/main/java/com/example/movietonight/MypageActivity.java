package com.example.movietonight;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MyPageActivity extends AppCompatActivity {

    Button logout;
    Button saved;
    Button profile;
    Button review;
    Button ranking;
    Button calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        saved=findViewById(R.id.btn_saved);
        profile=findViewById(R.id.btn_profile);
        review=findViewById(R.id.btn_review);
        ranking=findViewById(R.id.btn_ranking);
        calendar=findViewById(R.id.btn_ranking);
        logout=findViewById(R.id.btn_logout);

        logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LogoutActivity = new Intent(getApplicationContext(),LogoutActivity.class);
            }
        });

        saved.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SavedActivity = new Intent(getApplicationContext(), SavedActivity.class); }
        });
        profile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ProfileActivity = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(ProfileActivity);
            }
        });
        review.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ReviewActivity=new Intent(getApplicationContext(),MyReviewActivity.class);
            }
        });
        ranking.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RankingActivity=new Intent(getApplicationContext(),RankingActivity.class);
            }
        });
        calendar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CalendarActivity=new Intent(getApplicationContext(),CalendarActivity.class);
            }
        });

    }
