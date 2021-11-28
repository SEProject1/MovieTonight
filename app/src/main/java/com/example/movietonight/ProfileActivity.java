package com.example.movietonight;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import android.util.Base64;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference("UserAccount");
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    private EditText nickname;
    private CircleImageView profile_img;
    private ImageButton back;
    private Button btn_profile, btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nickname = findViewById(R.id.et_nickname);
        profile_img = findViewById(R.id.img_profile);
        back = findViewById(R.id.btnBack);
        btn_profile = findViewById(R.id.btn_profile);
        btn_register=findViewById(R.id.btn_register);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(nickname.getText().toString().equals(""))){//변경된 닉네임이 있다면
                    changeNickName();
                }
                else{
                    System.out.println("변경닉네임 없음");
                }
            }
        });
    }
    public void changeNickName(){
        HashMap<String,Object> nickNameUpdate=new HashMap<String,Object>();
        nickNameUpdate.put("userNickname",nickname.getText().toString());
        databaseReference.child(user.getUid()).updateChildren(nickNameUpdate);//현재 유저db에서 닉네임 수정

        //이유저를 팔로잉 중인 유저의 팔로잉 목록에서 수정
        databaseReference.child(user.getUid()).child("follower").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:snapshot.getChildren()){
                    HashMap<String,Object>followerMap=(HashMap<String, Object>) s.getValue();
                    //이 유저를 팔로잉 하는 팔로워 목록을 해시맵으로 부른다
                    String idToken=(String) followerMap.get("idToken");//이 유저 팔로잉 중인 유저 idtoken
                    databaseReference.child(idToken).child("following").child(user.getUid()).updateChildren(nickNameUpdate);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //이유저가 팔로우 중인 유저의 팔로워 목록에서 수정
        databaseReference.child(user.getUid()).child("following").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:snapshot.getChildren()){
                    HashMap<String,Object>followingMap=(HashMap<String, Object>) s.getValue();
                    //이 유저가 팔로잉 하는 팔로잉 목록을 해시맵으로 부른다
                    String idToken=(String) followingMap.get("idToken");//이 유저가 팔로잉 중인 유저 idtoken
                    databaseReference.child(idToken).child("follower").child(user.getUid()).updateChildren(nickNameUpdate);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
