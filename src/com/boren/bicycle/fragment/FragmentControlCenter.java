package com.boren.bicycle.fragment;

import java.util.HashMap;
import java.util.Map;

import com.boren.bicycle.util.CommonLog;
import com.boren.bicycle.util.LogFactory;

import android.content.Context;

public class FragmentControlCenter {

	private static final CommonLog log = LogFactory.createLog();
	
	private static  FragmentControlCenter instance;
	private Context mContext;
	private FragmentBuilder builder;
	
	private Map<String, FragmentModel> mFragmentModelMaps = new HashMap<String, FragmentModel>();

	private FragmentControlCenter(Context context) {
		this.mContext = context;
		this.builder = new FragmentBuilder();
	}
	
	public static synchronized FragmentControlCenter getInstance(Context context) {
		if (instance == null){
			instance  = new FragmentControlCenter(context);
		}
		return instance;
	}
	
	public FragmentModel getAppHomeFragmentModel(){
		FragmentModel fragmentModel = mFragmentModelMaps.get(FragmentBuilder.APPHOME_FRAGMENT);
		if(fragmentModel == null){
			fragmentModel = builder.getAppHomeFragmentModel();
			mFragmentModelMaps.put(FragmentBuilder.APPHOME_FRAGMENT, fragmentModel);
		}
		return fragmentModel;
	}
	
	public FragmentModel getMyHomeFragmentModel(){
		FragmentModel fragmentModel = mFragmentModelMaps.get(FragmentBuilder.MYHOME_FRAGMENT);
		if(fragmentModel == null){
			fragmentModel = builder.getMyHomeFragmentModel();
			mFragmentModelMaps.put(FragmentBuilder.MYHOME_FRAGMENT, fragmentModel);
		}
		return fragmentModel;
	}
	
	public FragmentModel getSearchFragmentModel(){
		FragmentModel fragmentModel = mFragmentModelMaps.get(FragmentBuilder.SEARCH_FRAGMENT);
		if(fragmentModel == null){
			fragmentModel = builder.getSearchFragmentModel();
			mFragmentModelMaps.put(FragmentBuilder.SEARCH_FRAGMENT, fragmentModel);
		}
		return fragmentModel;
	}
	
	public FragmentModel getAroundFragmentModel(){
		FragmentModel fragmentModel = mFragmentModelMaps.get(FragmentBuilder.AROUND_FRAGMENT);
		if(fragmentModel == null){
			fragmentModel = builder.getAroundFragmentModel();
			mFragmentModelMaps.put(FragmentBuilder.AROUND_FRAGMENT, fragmentModel);
		}
		return fragmentModel;
	}
	
	
	public FragmentModel getFragmentModel(String name){
		return mFragmentModelMaps.get(name);
	}

	public void addFragmentModel(String name,FragmentModel fragment){
		mFragmentModelMaps.put(name, fragment);
	}
	
	
	public class FragmentBuilder{
		
		public static final String MYHOME_FRAGMENT = "MYHOME_FRAGMENT";
		public static final String SEARCH_FRAGMENT = "SEARCH_FRAGMENT";
		public static final String AROUND_FRAGMENT = "AROUND_FRAGMENT";
		public static final String APPHOME_FRAGMENT = "APPHOME_FRAGMENT";
		
		public FragmentBuilder getBuilder(){
			return new FragmentBuilder();
		}
		
		public FragmentModel getAppHomeFragmentModel(){
			AppHomeFragment fragment = new AppHomeFragment(mContext);
			FragmentModel fragmentModel = new FragmentModel("首页",fragment);
			return fragmentModel;
		}
		
		public FragmentModel getMyHomeFragmentModel(){
			MyHomeFragment fragment = new MyHomeFragment(mContext);
			FragmentModel fragmentModel = new FragmentModel("个人中心",fragment);
			return fragmentModel;
		}
		
		public FragmentModel getAroundFragmentModel(){
			AroundFragment fragment = new AroundFragment(mContext);
			FragmentModel fragmentModel = new FragmentModel("周边",fragment);
			return fragmentModel;
		}
		
		public FragmentModel getSearchFragmentModel(){
			SearchFragment fragment = new SearchFragment(mContext);
			FragmentModel fragmentModel = new FragmentModel("搜索",fragment);
			return fragmentModel;
		}
		
	}
}
