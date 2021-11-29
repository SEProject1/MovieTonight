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

        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
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
            if(result.length > 0){
                for(Movie p : result){
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


}
