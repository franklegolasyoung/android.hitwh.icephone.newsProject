package com.example.superproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OneFragment extends Fragment {

    public List<News.DataBean.TechBean> tNewsList = new ArrayList<>();

    private void sendRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {/*
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("https://www.apiopen.top/journalismApi").build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Gson gson = new Gson();
                    News news = gson.fromJson(responseData, News.class);
                    tNewsList = news.getData().getTech();*/
                    OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
                    Request request = new Request.Builder().url("https://www.apiopen.top/journalismApi").build();
                    //Response response = client.newCall(request).execute();
                    Call call = client.newCall(request);
                    try {
                        Response response = call.execute();
                        String responseData = response.body().string();
                        Gson gson = new Gson();
                        News news = gson.fromJson(responseData, News.class);
                        tNewsList = news.getData().getTech();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private View view;//定义view用来设置fragment的layout
    public RecyclerView mrecyclerView;//定义RecyclerView
    private TechNewsAdapter tAdapter;//自定义recycleview的适配器

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sendRequest();
        view = inflater.inflate(R.layout.fragment_one, container, false);

        //News.DataBean.TechBean news1 = new News.DataBean.TechBean("a","12","aa","dd");
        //tNewsList.add(news1);//数据测试

        //获取RecyclerView
        mrecyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mrecyclerView.setLayoutManager(layoutManager);

        tAdapter = new TechNewsAdapter(tNewsList);
        mrecyclerView.setAdapter(tAdapter);

        //设置分割线
        mrecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        tAdapter.setOnItemClickListener(new TechNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //此处进行监听事件的业务处理
                News.DataBean.TechBean news = tNewsList.get(position);
                Intent intent = new Intent(getActivity(),Web.class);
                intent.putExtra("uri",news.getLink());
                startActivity(intent);
            }
        });
        //模拟数据
        return view;
    }

    public static Bitmap getBitmap(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200){
            InputStream inputStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        return null;
    }
}