package com.boren.bicycle.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.boren.bicycle.R;
import com.boren.bicycle.fragment.FragmentControlCenter;
import com.boren.bicycle.fragment.FragmentModel;
import com.boren.bicycle.fragment.NavigationFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * 
 * @author lance
 * @csdn-blog --> http://blog.csdn.net/geniuseoe2012
 * @android-develop-group ：298044305
 */
public class MainActivity extends SlidingFragmentActivity implements OnClickListener{

	private Context context;
	
	private String mTitle;
	private Fragment mContent;
	
	private ImageView mLeftIcon;
	private ImageView mRightIcon;
	private TextView mTitleTextView;
	
	private FragmentControlCenter mControlCenter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.context = this;
		mControlCenter = FragmentControlCenter.getInstance(this);
		setupViews();
		initData();
	}
	
	private void setupViews(){
		setContentView(R.layout.bicycle_main_slidemenu);
		initActionBar();
		initSlideMenu();
	}
	
	private void initSlideMenu(){
		FragmentModel fragmentModel = mControlCenter.getAppHomeFragmentModel();
		switchContent(fragmentModel);

		
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);

		setBehindContentView(R.layout.left_menu_frame);
		sm.setSlidingEnabled(true);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		//xueboren getSupportedActionBar()
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.left_menu_frame, new NavigationFragment(this))
		.commit();
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setBehindScrollScale(0);
		sm.setFadeDegree(0.25f);
		//sm.setSecondaryMenu(R.layout.right_menu_frame);
		//sm.setSecondaryShadowDrawable(R.drawable.shadow);
		//getSupportFragmentManager()
		//.beginTransaction()
		//.replace(R.id.right_menu_frame, new SettingFragment())
		//.commit();
	}
	
	private void initActionBar(){
		android.app.ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.actionbar_layout);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		
		mLeftIcon = (ImageView) findViewById(R.id.iv_left_icon);
		mRightIcon = (ImageView) findViewById(R.id.iv_right_icon);
		mLeftIcon.setOnClickListener(this);
		mRightIcon.setOnClickListener(this);
		
		mTitleTextView = (TextView) findViewById(R.id.tv_title);
	}
	
	private void initData(){
		
	}
	
	
	public void switchContent(final FragmentModel fragment) {
		mTitle = fragment.mTitle;
		mContent = fragment.mFragment;

		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			@Override
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
		
		mTitleTextView.setText(mTitle);
	}

	public void goNewActivity(Class clazz){
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
		overridePendingTransition(R.anim.activity_new_right_in, R.anim.activity_new_left_out);
	}
	
	public void goBackActivity(Class clazz){
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
		overridePendingTransition(R.anim.activity_back_left_in, R.anim.activity_back_right_out);
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.iv_left_icon:
			toggle();
			break;
		case R.id.iv_right_icon:
			showSecondaryMenu();
			break;
		}
	}	

}

