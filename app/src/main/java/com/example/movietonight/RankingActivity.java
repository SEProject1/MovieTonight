package com.example.movietonight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.animation.Easing;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RankingActivity extends AppCompatActivity {
   // static ArrayList<String> list = new ArrayList<String>();
    static String[] Ranking_Genre=new String[16];
    static int[] occurrence=new int[16];
    PieChart pieChart;
    private ImageButton btn_backRanking;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("UserAccount");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        btn_backRanking=findViewById(R.id.btn_backRanking);
        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.BLACK);
        pieChart.setTransparentCircleRadius(61f);
        getMyMovie(); //db에서 장르 불러오기
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
        for(int k=0;k<occurrence.length;k++) {
            yValues.add(new PieEntry(occurrence[k], Ranking_Genre[k]));
        }
        Description description = new Description();
        description.setText("내가 본 장르"); //라벨
        description.setTextSize(15);
        pieChart.setDescription(description);

        pieChart.animateY(1000, Easing.EaseInOutCubic); //애니메이션

        PieDataSet dataSet = new PieDataSet(yValues, "Genre");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        pieChart.setData(data);
        btn_backRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //DB에서 영화가져오기
    public void getMyMovie() {
        ArrayList<String> list = new ArrayList<String>();
        String[] Genre=new String[16];
        databaseReference.child(user.getUid()).child("Review").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) { //DataSnapshot s : snapshot.getChildren()
                    HashMap<String, Object> reviewMap = (HashMap<String, Object>) s.getValue();
                    String mGenre = (String) reviewMap.get("mgenre"); //파이어베이스에서 장르 받아오기
                    String[] Genre = mGenre.split(" "); //장르가 스페이스바로 여러개 분리되어있으니 개별 카운트를 위해 분리
                   for (int i = 0; i < Genre.length; i++) {
                        list.add(Genre[i]); // 분리한장르를 list에 추가
                        //system out, logd 찍어보기

                    }
                     countFrequncies(list); //카운트함수 실행
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    //참고코드 https://www.geeksforgeeks.org/count-occurrences-elements-list-java/?ref=gcse
    public static void countFrequncies(ArrayList<String> list) //장르 카운트
    {
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (String i : list) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }
        int n=0;
        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            Ranking_Genre[n]= val.getKey(); // key값에 장르이름 들어간것 추출
            occurrence[n]=val.getValue(); //Value값에 카운트된 횟수 추출
            n++;
        }

    }
}
