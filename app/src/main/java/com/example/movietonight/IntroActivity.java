package com.example.movietonight;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

public class IntroActivity extends AppCompatActivity {

    private ImageView intro_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        intro_view = findViewById(R.id.introView);

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        };
        handler.postDelayed(runnable, 4000);
        //앱 접속 후 4초 시간 지나면 자동으로 로그인 화면으로 전환

        intro_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //4초 시간 안지나도 터치하면 자동으로 로그인 화면으로 전환
    }
}