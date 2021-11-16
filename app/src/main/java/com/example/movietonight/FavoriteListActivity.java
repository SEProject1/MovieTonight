package com.example.movietonight;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteListActivity extends AppCompatActivity {//찜한 리스트를 아이템으로 넣고 화면에 보여주는 클래스
    RecyclerView recyclerView;
    FavoriteListAdapter adapter;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_list_recycler);
        recyclerView = (RecyclerView)findViewById(R.id.favoriteMovie_recycler);
        btnBack=(ImageButton)findViewById(R.id.btnBack);//뒤로가기 버튼

        recyclerView.setLayoutManager(new LinearLayoutManager
                (this, RecyclerView.VERTICAL, false)) ;
        adapter = new FavoriteListAdapter();
        for (int i = 0; i < 10; i++) {//이곳에 db에서 찜목록 가져와 영화이름 넣기.
            String str = i + "번영화";
            FavoriteMovie item=new FavoriteMovie(str);
            adapter.setFavoriteMovieData(item);
        }
        recyclerView.setAdapter(adapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //뒤로가기, 인텐트 종료
                //finish();
            }
        });
    }
}
