package com.example.movietonight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
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

import android.content.Intent;
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
    static String[] Ranking_Genre;
    static int[] occurrence;
    PieChart pieChart;
    private ImageButton btn_backRanking;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("UserAccount");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ArrayList<PieEntry> yValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ranking_Genre=new String[16];
        occurrence=new int[16];
        setContentView(R.layout.activity_ranking);
        btn_backRanking=findViewById(R.id.btn_backRanking);
        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setRotationEnabled(false);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.BLACK);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setTransparentCircleRadius(61f);
        getMyMovie(); //db?????? ?????? ????????????
        yValues = new ArrayList<PieEntry>();
        pieChart.animateY(1000, Easing.EaseInOutCubic); //???????????????

        Description description = new Description();
        description.setText("?????? ??? ??????"); //??????
        description.setTextSize(15);
        pieChart.setDescription(description);
        btn_backRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //DB?????? ??????????????????
    public void getMyMovie() {
        ArrayList<String> list = new ArrayList<String>();
        String[] Genre=new String[16];
        databaseReference.child(user.getUid()).child("Review").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) { //DataSnapshot s : snapshot.getChildren()
                    HashMap<String, Object> reviewMap = (HashMap<String, Object>) s.getValue();
                    String mGenre = (String) reviewMap.get("mgenre"); //???????????????????????? ?????? ????????????
                    String[] Genre = mGenre.split(" "); //????????? ?????????????????? ????????? ????????????????????? ?????? ???????????? ?????? ??????
                    for (int i = 0; i < Genre.length; i++) {
                        list.add(Genre[i]); // ?????????????????? list??? ??????
                    }
                }
                countFrequncies(list); //???????????? ?????? ????????? ??? ?????? ??????????????? ??????
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void countFrequncies(ArrayList<String> list) //?????? ?????????
    {
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (String i : list) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }
        int n=0;
        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            Ranking_Genre[n]= val.getKey(); // key?????? ???????????? ???????????? ??????
            occurrence[n]=val.getValue(); //Value?????? ???????????? ?????? ??????
            n++;
        }
        setRanking();
    }
    public void setRanking(){
        for(int k=0;k<occurrence.length;k++) {
            yValues.add(new PieEntry(occurrence[k], Ranking_Genre[k]));
        }
        PieDataSet dataSet = new PieDataSet(yValues, "Genre");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);
    }
}