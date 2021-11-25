package com.example.movietonight;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.google.firebase.database.collection.LLRBNode;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;


public class CalendarActivity extends AppCompatActivity{
    private MaterialCalendarView calendarView;
    private TextView tvNone;
    private ImageButton btnBackMyReview;
    private HashMap<String,ArrayList<MyReview>> myReviews=new HashMap<String,ArrayList <MyReview>>();//리뷰목록을 저장
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference("UserAccount");
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    private HashSet<String> decotterDates=new HashSet<String>();
    private RecyclerView recyclerView;
    private MyReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        btnBackMyReview=findViewById(R.id.btnBackMyReview);
        tvNone=findViewById(R.id.tvNone);
        calendarView=findViewById(R.id.calendarView);
        recyclerView=(RecyclerView)findViewById(R.id.calReviewRecycler);
        adapter=new MyReviewAdapter();
        adapter.setDelBtnGone();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        getMyReviews();
        btnBackMyReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void getMyReviews() {//db에서 영화정보를 가져오는 메서드
        //db에서 나의 리뷰 가져옴
        databaseReference.child(user.getUid()).child("Review").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()){
                    HashMap<String,Object> reviewMap= (HashMap<String, Object>) s.getValue();
                    String reviewMovieTitle = (String) reviewMap.get("mtitle");
                    String date=(String) reviewMap.get("mdate");
                    String reviewTitle= (String) reviewMap.get("rtitle");
                    String review=(String)reviewMap.get("rcontent");
                    MyReview item=new MyReview(reviewMovieTitle,date,reviewTitle,review);
                    if(myReviews.containsKey(date)){
                        myReviews.get(date).add(item);
                    }
                    else{
                        ArrayList<MyReview> tmp=new ArrayList<>();
                        tmp.add(item);
                        myReviews.put(date,tmp);
                    }
                    decotterDates.add(date);
                }
                setDateChangeListener();
                setDeco();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setDeco(){
        calendarView.addDecorator(new DateDecorator(decotterDates));
    }
    public void setDateChangeListener(){
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
                String selectedDate=dateFormat.format(date.getDate());
                setReviews(selectedDate);
            }
        });

    }
    public void setReviews(String date){
        if(myReviews.containsKey(date)){
            adapter.clearMyReviewList();
            tvNone.setVisibility(View.GONE);
            ArrayList<MyReview> dayReview=myReviews.get(date);
            for(int i=0;i<dayReview.size();i++){
                adapter.setMyReviewList(dayReview.get(i));
            }
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter);
        }else{
            recyclerView.setVisibility(View.GONE);
            tvNone.setVisibility(View.VISIBLE);
        }
    }
}