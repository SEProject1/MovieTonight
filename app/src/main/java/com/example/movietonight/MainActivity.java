package com.example.movietonight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.movietonight.Fragment.FragFeed;
import com.example.movietonight.Fragment.FragMain;
import com.example.movietonight.Fragment.FragMypage;
import com.example.movietonight.Fragment.FragNoti;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FragMain fragMain;
    private FragFeed fragFeed;
    private FragNoti fragNotification;
    private FragMypage fragMypage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        setFrag(0);
                        break;
                    case R.id.action_feed:
                        setFrag(1);
                        break;
                    case R.id.action_notification:
                        setFrag(2);
                        break;
                    case R.id.action_mypage:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });
        fragMain = new FragMain();
        fragFeed = new FragFeed();
        fragNotification = new FragNoti();
        fragMypage = new FragMypage();
        if(intent.getBooleanExtra("mypage",false)==true){
            setFrag(3);//닉네임변경, 언팔로우시 프래그먼트 mypage로
            bottomNavigationView.setSelectedItemId(R.id.action_mypage);//하단 아이콘 설정
        }
        else{
            setFrag(0); //첫 프래그먼트 화면을 무엇으로 지정해줄 것인지 선택
        }
    }


    // 프래그먼터 교체가 일어나는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0 :
                ft.replace(R.id.main_frame, fragMain);
                ft.commit();
                break;
            case 1 :
                ft.replace(R.id.main_frame, fragFeed);
                ft.commit();
                break;
            case 2 :
                ft.replace(R.id.main_frame, fragNotification);
                ft.commit();
                break;
            case 3 :
                ft.replace(R.id.main_frame, fragMypage);
                ft.commit();
                break;

        }

    }
}
