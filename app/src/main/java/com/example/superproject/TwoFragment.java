package com.example.superproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class TwoFragment extends Fragment {

    public List<News.DataBean.MoneyBean> mNewsList = new ArrayList<>();

    private void sendRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
                    Request request = new Request.Builder().url("https://www.apiopen.top/journalismApi").build();
                    //Response response = client.newCall(request).execute();
                    Call call = client.newCall(request);
                    try {
                        Response response = call.execute();
                        String responseData = response.body().string();
                        Gson gson = new Gson();
                        News news = gson.fromJson(responseData, News.class);
                        mNewsList = news.getData().getMoney();
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
    public RecyclerView recyclerView;//定义RecyclerView
    private MoneyNewsAdapter tAdapter;//自定义recycleview的适配器

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取fragment的layout
        sendRequest();
        view = inflater.inflate(R.layout.fragment_two, container, false);
        //对recycleview进行配置
        //News.DataBean.MoneyBean news1 = new News.DataBean.MoneyBean("a","12","aa","dd");
        //tNewsList.add(news1);
        //获取RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view2);
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙//设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //创建adapter
        tAdapter = new MoneyNewsAdapter(mNewsList);
        //给RecyclerView设置adapter
        recyclerView.setAdapter(tAdapter);
        //设置item的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        tAdapter.setOnItemClickListener(new MoneyNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //此处进行监听事件的业务处理
                News.DataBean.MoneyBean news = mNewsList.get(position);
                Intent intent = new Intent(getActivity(), Web.class);
                intent.putExtra("uri", news.getLink());
                startActivity(intent);
            }
        });
        //模拟数据
        return view;
    }

    /**
     * TODO 对recycleview进行配置
     */
}