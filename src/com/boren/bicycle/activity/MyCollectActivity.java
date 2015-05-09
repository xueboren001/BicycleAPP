package com.boren.bicycle.activity;

import com.boren.bicycle.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class MyCollectActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_collect_activity);		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			this.finish();
			this.overridePendingTransition(R.anim.activity_back_left_in, R.anim.activity_back_right_out);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
}
