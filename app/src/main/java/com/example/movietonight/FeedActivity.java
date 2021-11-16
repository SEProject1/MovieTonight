package com.example.movietonight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FeedActivity extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    FeedAdapter adapter;

    public View onCreate(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.feed_recycler, container, false);
        recyclerView = v.findViewById(R.id.feed_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FeedAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // 상하 스크롤 // recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)) ; // 좌우 스크롤

        for (int i = 0; i < 10; i++) {
            String str = i + "번영화";
            Feed item = new Feed(null, "kim", str,
                    "action", "good", 1, 0);
            adapter.setFeedData(item);
        }
        recyclerView.setAdapter(adapter);

        // 출처: https://3001ssw.tistory.com/201 [C++, WinAPI, Android, OpenCV 정리 블로그]
        return v;
    }

    @Override
    public void onClick(View v) {

    }
}
