package com.boren.bicycle.fragment;

import com.boren.bicycle.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AroundFragment extends CommonFragment{

	
	private Context context;
	
	public AroundFragment(Context context){
		this.context = context;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupViews();
	}
	
	private void setupViews(){
		mImageView.setBackgroundResource(R.drawable.blog_app_bg);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
}
