package com.boren.bicycle.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

public class FragmentModel {

	public String mTitle = "";
	public Fragment mFragment;
	
	public FragmentModel(String title, Fragment fg){
		LinearLayout n = null;
		View v = null;
		mTitle = title;
		mFragment = fg;
	}
}
