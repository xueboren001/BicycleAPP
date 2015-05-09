package com.boren.bicycle.fragment;

import com.boren.bicycle.R;
import com.boren.bicycle.activity.MainActivity;
import com.boren.bicycle.util.LogUtil;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class NavigationFragment extends Fragment implements OnCheckedChangeListener, OnClickListener{

	private Context context;
	
	private View mView;
	private RadioGroup  m_radioGroup;
	private ImageButton m_AddButton;
	
	private FragmentControlCenter mControlCenter;
	
	public NavigationFragment(Context context){
		this.context = context;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mControlCenter = FragmentControlCenter.getInstance(getActivity());
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.toastTest(context, "NavigationFragment onDestroy");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.navitation_channel_layout, null);
		return mView;	
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtil.toastTest(context, "NavigationFragment onActivityCreated");
		setupViews();
	}
	
	
	private void setupViews(){
		m_radioGroup = (RadioGroup) mView.findViewById(R.id.nav_radiogroup);
		((RadioButton) m_radioGroup.getChildAt(0)).toggle();
		
		m_radioGroup.setOnCheckedChangeListener(this);
		
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int id) {
		switch(id){
		case R.id.boren_rb_app_home:
			goAppHomeFragment();
			break;
		case R.id.boren_rb_my_home:
			goMyHomeFragment();
			break;
		case R.id.boren_rb_around:
			goAroundFragment();
			break;
		case R.id.boren_rb_search:
			goSearchFragment();
			break;
		}
	}
	
	private void goAppHomeFragment(){
		if (getActivity() == null)
			return;
		FragmentModel fragmentModel = mControlCenter.getAppHomeFragmentModel();
		if(getActivity() instanceof MainActivity){
			MainActivity ra = (MainActivity) getActivity();
			ra.switchContent(fragmentModel);
		}
	}
	
	private void goMyHomeFragment(){
		if (getActivity() == null)
			return;
		FragmentModel fragmentModel = mControlCenter.getMyHomeFragmentModel();
		if(getActivity() instanceof MainActivity){
			MainActivity ra = (MainActivity) getActivity();
			ra.switchContent(fragmentModel);
		}
	}
	
	private void goAroundFragment(){
		if (getActivity() == null)
			return;
		FragmentModel fragmentModel = mControlCenter.getAroundFragmentModel();
		if(getActivity() instanceof MainActivity){
			MainActivity ra = (MainActivity) getActivity();
			ra.switchContent(fragmentModel);
		}
	}
	
	private void goSearchFragment(){
		if (getActivity() == null)
			return;
		FragmentModel fragmentModel = mControlCenter.getSearchFragmentModel();
		if(getActivity() instanceof MainActivity){
			MainActivity ra = (MainActivity) getActivity();
			ra.switchContent(fragmentModel);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		}
	}
	
}
