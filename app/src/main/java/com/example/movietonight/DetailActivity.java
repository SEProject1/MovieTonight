package com.example.movietonight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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
    ArrayList arrayList = new ArrayList();
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

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserAccount");
        firebaseAuth=FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        TextView textView_title = (TextView)findViewById(R.id.tv_title);
        textView_title.setText(title);
        TextView textView_original_title = (TextView)findViewById(R.id.tv_original_title);
        textView_original_title.setText(original_title);
        ImageView imageView_poster = (ImageView) findViewById(R.id.iv_poster);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+poster_path)
                .centerCrop()
                .crossFade()
                .into(imageView_poster);

        TextView textView_overview = (TextView)findViewById(R.id.tv_overview);
        textView_overview.setText(overview);
        TextView textView_release_date = (TextView)findViewById(R.id.tv_release_date);
        textView_release_date.setText(release_date);
        Button btn_review = findViewById(R.id.btn_review);
        RatingBar ratingBar = findViewById(R.id.movie_rating);
        btn_save = findViewById(R.id.btn_save);

        ratingBar.setRating(((float)Double.parseDouble(vote_average)/2));

        databaseReference.child(user.getUid()).child("save").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    String m_name = dataSnapshot.getValue().toString();
                    if(m_name.equals(title)){
                        btn_save.setSelected(true);
                    }
                    arrayList.add(m_name);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, ReviewActivity.class);
                intent.putExtra("title",title);
                startActivity(intent);
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {    //save버튼 눌렀을 때 버튼 변화
            @Override
            public void onClick(View v) {
                if(arrayList.contains(title)){
                    databaseReference.child(user.getUid()).child("save").child(title).removeValue();
                    arrayList.remove(title);
                }else{  //save에 추가
                    databaseReference.child(user.getUid()).child("save").child(title).setValue(title);
                    arrayList.add(title);
                }
                btn_save.setSelected(!btn_save.isSelected());
            }
        });
    }
}