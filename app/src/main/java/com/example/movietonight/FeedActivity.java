package com.example.movietonight;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FeedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FeedAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_recycler);
        recyclerView = (RecyclerView)findViewById(R.id.feed_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager
                (this, RecyclerView.VERTICAL, false)) ;
        // 상하 스크롤 // recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)) ; // 좌우 스크롤
        adapter = new FeedAdapter();
        for (int i = 0; i < 10; i++) {
            String str = i + "번영화";
            Feed item=new Feed(null,"kim",str,
                    "action","good",1,0);
            adapter.setFeedData(item);
        }
        recyclerView.setAdapter(adapter);

        // 출처: https://3001ssw.tistory.com/201 [C++, WinAPI, Android, OpenCV 정리 블로그]
    }
}
