package com.example.superproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SportsNewsAdapter extends RecyclerView.Adapter<SportsNewsAdapter.ViewHolder> {

    private List<News.DataBean.SportsBean> aNewsList;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    //点击事件响应

    static class ViewHolder extends RecyclerView.ViewHolder {
        View newsView;
        ImageView newsImage;
        TextView newsName;
        TextView newsContext;
        TextView newsTime;
        TextView newsFrom;
        public ViewHolder(View view) {
            super(view);
            newsView = view;
            newsImage = view.findViewById(R.id.news_image);
            newsName = view.findViewById(R.id.news_name);
            newsContext = view.findViewById(R.id.news_context);
            newsTime = view.findViewById(R.id.news_time);
            newsFrom = view.findViewById(R.id.news_from);
        }//对应
    }
    public SportsNewsAdapter(List<News.DataBean.SportsBean> NewsList) {
        aNewsList = NewsList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.
                news_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        News.DataBean.SportsBean news = aNewsList.get(position);
        /*try {
            holder.newsImage.setImageBitmap(getBitmap(news.getPicInfo().get(position).getUrl()));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        holder.newsName.setText(news.getTitle());
        holder.newsContext.setText(news.getDigest());
        holder.newsTime.setText(news.getPtime());
        holder.newsFrom.setText(news.getSource());//将其都显示出来
        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                mOnItemClickListener.onItemClick(v, position);
                News.DataBean.SportsBean news = aNewsList.get(position);
                /*Toast.makeText(v.getContext(), "You clicked " + news.getTitle(),
                        Toast.LENGTH_SHORT).show();
                //试验点击效果*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return aNewsList.size();
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