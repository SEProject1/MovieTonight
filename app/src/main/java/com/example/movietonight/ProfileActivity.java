package com.example.movietonight;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class ProfileActivity extends AppCompatActivity {

    EditText etNickname;
    final int MIN_PASSWORD_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        viewInitializations();
    }

    void viewInitializations() {
        etNickname = findViewById(R.id.et_nickname);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    boolean validateInput() {
        if (etNickname.getText().toString().equals("")) {
            etNickname.setError("닉네임은 비워둘 수 없습니다.");
            return false;
        }
    }


    public void performEditProfile (View v) {
        if (validateInput()) {


            String Nickname = etNickname.getText().toString();

            Toast.makeText(this,"Profile Update Successfully",Toast.LENGTH_SHORT).show();

        }
    }

}
}
