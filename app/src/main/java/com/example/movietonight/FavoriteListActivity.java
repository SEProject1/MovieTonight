package com.example.movietonight;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoriteListActivity extends AppCompatActivity {//찜한 리스트를 아이템으로 넣고 화면에 보여주는 클래스
    RecyclerView recyclerView;
    FavoriteListAdapter adapter;
    ImageButton btnBack;
    ArrayList<FavoriteMovie> favoriteMovies=new ArrayList<FavoriteMovie>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_list_recycler);
        recyclerView = (RecyclerView)findViewById(R.id.favoriteMovie_recycler);
        btnBack=(ImageButton)findViewById(R.id.btnBack);//뒤로가기 버튼

        recyclerView.setLayoutManager(new LinearLayoutManager
                (this, RecyclerView.VERTICAL, false)) ;
        adapter = new FavoriteListAdapter();

        favoriteMovies();//db에서 찜목록을 가져옴
        setFavoriteMovies();//item추가
        recyclerView.setAdapter(adapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //뒤로가기, 인텐트 종료
                //finish();
            }
        });
    }
    public void favoriteMovies() {//db에서 찜영화 가져오는 메서드
        //db에서 찜 영화 가져옴
        for (int i = 0; i < 10; i++) {
            String str = i + "번영화";
            FavoriteMovie item=new FavoriteMovie(str);
            favoriteMovies.add(item);//아이템을 리스트에 넣기
        }
    }
    public void setFavoriteMovies(){//아이템 추가 메서드
        for (int i=0;i<favoriteMovies.size();i++){
            adapter.setFavoriteMovieData(favoriteMovies.get(i));
        }
    }
}
