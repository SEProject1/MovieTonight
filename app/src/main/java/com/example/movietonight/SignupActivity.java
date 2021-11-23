package com.example.movietonight;

import static android.widget.Toast.makeText;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movietonight.Class.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private EditText mEtId, mEtNickname, mEtPw;
    private Button mBtnSignup, idcheck;
    private Boolean check = false;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("MovieTonight");
        idcheck = findViewById(R.id.btn_overlap);
        mEtId = findViewById(R.id.et_id);
        mEtNickname = findViewById(R.id.et_nickname);
        mEtPw = findViewById(R.id.et_pw);
        mBtnSignup = findViewById(R.id.btn_signup_signup);
        progressDialog = new ProgressDialog(this);
        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 처리 시작
                String userId = mEtId.getText().toString().trim();
                String userNickname = mEtNickname.getText().toString().trim();
                String passwd = mEtPw.getText().toString().trim();

                if (!userId.matches(emailPattern)){
                    mEtId.setError("이메일 형식에 맞지 않습니다.");
                } else if (userNickname.isEmpty()){
                    mEtNickname.setError("닉네임을 입력해주세요.");
                } else if (passwd.isEmpty() || passwd.length() < 6){
                    mEtPw.setError("적절한 비밀번호를 입력해주세요.");
                } else {
                    progressDialog.setMessage("회원가입중입니다!");
                    progressDialog.setTitle("회원가입");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                }

                //Firebase Auth 진행
                mFirebaseAuth.createUserWithEmailAndPassword(userId, passwd).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()&& check) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setUserId(firebaseUser.getEmail());
                            account.setUserNickname(userNickname);
                            account.setPasswd(passwd);
                            //account.setImageurl("ic_baseline_profile_24");

                            //setValue : database에 삽입 동작
                            mDatabaseRef.child(firebaseUser.getUid()).setValue(account);

                            progressDialog.dismiss();
                            Toast.makeText(SignupActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                            //메인으로 이동
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SignupActivity.this, "회원가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        idcheck.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                    String email = mEtId.getText().toString().trim();
                    arrayList.clear();
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("UserAccount");
                    mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                            for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                                UserAccount account = snapshot.getValue(UserAccount.class);
                                arrayList.add(account.getUserId());
                            }
                            if(arrayList.contains(email)){
                                Toast.makeText(SignupActivity.this, "중복된 이메일입니다.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(SignupActivity.this, "사용가능한 이메일입니다", Toast.LENGTH_SHORT).show();
                                check=true;
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
            }
        });

        ImageView img_backmove = findViewById(R.id.img_backmove);
        img_backmove.setOnClickListener(view ->
                startActivity(new Intent(SignupActivity.this, StartActivity.class)));

    }

}