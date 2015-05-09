package com.boren.bicycle.fragment;

import com.boren.bicycle.R;
import com.boren.bicycle.util.CommonLog;
import com.boren.bicycle.util.LogFactory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CommonFragment  extends Fragment{

	public static final CommonLog log = LogFactory.createLog();
	public ImageView mImageView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.common_layout, null);
		mImageView = (ImageView) view.findViewById(R.id.imageView);
		return view;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
