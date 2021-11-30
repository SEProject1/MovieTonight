package com.example.movietonight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    ArrayList<Movie> movieList;
    String SearchTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        movieList = new ArrayList<Movie>();

        //LayoutManager
        recyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));

        Intent intent = getIntent();

        SearchTitle = intent.getStringExtra("SearchTitle");
        Log.d("intent_get", SearchTitle);
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(SearchTitle);

    }


    public class MyAsyncTask extends AsyncTask<String, Void, Movie[]> {
        //로딩중 표시
        ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            movieList.clear();
        }

        @Override
        protected Movie[] doInBackground(String... strings) {
            Log.d("AsyncTask", "url : " + SearchTitle);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(SearchTitle)
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

            //ArrayList에 차례대로 집어 넣는다.
            if (result.length > 0) {
                for (Movie p : result) {
                    movieList.add(p);
                    Log.d("movieList.add: ", String.valueOf(p));
                }
            }

            //어댑터 설정
            adapter = new SearchAdapter(SearchActivity.this, movieList);
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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //검색어를 다 입력하고 서치 버튼을 눌렀을때
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(SearchActivity.this, s + "에 대한 영화를 검색합니다.", Toast.LENGTH_LONG).show();

                String search_url = "https://api.themoviedb.org/3/search/movie?api_key=a652ee13e08fed970ce6ddfc717f595b&query=" + s + "&language=ko-KR&page=1";
                String[] strings = {search_url};
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute(strings[0]);
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
