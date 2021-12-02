package com.example.movietonight.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietonight.Movie;
import com.example.movietonight.MyRecyclerViewAdapter;
import com.example.movietonight.R;
import com.example.movietonight.SearchActivity;
import com.example.movietonight.SectionDataAdapter;
import com.example.movietonight.SectionItem;
import com.example.movietonight.SingleItem;
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
    SectionDataAdapter adapter_m;
    ArrayList<SingleItem> PmovieList;
    ArrayList<SingleItem> NmovieList;
    ArrayList<SingleItem> TmovieList;
    ArrayList<SectionItem> sectionDataList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_main, container, false);

        Toolbar myToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);

        // 메인
        sectionDataList = new ArrayList<SectionItem>();
        PmovieList = new ArrayList<SingleItem>();
        NmovieList = new ArrayList<SingleItem>();
        TmovieList = new ArrayList<SingleItem>();
            // 메인 실행
        MyAsyncTask_P mAsyncTask_P = new MyAsyncTask_P();

        mAsyncTask_P.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        MyAsyncTask_N mAsyncTask_N = new MyAsyncTask_N();

        mAsyncTask_N.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        MyAsyncTask_T mAsyncTask_T = new MyAsyncTask_T();

        mAsyncTask_T.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        CreateMovieList();

        RecyclerView my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        adapter_m = new SectionDataAdapter(getActivity(), sectionDataList);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter_m);

        return view;
    }

    public class MyAsyncTask_P extends AsyncTask<String, Void, SingleItem[]> {
        //로딩중 표시
        ProgressDialog progressDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("\t로딩중...");
            //show dialog
            progressDialog.show();

            PmovieList.clear();
        }

        @Override
        protected SingleItem[] doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/popular?api_key=a652ee13e08fed970ce6ddfc717f595b&language=ko-KR&page=1")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                SingleItem[] posts = gson.fromJson(rootObject, SingleItem[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(SingleItem[] result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            //ArrayList에 차례대로 집어 넣는다.
            if (result.length > 0) {
                for (SingleItem p : result) {
                    PmovieList.add(p);
                    Log.d("opd", "P" + String.valueOf(result));

                }
            }
            adapter_m.notifyDataSetChanged();
        }
    }

    public class MyAsyncTask_N extends AsyncTask<String, Void, SingleItem[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            NmovieList.clear();

        }
        @Override
        protected SingleItem[] doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/now_playing?api_key=a652ee13e08fed970ce6ddfc717f595b&language=ko-KR&page=1&region=KR")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                SingleItem[] posts = gson.fromJson(rootObject, SingleItem[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(SingleItem[] result2) {
            super.onPostExecute(result2);
            //ArrayList에 차례대로 집어 넣는다.
            if (result2.length > 0) {
                for (SingleItem p : result2) {
                    NmovieList.add(p);
                    Log.d("opd", "N" + String.valueOf(result2));
                }
            }
            adapter_m.notifyDataSetChanged();
        }
    }

    public class MyAsyncTask_T extends AsyncTask<String, Void, SingleItem[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TmovieList.clear();
        }

        @Override
        protected SingleItem[] doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/top_rated?api_key=a652ee13e08fed970ce6ddfc717f595b&language=ko-KR&page=1")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                SingleItem[] posts = gson.fromJson(rootObject, SingleItem[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(SingleItem[] result3) {
            super.onPostExecute(result3);
            //ArrayList에 차례대로 집어 넣는다.
            if (result3.length > 0) {
                for (SingleItem p : result3) {
                    TmovieList.add(p);
                    Log.d("opd", "T" + String.valueOf(result3));
                }
            }
            adapter_m.notifyDataSetChanged();
        }
    }

    public void CreateMovieList() {
        SectionItem sectionDataModel_P = new SectionItem();
        sectionDataModel_P.setHeaderTitle("Popular Movie");
        sectionDataModel_P.setSingItemList(PmovieList);
        sectionDataList.add(sectionDataModel_P);

        SectionItem sectionDataModel_N = new SectionItem();
        sectionDataModel_N.setHeaderTitle("Now Playing");
        sectionDataModel_N.setSingItemList(NmovieList);
        sectionDataList.add(sectionDataModel_N);

        SectionItem sectionDataModel_T = new SectionItem();
        sectionDataModel_T.setHeaderTitle("Top Rate Movie");
        sectionDataModel_T.setSingItemList(TmovieList);
        sectionDataList.add(sectionDataModel_T);
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
                Toast.makeText(getActivity(), s + "에 대한 영화를 검색합니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("SearchTitle", "https://api.themoviedb.org/3/search/movie?api_key=a652ee13e08fed970ce6ddfc717f595b&query="+s+"&language=ko-KR&page=1");
                startActivity(intent);

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
