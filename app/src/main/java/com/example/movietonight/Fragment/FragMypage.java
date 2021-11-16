package com.example.movietonight.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movietonight.CalendarActivity;
import com.example.movietonight.LogoutActivity;
import com.example.movietonight.MyReviewActivity;
import com.example.movietonight.R;
import com.example.movietonight.RankingActivity;
import com.example.movietonight.SavedActivity;

public class FragMypage extends Fragment {
    private Button review, saved, cal, rank, logout;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_mypage, container, false);
        review = view.findViewById(R.id.btn_review);
        saved = view.findViewById(R.id.btn_saved);
        cal = view.findViewById(R.id.btn_calendar);
        rank = view.findViewById(R.id.btn_ranking);
        logout = view.findViewById(R.id.btn_logout);
        return view;
    }
    public void onClick(View v){
        Intent intent;
        switch(v.getId()){
            case R.id.btn_review:
                intent = new Intent(getActivity(), MyReviewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_saved:
                intent = new Intent(getActivity(), SavedActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_calendar:
                intent = new Intent(getActivity(), CalendarActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_ranking:
                intent = new Intent(getActivity(), RankingActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_logout:
                intent = new Intent(getActivity(), LogoutActivity.class);
                startActivity(intent);
                break;
        }
    }
}
