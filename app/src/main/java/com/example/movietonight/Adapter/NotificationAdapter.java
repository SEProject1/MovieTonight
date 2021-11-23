package com.example.movietonight.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movietonight.Class.Notification;
import com.example.movietonight.Class.UserAccount;
import com.example.movietonight.Fragment.FragFeed;
import com.example.movietonight.Fragment.FragMypage;
import com.example.movietonight.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context mContext;
    private List<Notification> mNotifications;

    public NotificationAdapter(Context mContext, List<Notification> mNotifications) {
        this.mContext = mContext;
        this.mNotifications = mNotifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.notification_item, parent, false);

        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Notification notification = mNotifications.get(position);

        getUser(holder.imageProfile, holder.username, notification.getUserid());
        holder.comment.setText(notification.getText()); //comment 알림창에 출력


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notification.getIsReview()) {
                    mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
                            .edit().putString("userid", notification.getUserid()).apply();

                    ((FragmentActivity)mContext).getSupportFragmentManager()
                            .beginTransaction().replace(R.id.main_frame, new FragFeed()).commit();
                } else {
                    mContext.getSharedPreferences("PROFILE", Context.MODE_PRIVATE)
                            .edit().putString("profileId", notification.getUserid()).apply();

                    ((FragmentActivity)mContext).getSupportFragmentManager()
                            .beginTransaction().replace(R.id.main_frame, new FragMypage()).commit();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageProfile;
        public TextView username;
        public TextView comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageProfile = itemView.findViewById(R.id.image_profile);
            username = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.comment);
        }


    }


    private void getUser(ImageView imageView, TextView textView, String userNickname) {
        FirebaseDatabase.getInstance().getReference().child("UserAccount").child(userNickname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount user = dataSnapshot.getValue(UserAccount.class);
//                if (user.getImageurl().equals("default")) {
//                    imageView.setImageResource(R.mipmap.ic_launcher);
//                } else {
//                    Glide.with(mContext).load(user.getImageurl()).into(imageView);
//                }
                imageView.setImageResource(R.mipmap.ic_launcher_round); //임시 프로필 사진
                textView.setText(user.getUserNickname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
