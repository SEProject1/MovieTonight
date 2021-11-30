package com.example.movietonight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {
    ImageButton btn_save;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    String genre_list = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String original_title = intent.getStringExtra("original_title");
        String poster_path = intent.getStringExtra("poster_path");
        String overview = intent.getStringExtra("overview");
        String release_date = intent.getStringExtra("release_date");
        String vote_average = intent.getStringExtra("vote_average");
        ArrayList<Integer> genre_ids = intent.getIntegerArrayListExtra("genre_ids");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserAccount");
        firebaseAuth=FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        // 포스터
        ImageView imageView_poster = (ImageView) findViewById(R.id.iv_poster);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+poster_path)
                .centerCrop()
                .crossFade()
                .into(imageView_poster);
        // 제목
        TextView textView_title = (TextView)findViewById(R.id.tv_title);
        textView_title.setText(title);
        // 원제
        TextView textView_original_title = (TextView)findViewById(R.id.tv_original_title);
        textView_original_title.setText(original_title);
        // 개봉일
        TextView textView_release_date = (TextView)findViewById(R.id.tv_release_date);
        textView_release_date.setText(release_date);
        // 별점
        RatingBar ratingBar = findViewById(R.id.movie_rating);
        ratingBar.setRating(((float)Double.parseDouble(vote_average)/2));
        // 장르
        HashMap<Integer, String> map = new HashMap<>(18);
        map.put(12,"모험 ");map.put(14,"판타지 ");map.put(16,"애니메이션 ");map.put(18,"드라마 ");map.put(27,"공포 ");map.put(28,"액션 ");map.put(35,"코미디 ");map.put(36,"역사 ");map.put(37,"서부 ");
        map.put(53,"스릴러 ");map.put(80,"범죄 ");map.put(99,"다큐멘터리 ");map.put(9648,"미스테리 ");map.put(878,"SF ");map.put(10402,"음악 ");map.put(10749,"로맨스 ");map.put(10751,"가족 ");map.put(10752,"전쟁 ");map.put(10770,"TV Movie ");
        TextView textView_genre_ids = (TextView)findViewById(R.id.tv_genre_ids);
        // ArrayList의 Integer가 map을 통해 String으로 변환후 모아서 setText
        for(int i=0; i<genre_ids.size(); i++)
            genre_list += map.get((genre_ids.get(i)));
        textView_genre_ids.setText(genre_list);
        // 줄거리
        TextView textView_overview = (TextView)findViewById(R.id.tv_overview);
        textView_overview.setText(overview);
        // 리뷰버튼, 찜 버튼
        Button btn_review = findViewById(R.id.btn_review);
        btn_save = findViewById(R.id.btn_save);

        databaseReference.child(user.getUid()).child("save").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    String m_name = dataSnapshot.getValue().toString();
                    if(m_name.equals(title)){
                        btn_save.setSelected(true);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        btn_review.setOnClickListener(new View.OnClickListener() {  // 리뷰버튼 선택시
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, ReviewActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("genre_list", genre_list);
                startActivity(intent);
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {    // 찜버튼 선택시
            @Override
            public void onClick(View v) {
                if(!btn_save.isSelected()){
                    databaseReference.child(user.getUid()).child("save").child(title).setValue(title);
                    Toast.makeText(DetailActivity.this, "해당 영화가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    btn_save.setSelected(true);
                }

            }
        });
    }
}