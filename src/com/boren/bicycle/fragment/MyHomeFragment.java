package com.boren.bicycle.fragment;

import com.boren.bicycle.R;
import com.boren.bicycle.activity.ForgotPass1Activity;
import com.boren.bicycle.activity.MainActivity;
import com.boren.bicycle.activity.MyAddressActivity;
import com.boren.bicycle.activity.MyCollectActivity;
import com.boren.bicycle.wegdit.LineSetting;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class MyHomeFragment extends Fragment implements OnClickListener{
	
	private Context context;
	
	private LineSetting ls_collect, ls_address, ls_change_password;
	
	public MyHomeFragment(Context context){
		this.context = context;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_myhome, null);
		
		ls_collect = (LineSetting) view.findViewById(R.id.boren_myhome_collect);
		ls_address = (LineSetting) view.findViewById(R.id.boren_myhome_address);
		ls_change_password = (LineSetting) view.findViewById(R.id.boren_myhome_change_password);
		
		ls_address.setOnClickListener(this);
		ls_collect.setOnClickListener(this);
		ls_change_password.setOnClickListener(this);
		
		return view;
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.boren_myhome_collect:
			goNewActivity(MyCollectActivity.class);
			break;
		case R.id.boren_myhome_address:
			goNewActivity(MyAddressActivity.class);
			break;
		case R.id.boren_myhome_change_password:
			goNewActivity(ForgotPass1Activity.class);
			break;
		}
	}
	
	private void goNewActivity(Class clazz){
		MainActivity main = ((MainActivity)context);
		main.goNewActivity(clazz);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
