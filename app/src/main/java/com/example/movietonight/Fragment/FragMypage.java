package com.example.movietonight.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.movietonight.CalendarActivity;
import com.example.movietonight.FollowerActivity;
import com.example.movietonight.FollowingListActivity;
import com.example.movietonight.MainActivity;
import com.example.movietonight.MyReviewActivity;
import com.example.movietonight.ProfileActivity;
import com.example.movietonight.R;
import com.example.movietonight.RankingActicity;
import com.example.movietonight.SavedActivity;
import com.example.movietonight.StartActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragMypage extends Fragment implements View.OnClickListener{
    private Button profile, review, saved, cal, rank, logout;
    private View view;
    private TextView following,follwer,userName, cnt_follower, cnt_following;
    private CircleImageView img_view;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference("UserAccount");
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://movietonight-78dfc.appspot.com");
    StorageReference storageReference = storage.getReference();
    long fol, foling;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_mypage, container, false);
        review = view.findViewById(R.id.btn_review);
        profile = view.findViewById(R.id.btn_profile);
        img_view = view.findViewById(R.id.img_profile);
        saved = view.findViewById(R.id.btn_saved);
        cal = view.findViewById(R.id.btn_calendar);
        rank = view.findViewById(R.id.btn_ranking);
        logout = view.findViewById(R.id.btn_logout);
        following=view.findViewById(R.id.following);
        follwer=view.findViewById(R.id.follower);
        userName=view.findViewById(R.id.username);
        cnt_follower = view.findViewById(R.id.cnt_follower);
        cnt_following = view.findViewById(R.id.cnt_following);
        profile.setOnClickListener(this);
        review.setOnClickListener(this);
        saved.setOnClickListener(this);
        cal.setOnClickListener(this);
        rank.setOnClickListener(this);
        logout.setOnClickListener(this);
        following.setOnClickListener(this);
        follwer.setOnClickListener(this);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
        getUserInfo();

        storageReference.child(user.getUid()+"/").child("profileimg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getActivity()).load(uri).into(img_view);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getActivity(),"이미지 로딩에 실패하였습니다.",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    public void onClick(View v){
        Intent intent;
        switch(v.getId()){
            case R.id.btn_profile:
                intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
                break;
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
                intent = new Intent(getActivity(), RankingActicity.class);
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
            case R.id.follower:
                intent=new Intent(getActivity(), FollowerActivity.class);
                startActivity(intent);
                break;
        }
    }
    public void getUserInfo(){
        databaseReference.child(user.getUid()).child("userNickname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nickName= (String) snapshot.getValue();
                userName.setText(nickName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child(user.getUid()).child("follower").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null){
                    fol= snapshot.getChildrenCount();
                }
                cnt_follower.setText(Long.toString(fol));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child(user.getUid()).child("following").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null){
                    foling = snapshot.getChildrenCount();
                }
                cnt_following.setText(Long.toString(foling));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}