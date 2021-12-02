package com.example.movietonight;

import static android.widget.Toast.makeText;

import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.movietonight.Class.UserAccount;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {
    Uri Uuri;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private EditText mEtId, mEtNickname, mEtPw;
    private Button mBtnSignup, idcheck, profile;
    private ImageView view_pro;
    private Boolean check = false;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    ArrayList<String> arrayList = new ArrayList<>();
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://movietonight-78dfc.appspot.com");
    StorageReference storageReference = storage.getReference();

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
        view_pro = findViewById(R.id.view_profile);
        profile = findViewById(R.id.profile);
        progressDialog = new ProgressDialog(this);

        storageReference.child("profileimg/profile_pic.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Uuri = uri;
                Glide.with(getApplicationContext()).load(uri).into(view_pro);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"이미지 로딩에 실패하였습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        profile.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            launcher.launch(intent);
        });

        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 처리 시작
                String userId = mEtId.getText().toString().trim();
                String userNickname = mEtNickname.getText().toString().trim();
                String passwd = mEtPw.getText().toString().trim();

                if (userNickname.equals("")||userId.equals("")||passwd.equals("")){
                    Toast.makeText(SignupActivity.this, "입력하지 않은 항목이 있습니다.", Toast.LENGTH_SHORT).show();
                } else if (!userId.matches(emailPattern)){
                    mEtId.setError("이메일 형식에 맞지 않습니다.");
                } else if (passwd.length() < 6){
                    mEtPw.setError("비밀번호는 6자리 이상이어야 합니다.");
                } else if(!check){
                    Toast.makeText(SignupActivity.this, "이메일 중복확인이 필요합니다.", Toast.LENGTH_SHORT).show();
                } else{
                    progressDialog.setMessage("회원가입중입니다!");
                    progressDialog.setTitle("회원가입");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    signup(userId, passwd, userNickname);
                }
            }
        });

        idcheck.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String email = mEtId.getText().toString().trim();
                String userId = mEtId.getText().toString().trim();
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

                if (userId.equals("")){
                    Toast.makeText(SignupActivity.this, "입력하지 않은 항목이 있습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView img_backmove = findViewById(R.id.img_backmove);
        img_backmove.setOnClickListener(view ->
                startActivity(new Intent(SignupActivity.this, StartActivity.class)));

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
                        view_pro.setImageURI(Uuri);
                        Glide.with(getApplicationContext()).load(Uuri).into(view_pro);
                    }
                }
            });
    void signup(String userId, String passwd, String userNickname){
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
                    storageReference = storageReference.child(firebaseUser.getUid()).child("profileimg");
                    UploadTask uploadTask = storageReference.putFile(Uuri);

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignupActivity.this, "프로필이 기본 이미지로 설정됩니다.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });

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
}