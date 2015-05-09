package com.boren.bicycle.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewPagerAdapter extends PagerAdapter{
	
	
	private Context context;
	private ArrayList<View> views;
	
	public ViewPagerAdapter(Context context, ArrayList<View> views){
		this.context = context;
		this.views = views;
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(views.get(position));
		return views.get(position);
	}
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(views.get(position));
	}
	@Override
	public int getCount() {
		return views.size();
	}
	@Override
	public boolean isViewFromObject(View view, Object arg1) {
		return (view == arg1);
	}

}
