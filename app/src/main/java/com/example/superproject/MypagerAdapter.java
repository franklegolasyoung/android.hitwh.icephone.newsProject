package com.example.superproject;

import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MypagerAdapter extends PagerAdapter {

    private List<String> mTest ;

    //构造函数
    public MypagerAdapter(String[] classmate){

        mTest = Arrays.asList(classmate);
    }

    ////返回页卡的数量
    @Override
    public int getCount() {
        return mTest.size();
    }

    //官方提示这样写
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //这个方法，return一个对象，这个对象表明了PagerAdapter适配器选择哪个对象*放在当前的ViewPager中
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        TextView textView = new TextView(container.getContext());
        //根据点击的位置，取到mTest数组中的数据
        textView.setText(mTest.get(position));
        textView.setGravity(Gravity.CENTER);
        container.addView(textView);
        return textView;
    }

    //这个方法，是从ViewGroup中移出当前View
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        TextView textView = (TextView) object;
        String text = textView.getText().toString();
        int index = mTest.indexOf(text);
        if (index >= 0) {
            return index;
        }
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTest.get(position);
    }
}
