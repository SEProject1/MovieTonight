package com.example.movietonight;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;


public class ProfileActivity extends AppCompatActivity {

    EditText etNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        viewInitializations();
    }


    void viewInitializations() {
        etNickname = findViewById(R.id.et_nickname);
    }

    boolean validateInput() {
        if (etNickname.getText().toString().equals("")) {
            etNickname.setError("닉네임은 비워둘 수 없습니다.");
            return false;
        }
        return true;
    }

    public void onClick(View view)
    {
        Intent intent= new Intent(ProfileActivity.this,UploadActivity.class);
        startActivity(intent);
        finish();
    }

    public void performEditProfile (View v) {
        if (validateInput()) {

            String Nickname = etNickname.getText().toString();

            Toast.makeText(this,"프로필 수정 완료",Toast.LENGTH_SHORT).show();
            //DB연결하는부분
        }
    }

}
