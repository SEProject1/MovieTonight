package com.example.movietonight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FollowingListAdapter extends RecyclerView.Adapter<FollowingListViewHolder> {

    private ArrayList<FollowingList> myFollowingList=null;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference("UserAccount");
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://movietonight-78dfc.appspot.com");
    private StorageReference storageReference = storage.getReference();
    FollowingListAdapter(){
        myFollowingList=new ArrayList<>();
    }
    @NonNull
    @Override
    public FollowingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=(LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.following_list_item,parent,false);
        FollowingListViewHolder viewHolder=new FollowingListViewHolder(context,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingListViewHolder holder, int position) {
        FollowingList item=myFollowingList.get(position);
        String nickName=item.getNickName();
        String idToken=item.getIdToken();
        holder.tvFollowingNickName.setText(nickName);
        storageReference.child(idToken+"/").child("profileimg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext()).load(uri).into(holder.ivFollwingUserPic);
            }
        });
        holder.btnUnfollow.setOnClickListener(new View.OnClickListener() {//언팔로우버튼 클릭
            @Override
            public void onClick(View view) {
                //db 팔로잉 리스트에서 해당 유저 제거
                AlertDialog.Builder msgBilder=new AlertDialog.Builder(view.getContext());
                msgBilder.setTitle("정말로 언팔로우 하시겠습니까?");
                msgBilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference.child(user.getUid()).child("following").child(idToken).
                                removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                //해당 유저의 팔로워 목록에서 사용자 제거
                                databaseReference.child(idToken).child("follower").child(user.getUid()).removeValue();
                                Toast.makeText(view.getContext(),"언팔로우 되었습니다.",Toast.LENGTH_LONG).show();
                            }
                        });
                        myFollowingList.remove(holder.getAdapterPosition());//UI에서 해당 item삭제
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(),myFollowingList.size());
                    }
                });
                msgBilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(),"취소되었습니다.",Toast.LENGTH_LONG).show();
                    }
                });
                msgBilder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myFollowingList.size();
    }
    public void setMyFollowingList(FollowingList data){//데이터 추가
        myFollowingList.add(data);
    }
}
