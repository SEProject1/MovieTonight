package com.example.movietonight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ImageView img_backmove;
    private EditText id;
    private EditText passwd;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        img_backmove = findViewById(R.id.img_backmove);
        id = findViewById(R.id.et_id);
        passwd = findViewById(R.id.et_passwd);
        signup = findViewById(R.id.btn_signup_signup);
        img_backmove.setOnClickListener(view ->
                startActivity(new Intent(LoginActivity.this, StartActivity.class)));
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }
    private void login(){
        String email = id.getText().toString().trim();
        String pw = passwd.getText().toString().trim();
        if(email.equals("")||pw.equals("")){
            Toast.makeText(LoginActivity.this,"모두 작성해주세요",Toast.LENGTH_SHORT).show();
        }else{
            firebaseAuth.signInWithEmailAndPassword(email,pw).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "로그인 완료", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,FragmentActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"이메일이나 비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}