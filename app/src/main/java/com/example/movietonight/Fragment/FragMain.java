package com.example.movietonight.Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietonight.Movie;
import com.example.movietonight.MyRecyclerViewAdapter;
import com.example.movietonight.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragMain extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    ArrayList<Movie> movieList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_main, container, false);

        Toolbar myToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.recycler_view);
        movieList = new ArrayList<Movie>();

        //Asynctask - OKHttp
        MyAsyncTask mAsyncTask = new MyAsyncTask();
        mAsyncTask.execute("https://api.themoviedb.org/3/movie/upcoming?api_key=a652ee13e08fed970ce6ddfc717f595b&language=ko-KR&page=1");

        //LayoutManager
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2, GridLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return view;
    }


    public class MyAsyncTask extends AsyncTask<String, Void, Movie[]> {
        //로딩중 표시
        ProgressDialog progressDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("로딩중...");
            //show dialog
            progressDialog.show();

            //목록 배열의 내용을 클리어 해 놓는다.
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
            if (result.length > 0) {
                for (Movie p : result) {
                    movieList.add(p);
                }
            }

            //어댑터 설정
                if(getActivity()!=null) {
                    adapter = new MyRecyclerViewAdapter(getActivity(), movieList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
        }
    }


    // 검색창
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.my_menu, menu);
//        getActivity().getMenuInflater().inflate(R.menu.my_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("영화제목을 입력하세요.");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            //검색어를 다 입력하고 서치 버튼을 눌렀을때
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getActivity(), s + "에 대한 영화를 검색합니다.", Toast.LENGTH_LONG).show();

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_search:
                //Toast.makeText(this, "action_search", Toast.LENGTH_LONG).show();
                return true;

            default:
                Toast.makeText(getActivity(), "default", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }
}
