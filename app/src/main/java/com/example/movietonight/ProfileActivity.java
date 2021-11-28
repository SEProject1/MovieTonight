package com.example.movietonight;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

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

import com.bumptech.glide.Glide;
import com.example.movietonight.Fragment.FragMypage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileActivity extends FragmentActivity {
    private Uri Uuri;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://movietonight-78dfc.appspot.com");
    private StorageReference storageReference = storage.getReference();
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference("UserAccount");
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    private EditText nickname;
    private CircleImageView profile_img;
    private ImageButton back;
    private Button btn_profile, btn_register;
    String uuid = firebaseUser.getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nickname = findViewById(R.id.et_nickname);
        profile_img = findViewById(R.id.img_profile);
        back = findViewById(R.id.btnBack);
        btn_profile = findViewById(R.id.btn_profile);
        btn_register=findViewById(R.id.btn_register);

        getprofile();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                launcher.launch(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(nickname.getText().toString().equals(""))){//변경된 닉네임이 있다면
                    changeNickName();
                    changeProfile();
                    Toast.makeText(ProfileActivity.this, "변경사항이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
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
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()==RESULT_OK)
                    {
                        Intent intent = result.getData();
                        Uuri = intent.getData();
                        profile_img.setImageURI(Uuri);
                        Glide.with(getApplicationContext()).load(Uuri).into(profile_img);
                    }
                }
            });

    void getprofile(){
        storageReference.child(uuid).child("profileimg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Uuri = uri;
                Glide.with(getApplicationContext()).load(Uuri).into(profile_img);
            }
        });
    }

    void changeProfile(){
        storageReference = storage.getReference().child(user.getUid()).child("profileimg");
        UploadTask uploadTask = storageReference.putFile(Uuri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this,"프로필 변경에 실패했습니다.",  Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(ProfileActivity.this, "프로필이 정상적으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}