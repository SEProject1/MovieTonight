package com.example.movietonight.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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
import com.example.movietonight.FollowingList;
import com.example.movietonight.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class FragFeed extends Fragment {

    private View view;
    private RecyclerView recyclerView,feedRecyclerView;
    private UserAdapter userAdapter;
    private List<UserAccount> mUsers;
    private FeedAdapter feedAdapter;
    ArrayList<Feed> feeds=new ArrayList<Feed>();
    ArrayList<FollowingList> followingLists=new ArrayList<>();
    EditText search_bar;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference("UserAccount");
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_feed, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        feedRecyclerView=view.findViewById(R.id.feed_recycler_view);
        feedRecyclerView.setHasFixedSize(true);
        feedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        search_bar = view.findViewById(R.id.search_bar);
        mUsers = new ArrayList<>();
        userAdapter = new UserAdapter(getContext(), mUsers);
        feedAdapter = new FeedAdapter();
        recyclerView.setAdapter(userAdapter);

        recyclerView.setVisibility(View.GONE);

        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);
                //feedRecyclerView.setBottom(R.id.recycler_view);
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
        getFollowingList();
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
                List<UserAccount> result = new ArrayList<UserAccount>();
                result = mUsers;
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
    public void getFollowingList(){
        followingLists.clear();
        databaseReference.child(user.getUid()).child("following").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {//팔로잉 리스트 부름
                for(DataSnapshot s:snapshot.getChildren()){
                    HashMap<String,Object>followingMap=(HashMap<String, Object>) s.getValue();
                    String nickName=(String) followingMap.get("userNickname");
                    String idToken=(String)followingMap.get("idToken");
                    FollowingList item=new FollowingList(nickName,idToken);
                    followingLists.add(item);
                }
                getFeeds();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void getFeeds() {//db피드 가져옴
        //db에서 나의 리뷰 가져옴
            feeds.clear();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(int i=0;i<followingLists.size();i++){
                            String name=followingLists.get(i).getNickName();
                            Iterable<DataSnapshot> reviewSnapshot= snapshot.child(followingLists.get(i).getIdToken()).
                                        child("Review").getChildren();
                            Iterator<DataSnapshot> iter=reviewSnapshot.iterator();
                            while(iter.hasNext()){
                                HashMap<String,Object> reviewMap= (HashMap<String, Object>) iter.next().getValue();
                                String movieTitle = (String)reviewMap.get("mtitle");
                                String genre=(String)reviewMap.get("mgenre");
                                String review=(String)reviewMap.get("rcontent");
                                String reviewTitle=(String)reviewMap.get("rtitle");
                                String like=Long.toString((Long) reviewMap.get("like"));
                                String dislike=Long.toString((Long) reviewMap.get("dislike"));
                                String mdate=(String)reviewMap.get("mdate");
                                Feed item=new Feed(null,name,reviewTitle,movieTitle,genre
                                        ,review,like,dislike,mdate,followingLists.get(i).getIdToken());
                                feeds.add(item);//아이템을 리스트에 넣기
                            }

                    }
                    Collections.sort(feeds);
                    setFeeds();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }
    public void setFeeds(){//아이템 추가 메서드
        for (int i=0;i<feeds.size();i++){
            feedAdapter.setFeedData(feeds.get(i));
        }
        feedRecyclerView.setAdapter(feedAdapter);
    }
}