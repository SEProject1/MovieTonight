package com.example.movietonight;

import android.content.Intent;
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

public class FollowingListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView tvNoFollowing;
    private FollowingListAdapter adapter;
    private ImageButton btnBack;
    private ArrayList<FollowingList> myFollowingList=new ArrayList<FollowingList>();//팔로잉 목록 저장
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference("UserAccount");
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.following_list_recycler);
        tvNoFollowing=findViewById(R.id.tvNoFollowing);
        recyclerView = (RecyclerView)findViewById(R.id.following_list_recycler);
        btnBack=(ImageButton)findViewById(R.id.btnBackMyReview);//뒤로가기 버튼
        adapter = new FollowingListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager
                (this, RecyclerView.VERTICAL, false)) ;

        getMyFollowingList();//db에서 팔로잉정보 가져오기

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FollowingListActivity.this, MainActivity.class);
                intent.putExtra("mypage",true);
                startActivity(intent);
                finish();
            }
        });
    }

    public void getMyFollowingList() {//db에서 팔로잉 정보 가져옴
        databaseReference.child(user.getUid()).child("following").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:snapshot.getChildren()){
                    HashMap<String,Object>followingMap=(HashMap<String, Object>) s.getValue();
                    String nickName=(String) followingMap.get("userNickname");
                    String idToken=(String)followingMap.get("idToken");
                    FollowingList item=new FollowingList(nickName,idToken);
                    myFollowingList.add(item);
                }
                setMyFollowingList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setMyFollowingList(){//아이템 추가 메서드
        if(myFollowingList.size()==0){
            recyclerView.setVisibility(View.GONE);
            tvNoFollowing.setVisibility(View.VISIBLE);
        }else{
            for (int i=0;i<myFollowingList.size();i++){
                adapter.setMyFollowingList(myFollowingList.get(i));
            }
            recyclerView.setAdapter(adapter);
        }
    }
}
