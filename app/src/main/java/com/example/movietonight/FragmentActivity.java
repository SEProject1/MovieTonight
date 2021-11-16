package com.example.movietonight;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentActivity extends AppCompatActivity {
    ImageButton main, feed, noti, mypage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        main = findViewById(R.id.img_home);
        feed = findViewById(R.id.img_feed);
        noti = findViewById(R.id.img_noti);
        mypage = findViewById(R.id.img_mypage);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                MainActivity main = new MainActivity();
                transaction.replace(R.id.frame, main);
                //transaction.replace(R.id.frame, );
                transaction.commit();
            }
        });
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                FeedActivity feed = new FeedActivity();
                transaction.replace(R.id.frame, feed);
                transaction.commit();
            }
        });
        noti.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                NotiActivity noti = new NotiActivity();
                transaction.replace(R.id.frame, noti);
                transaction.commit();
            }
        });
        mypage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                MypageActivity mypage = new MypageActivity();
                transaction.replace(R.id.frame, mypage);
                transaction.commit();
            }
        });
    }
}
