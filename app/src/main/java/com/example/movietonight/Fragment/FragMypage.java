package com.example.movietonight.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movietonight.CalendarActivity;
import com.example.movietonight.FollowingListActivity;
import com.example.movietonight.MyReviewActivity;
import com.example.movietonight.R;
import com.example.movietonight.RankingActivity;
import com.example.movietonight.SavedActivity;
import com.example.movietonight.StartActivity;

public class FragMypage extends Fragment implements View.OnClickListener{
    private Button review, saved, cal, rank, logout;
    private View view;
    private TextView following;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_mypage, container, false);
        review = view.findViewById(R.id.btn_review);
        saved = view.findViewById(R.id.btn_saved);
        cal = view.findViewById(R.id.btn_calendar);
        rank = view.findViewById(R.id.btn_ranking);
        logout = view.findViewById(R.id.btn_logout);
        following=view.findViewById(R.id.following);
        review.setOnClickListener(this);
        saved.setOnClickListener(this);
        cal.setOnClickListener(this);
        rank.setOnClickListener(this);
        logout.setOnClickListener(this);
        following.setOnClickListener(this);
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
                new AlertDialog.Builder(getActivity()).setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?").setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), StartActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.following:
                intent=new Intent(getActivity(), FollowingListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
