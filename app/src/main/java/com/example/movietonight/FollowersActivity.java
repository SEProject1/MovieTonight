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

public class FollowersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FollowerAdapter adapter;
    private ImageButton btnBack;
    private TextView tvNoFollwer;
    private ArrayList<FollowingList> myFollowerList = new ArrayList<FollowingList>();//팔로워 목록 저장
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("UserAccount");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follower_recycler);
        tvNoFollwer = findViewById(R.id.tvNoFollower);
        recyclerView = (RecyclerView) findViewById(R.id.follower_list_recycler);
        btnBack = (ImageButton) findViewById(R.id.btnBackMyReview);//뒤로가기 버튼
        adapter = new FollowerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager
                (this, RecyclerView.VERTICAL, false));
        getMyFollowerList();//db에서 팔로워정보 가져오기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //뒤로가기, 인텐트 종료
                finish();
            }
        });
    }

    public void getMyFollowerList() {//db에서 팔로워 정보 가져옴
        databaseReference.child(user.getUid()).child("follower").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    HashMap<String, Object> followerMap = (HashMap<String, Object>) s.getValue();
                    String nickName = (String) followerMap.get("userNickname");
                    String idToken = (String) followerMap.get("idToken");
                    FollowingList item = new FollowingList(nickName, idToken);
                    myFollowerList.add(item);
                }
                setMyFollowerList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void setMyFollowerList() {//아이템 추가 메서드
        for (int i = 0; i < myFollowerList.size(); i++) {
            adapter.setMyFollowerList(myFollowerList.get(i));
            if (myFollowerList.size() == 0) {
                tvNoFollwer.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                for (int j = 0; j < myFollowerList.size(); j++) {
                    adapter.setMyFollowerList(myFollowerList.get(j));
                }
                recyclerView.setAdapter(adapter);
            }
            recyclerView.setAdapter(adapter);

        }


    }
}