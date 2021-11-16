package com.example.movietonight;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Fragment implements View.OnClickListener{


    public View OnCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_main, container, false);
        return v;
    }

    @Override
    public void onClick(View v) {

    }
}