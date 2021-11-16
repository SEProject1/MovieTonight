package com.example.movietonight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListViewHolder>{//찜한 영화 아이템을 만들고 설정, 추가하는 어댑터
    private ArrayList<FavoriteMovie> favoriteMovieData=null;//찜한 영화 아이템 list
    FavoriteListAdapter (){
        favoriteMovieData=new ArrayList<>();
    }

    @NonNull
    @Override
    public FavoriteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=(LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.favorite_list_item,parent,false);
        FavoriteListViewHolder viewHolder=new FavoriteListViewHolder(context,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteListViewHolder holder, int position) {
        FavoriteMovie item=favoriteMovieData.get(position);//리스트 안의position위치의 favoritemovie객체 꺼내기
        String mt=item.getMovieTitle();//favoritemovie객체의 영화 이름 받아오기
        holder.tvFavoriteMovieTitle.setText(mt);//홀더에 영화 제목 설정
        holder.btnFavoriteMovieDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db에서 찜한 영화 삭제

                favoriteMovieData.remove(holder.getAdapterPosition());//UI에서 해당 item삭제
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(),favoriteMovieData.size());
            }
        });
    }

    @Override
    public int getItemCount() {

        return favoriteMovieData.size();
    }
    public void setFavoriteMovieData(FavoriteMovie data){//데이터 추가

        favoriteMovieData.add(data);
    }
}
