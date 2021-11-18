package com.example.movietonight;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import android.util.Base64;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileActivity extends AppCompatActivity {

    EditText etNickname;
    private Bitmap bitmap;
    CircleImageView profile_image;
    String getId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        viewInitializations();
    }


    void viewInitializations() {
        etNickname = findViewById(R.id.et_nickname);
    }

    boolean validateInput() {
        if (etNickname.getText().toString().equals("")) {
            etNickname.setError("닉네임은 비워둘 수 없습니다.");
            return false;
        }
        return true;
    }

    public void onClick(View view)
    {
        choosefile();
    }

    public void performEditProfile (View v) {
        if (validateInput()) {

            String Nickname = etNickname.getText().toString();

            Toast.makeText(this,"프로필 수정 완료",Toast.LENGTH_SHORT).show();
            //DB연결하는부분
        }
    }
    private void saveEditDetail()
    {
        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String id = getId;

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1"))
                    {
                        Toast.makeText(ProfileActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                        sessionManager.createSession(name, email, id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, "Error : " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
                Toast.makeText(ProfileActivity.this, "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
    private void choosefile()
    {
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
