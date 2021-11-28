package com.example.movietonight;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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

public class SavedActivity extends AppCompatActivity {//찜한 리스트를 아이템으로 넣고 화면에 보여주는 클래스
    private RecyclerView recyclerView;
    private FavoriteListAdapter adapter;
    private TextView tvNoSave;
    private ImageButton btnBack;
    private ArrayList<FavoriteMovie> favoriteMovies=new ArrayList<FavoriteMovie>();
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference("UserAccount");
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_list_recycler);
        recyclerView = (RecyclerView)findViewById(R.id.favoriteMovie_recycler);
        btnBack=(ImageButton)findViewById(R.id.btnBack);//뒤로가기 버튼
        tvNoSave=findViewById(R.id.tvNoSave);
        adapter = new FavoriteListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager
                (this, RecyclerView.VERTICAL, false)) ;
        favoriteMovies();//db에서 찜목록을 가져옴
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //뒤로가기, 인텐트 종료
                finish();
            }
        });
    }
    public void favoriteMovies() {//db에서 찜영화 가져오는 메서드
        //db에서 찜 영화 가져옴
        databaseReference.child(user.getUid()).child("save").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()){
                    FavoriteMovie item=new FavoriteMovie((String) s.getValue());
                    favoriteMovies.add(item);
                }
                setFavoriteMovies();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setFavoriteMovies(){//아이템 추가 메서드
        if (favoriteMovies.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            tvNoSave.setVisibility(View.VISIBLE);
        }else{
            for (int i=0;i<favoriteMovies.size();i++){
                adapter.setFavoriteMovieData(favoriteMovies.get(i));
            }
            recyclerView.setAdapter(adapter);
        }

    }
}
