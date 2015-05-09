package com.boren.bicycle.fragment;

import com.boren.bicycle.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AppHomeFragment extends Fragment{
	
	private Context context;
	
	public AppHomeFragment(Context context){
		this.context = context;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_apphome, null);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupViews();
	}
	
	private void setupViews(){
		//mImageView.setBackgroundResource(R.drawable.blog_app_bg);
		//mImageView.setVisibility(View.GONE);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
}
