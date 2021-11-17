package com.example.movietonight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }


    public class MyAsyncTask extends AsyncTask<String, Void, Movie[]> {
        //로딩중 표시
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("로딩중...");
            //show dialog
            progressDialog.show();

            //목록 배열의 내용을 크리어 해 놓는다.
            movieList.clear();

        }

        @Override
        protected Movie[] doInBackground(String... strings) {
            Log.d("AsyncTask", "url : " + strings[0]);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(strings[0])
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
            adapter.notifyDataSetChanged();
        }
    }

    // 검색창
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("영화제목을 입력하세요.");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            //검색어를 다 입력하고 서치 버튼을 눌렀을때
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MainActivity.this, s + "에 대한 영화를 검색합니다.", Toast.LENGTH_LONG).show();
                String search_url = "https://api.themoviedb.org/3/search/movie?api_key=a652ee13e08fed970ce6ddfc717f595b&query="+ s +"&language=ko-KR&page=1";
                String[] strings = {search_url};
                MyAsyncTask mAsyncTask = new MyAsyncTask();
                mAsyncTask.execute(strings[0]);
                return false;
            }

            //검색 입력창에 새로운 텍스트가 들어갈때 마다 호출 - 여기선 필요 없음
            @Override
            public boolean onQueryTextChange(String s) {
                //Log.d("Search", "keyword: " + s);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_search:
                //Toast.makeText(this, "action_search", Toast.LENGTH_LONG).show();
                return true;
            default:
                Toast.makeText(this, "default", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
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