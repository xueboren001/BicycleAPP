package com.boren.bicycle.activity;

import com.boren.bicycle.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ForgotPass4Activity extends Activity implements OnClickListener{
	
	//注册界面头部的 3 个 View
	private Button btn_title_left, btn_title_right;
	private TextView tv_top_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bicycle_forgotpass_activity_three_over);
		
		initViews();
		
		
	}
	
	private void initViews(){
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);
		tv_top_title = (TextView) findViewById(R.id.tv_title);
		btn_title_left.setOnClickListener(this);
		btn_title_right.setVisibility(View.GONE);
		
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_title_left:
			this.finish();
			break;
		}
	}
	

}
