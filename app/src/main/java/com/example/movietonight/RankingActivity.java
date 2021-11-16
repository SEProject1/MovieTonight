package com.example.movietonight;

import androidx.appcompat.app.AppCompatActivity;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class RankingActivity extends AppCompatActivity {
    PieChart chart1;

    public void initView(View v){
        chart1 = (PieChart) v.findViewById(R.id.tab1_chart_1);

    }
    private void setPieChart(List<itemModel>itemList){

        chart1.clearChart();
        chart1.addPieSlice(new PieModel("TYPE 1",60, Color.parseColor("#CDA67F")));
        chart1.addPieSlice(new PieModel("TYPE 2", 40, Color.parseColor("#00BFFF")));

        chart1.startAnimation();

    }

}
