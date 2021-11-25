package com.example.movietonight;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import android.util.Base64;
import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileActivity extends AppCompatActivity {

    EditText mEtNickname;
    private Bitmap bitmap;
    CircleImageView profile_image;
    String getId;
    private Button btn_uploadPicture;
    private File tempFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        viewInitializations();
        btn_uploadPicture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                chooseFile();
            }
        });
    }
    void viewInitializations() {
        mEtNickname = findViewById(R.id.et_nickname);
    }

    boolean validateInput() {
        if (mEtNickname.getText().toString().equals("")) {
            mEtNickname.setError("닉네임은 비워둘 수 없습니다.");
            return false;
        }
        return true;
    }


    public void performEditProfile (View v) {
            String Nickname = mEtNickname.getText().toString();

            Toast.makeText(this,"프로필 수정 완료",Toast.LENGTH_SHORT).show();
    }
 c
    private void chooseFile() { //Pick Pic from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"사진을 고르세요"),1);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            uploadPicture(getId,getStringImage(bitmap));
        }
    }
    private void uploadPicture(final String id, final String photo)
    {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();


    }
    public String getStringImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
        return encodedImage;
    }
}
