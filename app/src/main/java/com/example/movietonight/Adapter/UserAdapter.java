package com.example.movietonight.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movietonight.Class.UserAccount;
import com.example.movietonight.FollowUser;
import com.example.movietonight.FollowingList;
import com.example.movietonight.Fragment.FragMypage;
import com.example.movietonight.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private Context mContext;
    private List<UserAccount> mUsers;
    private FirebaseUser firebaseUser;

    public UserAdapter(Context mContext, List<UserAccount> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        UserAccount user = mUsers.get(position);
        holder.nickname.setText(user.getUserNickname());
        if(user.getIdToken().equals(firebaseUser.getUid())){
            holder.btn_follow.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(user.getImageurl()).into(holder.image_profile);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("UserAccount").child(firebaseUser.getUid()).child("following");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String id = data.getKey();
                    System.out.println("id " + id);
                    System.out.println("user "+ user.getIdToken());
                    if (id.equals(user.getIdToken())) {
                        System.out.println("일치");
                        holder.btn_follow.setText("팔로잉");
                    } else {
                        holder.btn_follow.setText("팔로우");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //isFollowing(databaseReference, user, holder.btn_follow);
        holder.btn_follow.setVisibility(View.VISIBLE);
        //친구 마이페이지로 이동
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("profileid", user.getUserId());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,
                        new FragMypage()).commit();
            }
        });*/

        holder.btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    //firebaseuser -> 현재 사용자 user -> 팔로우하는 사용자
                FollowUser followUser = new FollowUser();
                String id = firebaseUser.getUid();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserAccount").child(id).child("userNickname");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String nickname = snapshot.getValue(String.class);
                        if (holder.btn_follow.getText().toString().equals("팔로우")){
                            holder.btn_follow.setText("팔로잉");
                            followUser.setUserEmail(user.getUserId());
                            followUser.setUserNickname(user.getUserNickname());
                            followUser.setIdToken(user.getIdToken());
                            FirebaseDatabase.getInstance().getReference().child("UserAccount").child(firebaseUser.getUid())
                                    .child("following").child(user.getIdToken()).setValue(followUser);
                            followUser.setUserEmail(firebaseUser.getEmail());
                            followUser.setUserNickname(nickname);
                            followUser.setIdToken(firebaseUser.getUid());
                            FirebaseDatabase.getInstance().getReference().child("UserAccount").child(user.getIdToken())
                                    .child("follower").child(firebaseUser.getUid()).setValue(followUser);
                        } else {    //팔로잉일 때
                            holder.btn_follow.setText("팔로우");
                            FirebaseDatabase.getInstance().getReference().child("UserAccount").child(firebaseUser.getUid())
                                    .child("following").child(user.getIdToken()).removeValue();
                            FirebaseDatabase.getInstance().getReference().child("UserAccount").child(user.getIdToken())
                                    .child("follower").child(firebaseUser.getUid()).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nickname;
        public CircleImageView image_profile;
        public Button btn_follow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.nickname);
            image_profile = itemView.findViewById(R.id.image_profile);
            btn_follow = itemView.findViewById(R.id.btn_follow);
        }
    }
    private void isFollowing(DatabaseReference databaseReference, UserAccount user, ViewHolder button) {
    }
}