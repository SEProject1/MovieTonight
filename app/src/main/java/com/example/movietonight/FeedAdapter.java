package com.example.movietonight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder>{
    private ArrayList<Feed> feedData=null;
    FeedAdapter(){
        feedData=new ArrayList<>();
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=(LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.feed_item,parent,false);
        FeedViewHolder viewHolder=new FeedViewHolder(context,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        Feed item=feedData.get(position);//리스트 안의position위치의 feed객체 꺼내기
        String mt=item.getMovieTitle();//feed객체의 영화 이름 받아오기
        holder.tvMovieTitle.setText(mt);//홀더에 영화 제목 설정
    }

    @Override
    public int getItemCount() {
        return feedData.size();
    }
    public void setFeedData(Feed data){//데이터 추가
        feedData.add(data);
    }
}
//https://3001ssw.tistory.com/201