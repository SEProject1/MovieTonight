package com.example.movietonight.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietonight.Adapter.UserAdapter;
import com.example.movietonight.Class.UserAccount;
import com.example.movietonight.Feed;
import com.example.movietonight.FeedAdapter;
import com.example.movietonight.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragFeed extends Fragment {

    private View view;
    private RecyclerView recyclerView,feedRecyclerView;
    private UserAdapter userAdapter;
    private List<UserAccount> mUsers;
    private FeedAdapter feedAdapter;
    ArrayList<Feed> feeds=new ArrayList<Feed>();
    EditText search_bar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_feed, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setVisibility(View.GONE);

        feedRecyclerView=view.findViewById(R.id.feed_recycler_view);
        feedRecyclerView.setHasFixedSize(true);
        feedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        search_bar = view.findViewById(R.id.search_bar);
        mUsers = new ArrayList<>();
        userAdapter = new UserAdapter(getContext(), mUsers);
        feedAdapter = new FeedAdapter();
        recyclerView.setAdapter(userAdapter);
        feedRecyclerView.setAdapter(feedAdapter);
        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    recyclerView.setVisibility(View.VISIBLE);
                }
        });

        readUsers();
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString().toLowerCase());
                if(search_bar.getText().toString().equals("")){
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        getFeeds();//db에서 피드 가져옴
        setFeeds();//item추가
        return view;
    }
    private void searchUsers(String s) {
        Query query = FirebaseDatabase.getInstance().getReference("UserAccount").orderByChild("userNickname")
                .startAt(s)
                .endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserAccount user = snapshot.getValue(UserAccount.class);
                    mUsers.add(user);
                }

                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void readUsers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserAccount");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (search_bar.getText().toString().equals("")) {
                    mUsers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserAccount user = snapshot.getValue(UserAccount.class);
                        mUsers.add(user);
                    }

                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getFeeds() {//db피드 가져옴
        //db에서 찜 영화 가져옴
        for (int i = 0; i < 10; i++) {
            String name=i+"번 유저";
            String movieTitle = i + "번 영화";
            String genre=i+"번 장르";
            String review=i+"번 리뷰";
            String reviewTitle=i+"번 리뷰제목";
            int like=i;
            int dislike=10+i;
            Feed item=new Feed(null,name,reviewTitle,movieTitle,genre
                    ,review,like,dislike);
            feeds.add(item);//아이템을 리스트에 넣기
        }
    }
    public void setFeeds(){//아이템 추가 메서드
        for (int i=0;i<feeds.size();i++){
            feedAdapter.setFeedData(feeds.get(i));
        }
    }
}