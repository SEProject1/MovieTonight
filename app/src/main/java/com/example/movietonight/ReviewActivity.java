package com.example.movietonight;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ReviewActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR,year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            setDate();
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Intent mintent = getIntent();
        String title = mintent.getExtras().getString("title");
        EditText et_title = findViewById(R.id.et_title);
        EditText et_content = findViewById(R.id.et_content);
        Button btn_reg = findViewById(R.id.btn_reg);
        TextView m_title = findViewById(R.id.m_title);
        TextView m_genre = findViewById(R.id.genre);
        m_title.setText(title);
        Button date = findViewById(R.id.date);
        ProgressDialog progressDialog = new ProgressDialog(this);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ReviewActivity.this, myDate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rtitle = et_title.getText().toString();
                String rcontent = et_content.getText().toString();
                String mdate = date.getText().toString();
                String mgenre = m_genre.getText().toString();

                if(rtitle.equals("")||rcontent.equals("")){
                    Toast.makeText(ReviewActivity.this, "제목 또는 내용을 작성해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.setMessage("리뷰를 작성중입니다");
                    progressDialog.setTitle("리뷰");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference("UserAccount");
                    firebaseAuth=FirebaseAuth.getInstance();
                    user = firebaseAuth.getCurrentUser();
                    Review review = new Review(title, rtitle, mgenre, rcontent, 0, 0, mdate, user.getEmail(), user.getDisplayName());
                    databaseReference.child(user.getUid()).child("Review").child(title).setValue(review);
                    progressDialog.dismiss();
                    Toast.makeText(ReviewActivity.this, "리뷰가 작성되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReviewActivity.this, MainActivity.class);
                    startActivity(intent);
                    /*review.setDislike(0);
                    review.setLike(0);
                    review.setEmail(user.getEmail());
                    review.setMdate(mdate);
                    review.setMgenre(mgenre);
                    review.setMtitle(title);
                    review.setRtitle(rtitle);
                    review.setRcontent(rcontent);*/
                    finish();
                }
            }
        });
    }

    private void setDate(){
        String Date = "yyyy/MM/dd";
        SimpleDateFormat format = new SimpleDateFormat(Date, Locale.KOREA);
        Button date = findViewById(R.id.date);
        date.setText(format.format(myCalendar.getTime()));
    }
}
