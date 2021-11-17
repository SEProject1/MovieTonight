package com.example.movietonight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.movietonight.Fragment.FragFeed;
import com.example.movietonight.Fragment.FragMain;
import com.example.movietonight.Fragment.FragMypage;
import com.example.movietonight.Fragment.FragNoti;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        movieList = new ArrayList<Movie>();

        //Asynctask - OKHttp
        MyAsyncTask mAsyncTask = new MyAsyncTask();
        mAsyncTask.execute();

        //LayoutManager
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
    }

    public class MyAsyncTask extends AsyncTask<String, Void, Movie[]> {
        //로딩중 표시
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("\t로딩중...");
            //show dialog
            progressDialog.show();
        }

        @Override
        protected Movie[] doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/upcoming?api_key=a652ee13e08fed970ce6ddfc717f595b&language=ko-KR&page=1")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                Movie[] posts = gson.fromJson(rootObject, Movie[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Movie[] result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            //ArrayList에 차례대로 집어 넣는다.
            if(result.length > 0){
                for(Movie p : result){
                    movieList.add(p);
                }
            }

            //어답터 설정
            adapter = new MyRecyclerViewAdapter(MainActivity.this, movieList);
            recyclerView.setAdapter(adapter);
        }
    }

}

//    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
//    private FragmentManager fm;
//    private FragmentTransaction ft;
//    private FragMain fragMain;
//    private FragFeed fragFeed;
//    private FragNoti fragNotification;
//    private FragMypage fragMypage;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        bottomNavigationView = findViewById(R.id.bottomNavi);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.action_home:
//                        setFrag(0);
//                        break;
//                    case R.id.action_feed:
//                        setFrag(1);
//                        break;
//                    case R.id.action_notification:
//                        setFrag(2);
//                        break;
//                    case R.id.action_mypage:
//                        setFrag(3);
//                        break;
//                }
//                return true;
//            }
//        });
//        fragMain = new FragMain();
//        fragFeed = new FragFeed();
//        fragNotification = new FragNoti();
//        fragMypage = new FragMypage();
//        setFrag(0); //첫 프래그먼트 화면을 무엇으로 지정해줄 것인지 선택
//
//    }
//
//
//    // 프래그먼터 교체가 일어나는 실행문
//    private void setFrag(int n) {
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        switch (n) {
//            case 0 :
//                ft.replace(R.id.main_frame, fragMain);
//                ft.commit();
//                break;
//            case 1 :
//                ft.replace(R.id.main_frame, fragFeed);
//                ft.commit();
//                break;
//            case 2 :
//                ft.replace(R.id.main_frame, fragNotification);
//                ft.commit();
//                break;
//            case 3 :
//                ft.replace(R.id.main_frame, fragMypage);
//                ft.commit();
//                break;
//
//        }
//
//    }