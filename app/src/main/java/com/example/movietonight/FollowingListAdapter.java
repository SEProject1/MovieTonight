package com.example.movietonight;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class FollowingListAdapter extends RecyclerView.Adapter<FollowingListViewHolder> {

    private ArrayList<FollowingList> myFollowingList=null;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference("UserAccount");
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

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
//        databaseReference.child(idToken).child("profilePic").addValueEventListener(new ValueEventListener() {
//            //db에 이진수로 저장된 이미지를 받아와 drawable로 바꾼뒤 ivFollowingUserPic에 설정함
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String image=snapshot.getValue().toString();
//                byte[]b=binaryStringToByteArray(image);
//                ByteArrayInputStream is=new ByteArrayInputStream(b);
//                Drawable profilePic=Drawable.createFromStream(is,"profilePic");
//                holder.ivFollwingUserPic.setImageDrawable(profilePic);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        holder.btnUnfollow.setOnClickListener(new View.OnClickListener() {//언팔로우버튼 클릭
            @Override
            public void onClick(View view) {
                //db 팔로잉 리스트에서 해당 유저 제거
//                databaseReference.child(user.getUid()).child("following").child(idToken).
//                        removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(view.getContext(),"언팔로우 되었습니다.",Toast.LENGTH_LONG).show();
//                    }
//                });
                myFollowingList.remove(holder.getAdapterPosition());//UI에서 해당 item삭제
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(),myFollowingList.size());
            }
        });
    }
// 스트링을 바이너리 바이트 배열로
 public static byte[] binaryStringToByteArray(String s) {
     int count = s.length() / 8;
     byte[] b = new byte[count];
     for (int i = 1; i < count; ++i) {
         String t = s.substring((i - 1) * 8, i * 8);
         b[i - 1] = binaryStringToByte(t);
     }
     return b;
    }
    // 스트링을 바이너리 바이트로
    public static byte binaryStringToByte(String s) {
        byte ret = 0, total = 0;
        for (int i = 0; i < 8; ++i) {
            ret = (s.charAt(7 - i) == '1') ? (byte) (1 << i) : 0;
            total = (byte) (ret | total);
        }
        return total;
    }

    @Override
    public int getItemCount() {
        return myFollowingList.size();
    }
    public void setMyFollowingList(FollowingList data){//데이터 추가
        myFollowingList.add(data);
    }
}
