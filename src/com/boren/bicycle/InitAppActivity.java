package com.boren.bicycle;

import java.util.ArrayList;

import com.boren.bicycle.activity.LoginActivity;
import com.boren.bicycle.activity.MainActivity;
import com.boren.bicycle.activity.Register1Activity;
import com.boren.bicycle.adapter.ViewPagerAdapter;
import com.boren.bicycle.util.AsyncHttpUtil;
import com.boren.bicycle.util.Bicycle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class InitAppActivity extends Activity implements OnClickListener{
	
	public static final String TAG = "BicycleAppTAG";
	private Context context;
	private SharedPreferences sp;
	
	private String ViewPagerHasInited = "ViewPagerHasInited";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        
        sp = getSharedPreferences(Bicycle.app_sharedPreferences_name,Context.MODE_PRIVATE);
        boolean ViewPagerHasInitedB = sp.getBoolean(ViewPagerHasInited, false);
        
        /*
        if( ViewPagerHasInitedB ){
        	//setContentView(R.layout.activity_bicycle_main);
        	//nitViews();
        	Intent intent = new Intent(this, MainActivity.class);
        	startActivity(intent);
        	finish();
        }else{	//初始化引导界面
            setContentView(R.layout.guide_main);
            initViewPagerDatasAndViews();
        }*/
        setContentView(R.layout.guide_main);
        initViewPagerDatasAndViews();
        
    }
    /*
     * 初始化 app 主页面元素
     */
    private Button bicycle_btn_login;
    private void initViews(){
    	bicycle_btn_login = (Button) findViewById(R.id.boren_bicycle_main_btn_login);
    	bicycle_btn_login.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = null;
		switch(id){
		
		case R.id.boren_bicycle_main_btn_login:
			//testBaidu();
			new AsyncHttpUtil().execute("http://192.168.1.105:8080/BicycleWeb/login");
			break;
		}
	}
    
    
    
    
	/*
		***********************以下是 ViewPager 相关： **************
		1. initViewPagerDatasAndViews() : 初始化 ViewPager相关组件
		2. MyOnPageChangeListener : 用来 在 ViewPager组件改变 View时，同时改变 ViewPager下面的小圆点
		3. MyVeiwPageBtnOnClickListener : 实现 登录、注册、先逛选 三个按钮的跳转功能
		****************************************************
	*/
  	private ViewPager viewPager;
  	private ArrayList<View> views;
  	private PagerAdapter adapter; 
  	private View view1, view2, view3, view4, view5, view6;
  	private ImageView pointImg0, pointImg1, pointImg2, pointImg3, pointImg4, pointImg5;
  	private Button btn_login,btn_register,btn_notlogin;
    
  	private void initViewPagerDatasAndViews(){
    	//初始化引导界面下面的小圆点
    	pointImg0 = (ImageView) findViewById(R.id.boren_point_page0);
    	pointImg1 = (ImageView) findViewById(R.id.boren_point_page1);
    	pointImg2 = (ImageView) findViewById(R.id.boren_point_page2);
    	pointImg3 = (ImageView) findViewById(R.id.boren_point_page3);
    	pointImg4 = (ImageView) findViewById(R.id.boren_point_page4);
    	pointImg5 = (ImageView) findViewById(R.id.boren_point_page5);
    	//初始化引导界面中部的view，每个view对应一个layout/xml文件
    	//并将所有 的view 添加入 ArrayList<View> views中
    	views = new ArrayList<View>();
    	LayoutInflater li = LayoutInflater.from(this);
    	view1 = li.inflate(R.layout.guide_view01, null);
    	view2 = li.inflate(R.layout.guide_view02, null);
    	view3 = li.inflate(R.layout.guide_view03, null);
    	view4 = li.inflate(R.layout.guide_view04, null);
    	view5 = li.inflate(R.layout.guide_view05, null);
    	view6 = li.inflate(R.layout.guide_view06, null);
    	views.add(view1);	views.add(view2);	views.add(view3);
    	views.add(view4);	views.add(view5);	views.add(view6);
    	//初始化 ViewPagerAdapter 类的实例 
    	adapter = new ViewPagerAdapter(this, views);
    	viewPager = (ViewPager) findViewById(R.id.boren_viewpager);  	
    	viewPager.setAdapter(adapter);
    	viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    	//最后一个引导界面的按钮
    	btn_login = (Button) view6.findViewById(R.id.boren_view_pager_loginBtn);
    	btn_notlogin = (Button) view6.findViewById(R.id.boren_view_pager_no_login_Btn);
    	btn_register = (Button) view6.findViewById(R.id.boren_view_pager_registerBtn);
    	OnClickListener listener = new MyVeiwPageBtnOnClickListener();
    	btn_login.setOnClickListener(listener);
    	btn_notlogin.setOnClickListener(listener);
    	btn_register.setOnClickListener(listener);
    	//在 SharedPreferences 中，添加 ViewPagerHasInited 值
    	Editor spe = sp.edit();
    	spe.putBoolean(ViewPagerHasInited, true);
    	spe.commit();
    }
    
    private class MyOnPageChangeListener implements OnPageChangeListener{
		@Override
		public void onPageScrollStateChanged(int position) {
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		@Override
		public void onPageSelected(int position) {
			switch(position){
			case 0:
				pointImg5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				pointImg0.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
				pointImg1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				break;
			case 1:
				pointImg0.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				pointImg1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
				pointImg2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				break;
			case 2:
				pointImg1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				pointImg2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
				pointImg3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				break;
			case 3:
				pointImg2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				pointImg3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
				pointImg4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				break;
			case 4:
				pointImg3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				pointImg4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
				pointImg5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				break;
			case 5:
				pointImg4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				pointImg5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
				pointImg0.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
				break;
			}
		}
    }
    
    /*
     * 专门用来监听 引导界面按钮 点击事件 的 OnClickListener
     */
    private class MyVeiwPageBtnOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch(id){
			case R.id.boren_view_pager_loginBtn:
				startActivityFromViewPageBtn(LoginActivity.class);
				break;
			case R.id.boren_view_pager_no_login_Btn:
				//startActivityFromViewPageBtn(BicycleMainActivity.class);
				startActivityFromViewPageBtn(MainActivity.class);
				break;
			case R.id.boren_view_pager_registerBtn:
				startActivityFromViewPageBtn(Register1Activity.class);
				break;
			default:
				startActivityFromViewPageBtn(MainActivity.class);
				break;
			}
		}
    }
    
    private void startActivityFromViewPageBtn(Class cls){
    	Intent intent = new Intent(this, cls);
    	this.startActivity(intent);
    	overridePendingTransition(R.anim.activity_new_right_in, R.anim.activity_new_left_out);
    	//this.finish();
    }
    
}
