package com.example.superproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class showNews extends AppCompatActivity {

    //private static final String ACTIVITY_TAG = "LogDemo";

    //MagicIndicator指示器数据,Arrays.asList(channels)返回一个Arraylist,并将数据存到mDataList
    String[] channels = new String[]{"科技", "财经", "体育"};
    private List<String> mDataList = Arrays.asList(channels);

    private List<Fragment> list = new ArrayList<>();//viewPager要显示的三个页面

    private ViewPager mViewPager;//ViewPager实例

    private TabFragmentPagerAdapter adapter;//适配器实例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        String time = intent.getStringExtra("time");
        TextView sname = findViewById(R.id.phone);//开发者
        TextView stime = findViewById(R.id.time);//时间
        sname.setText("开发者："+ phone);
        stime.setText(time);
        /*OkHttpClient client = new OkHttpClient.Builder().readTimeout(3, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url("https://www.apiopen.top/journalismApi").build();
        //Response response = client.newCall(request).execute();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String responseData = response.body().string();
            Gson gson = new Gson();
            News news = gson.fromJson(responseData, News.class);
            OneFragment one = new OneFragment();
            one.tNewsList = news.getData().getTech();
            TwoFragment two = new TwoFragment();
            two.tNewsList = news.getData().getMoney();
            ThreeFragment three = new ThreeFragment();
            three.tNewsList = news.getData().getSports();
            list.add(one);
            list.add(two);
            list.add(three);
            adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), list);
            mViewPager = findViewById(R.id.view_pager);
            mViewPager.setAdapter(adapter);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(showNews.ACTIVITY_TAG, "onCreate: fail to connect");
        }*/
        //mViewPager.setOffscreenPageLimit(list.size());
        //sendRequest();

        list.add(new OneFragment());
        list.add(new TwoFragment());
        list.add(new ThreeFragment());
        list.add(new ThreeFragment());
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), list);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(adapter);

        //MagicIndicator指示器部分
        MagicIndicator magicIndicator = findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);

                clipPagerTitleView.setText(mDataList.get(index));
                clipPagerTitleView.setTextColor(Color.BLACK);
                clipPagerTitleView.setClipColor(Color.parseColor("#05c0ab"));

                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            // 设定指示器(cute 小横线)
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#05c0ab"));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });

        //小横线参数设置
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(UIUtil.dip2px(this, 5));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));
        ViewPagerHelper.bind(magicIndicator, mViewPager);
        //指示器滑动跟随的效果
    }
/*
    private void sendRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder().readTimeout(3, TimeUnit.SECONDS).build();
                Request request = new Request.Builder().url("https://www.apiopen.top/journalismApi").build();
                //Response response = client.newCall(request).execute();
                Call call = client.newCall(request);
                try {
                    Response response = call.execute();
                    String responseData = response.body().string();
                    Gson gson = new Gson();
                    News news = gson.fromJson(responseData, News.class);
                    OneFragment one = new OneFragment();
                    one.tNewsList = news.getData().getTech();
                    TwoFragment two = new TwoFragment();
                    two.tNewsList = news.getData().getMoney();
                    ThreeFragment three = new ThreeFragment();
                    three.tNewsList = news.getData().getSports();
                    list.add(one);
                    list.add(two);
                    list.add(three);
                    adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), list);
                    mViewPager = findViewById(R.id.view_pager);
                    mViewPager.setAdapter(adapter);
                    MagicIndicator magicIndicator = findViewById(R.id.magic_indicator);
                    CommonNavigator commonNavigator = new CommonNavigator(getBaseContext());
                    commonNavigator.setAdjustMode(true);
                    commonNavigator.setAdapter(new CommonNavigatorAdapter() {
                        @Override
                        public int getCount() {
                            return mDataList.size();
                        }

                        @Override
                        public IPagerTitleView getTitleView(Context context, final int index) {
                            ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);

                            clipPagerTitleView.setText(mDataList.get(index));
                            clipPagerTitleView.setTextColor(Color.BLACK);
                            clipPagerTitleView.setClipColor(Color.parseColor("#05c0ab"));

                            clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mViewPager.setCurrentItem(index);
                                }
                            });
                            return clipPagerTitleView;
                        }

                        // 设定指示器(cute 小横线)
                        @Override
                        public IPagerIndicator getIndicator(Context context) {
                            LinePagerIndicator indicator = new LinePagerIndicator(context);
                            indicator.setColors(Color.parseColor("#05c0ab"));
                            indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                            return indicator;
                        }
                    });

                    //小横线参数设置
                    magicIndicator.setNavigator(commonNavigator);
                    LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
                    titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
                    titleContainer.setDividerPadding(UIUtil.dip2px(getBaseContext(), 5));
                    titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));
                    ViewPagerHelper.bind(magicIndicator, mViewPager);
                    //指示器滑动跟随的效果
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(showNews.ACTIVITY_TAG, "onCreate: fail to connect");
                }
            }
        }).start();
    }
    */
}