package com.boren.bicycle.wegdit;

import java.util.ArrayList;
import java.util.List;

import com.boren.bicycle.R;
import com.boren.bicycle.model.User;
import com.boren.bicycle.text.TextWatcherFactory;
import com.boren.bicycle.util.LogUtil;
import com.boren.bicycle.util.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;

public class LoginLinearLayout extends LinearLayout implements OnClickListener, OnDismissListener, OnItemClickListener{
	
	private Context context;
	
	private LinearLayout ll_login_top_parent;
	private LinearLayout ll_login;
	private LinearLayout ll_usrename_layout;
	private ImageView iv_more_user;
	private EditText et_username, et_password;
	private TextWatcher tw_et_username, tw_et_password;
	private String username, password; //实时监控 et_username、et_password组件，实时赋值
	
	private PopupWindow pw_user_listview;
	private ArrayAdapter moreAdapter;
	private ListView lv_more_users;
	private ArrayList<User> userList;
	

	public LoginLinearLayout(Context context) {
		this(context, null);
		this.context = context;
	}
	public LoginLinearLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.context = context;
	}
	public LoginLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
		//1.初始化组件
		initViews();
		//2.初始化组件的监听器
		initViewListeners();
		//3.初始化 ListView组件
		initListViewMore();
		initPop();
		
		this.addView(ll_login_top_parent);
	}
	
	//从 wegdit_login_linearlayout.xml布局文件中获取
	private void initViews(){
		LayoutInflater inflater = LayoutInflater.from(context);
		ll_login_top_parent = (LinearLayout) inflater.inflate(R.layout.wegdit_login_linearlayout, null);
		ll_login = (LinearLayout) ll_login_top_parent.findViewById(R.id.wegdit_login_login_LinearLayout);
		ll_usrename_layout = (LinearLayout) ll_login_top_parent.findViewById(R.id.wegdit_username_LinearLayout);
		et_username = (EditText) ll_login_top_parent.findViewById(R.id.wegdit_login_et_username);
		et_password = (EditText) ll_login_top_parent.findViewById(R.id.wegdit_login_et_password);
		iv_more_user = (ImageView) ll_login_top_parent.findViewById(R.id.wegdit_login_iv_more_user);
	}
	
	private void initListViewMore(){
		LayoutInflater inflater = LayoutInflater.from(context);
		LinearLayout lv_parent = (LinearLayout) inflater.inflate(R.layout.wegdit_listview, null);
		lv_more_users = (ListView) lv_parent.findViewById(R.id.wegdit_login_lv_more_users);
		lv_parent.removeView(lv_more_users);
		
		userList = Utils.getUserList(context);
		if (userList.size() > 0) { /* 将列表中的第一个user显示在编辑框 */
			et_username.setText(userList.get(0).getUsername());
			et_password.setText(userList.get(0).getPassword());
		}
		
		moreAdapter = new MyAdapter(userList);
		//moreAdapter.f
		lv_more_users.setOnItemClickListener(this);
		lv_more_users.setAdapter(moreAdapter);
		//lv_more_users.setFilterText("");
	}
	
	private void initPop(){
		int width = ll_usrename_layout.getWidth() - 4;
		int height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		LogUtil.toastTest(context, "===PopWidth: "+width);
		pw_user_listview = new PopupWindow(lv_more_users, width, height, true);
		pw_user_listview.setOnDismissListener(this);
		// 注意要加这句代码，点击弹出窗口其它区域才会让窗口消失
		pw_user_listview.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
	}
	
	private void initViewListeners(){
		tw_et_username = TextWatcherFactory.createUsernameWatcher(context);
		tw_et_password = TextWatcherFactory.createPasswordWatcher(context);
		et_username.addTextChangedListener(tw_et_username);
		et_password.addTextChangedListener(tw_et_password);
		iv_more_user.setOnClickListener(this);
		et_password.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void afterTextChanged(Editable s) {	
				password = s.toString();
				LogUtil.toast(context, "-------  : "+password);
			}
		});
		et_username.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void afterTextChanged(Editable s) {	
				username = s.toString();
				LogUtil.toast(context, "----- :"+username);
			}
		});
		et_username.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					LogUtil.toastTest(context, "et_username--has focused!");
				}
			}
		});
	}
	
	private void showPopWindowForMore(){
		if (pw_user_listview == null) {
			initPop();
		}
		if (!pw_user_listview.isShowing() && userList.size() > 0) {
			// Log.i(TAG, "切换为角向上图标");
			//pw_user_listview.setWidth();
			//LogUtil.toastTest(context, "PopWidh= : "+pw_user_listview.getWidth()+"   "+ll_usrename_layout.getWidth());
			pw_user_listview.setWidth(ll_usrename_layout.getWidth()-4);
			iv_more_user.setImageResource(R.drawable.login_more_down); // 切换图标
			pw_user_listview.showAsDropDown(ll_usrename_layout, 2, 1); // 显示弹出窗口
			//pw_user_listview.showAtLocation(ll_login, Gravity.NO_GRAVITY, 0, 0);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.wegdit_login_iv_more_user:
			//显示 用户选择列表，供用户选择
			showPopWindowForMore();
			break;
		}
	}
	
	@Override// 用户选择列表消失时，把图片设置为 R.drawable.login_more_up
	public void onDismiss() {
		iv_more_user.setImageResource(R.drawable.login_more_up);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//自动填充 用户名输入框 和 密码输入框 
		et_username.setText(userList.get(position).getUsername());
		et_password.setText(userList.get(position).getPassword());
		pw_user_listview.dismiss();
	}
	
	
	
	private class MyAdapter extends ArrayAdapter<User>{
		private List<User> users;
		public MyAdapter(List<User> objects) {
			super(context, 0, objects);
			this.users = objects;
		}
		public MyAdapter(Context context, int textViewResourceId,List<User> objects) {
			super(context, textViewResourceId, objects);
			this.users = objects;
		}
		@Override
		public User getItem(int position) {
			return users.get(position);
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.wegdit_listview_item, null);
				
			}
			TextView usernameText = (TextView) convertView.findViewById(R.id.wegdit_listview_item_tv_username);
			usernameText.setText(getItem(position).getUsername());
			ImageView deleteUser = (ImageView) convertView.findViewById(R.id.wegdit_listview_item_iv_delete_user);
			deleteUser.setOnClickListener(new OnClickListener() {
				@Override  // 点击删除deleteUser时,在mUsers中删除选中的元素
				public void onClick(View v) {
					if (getItem(position).getUsername().equals(username)) {
						// 如果要删除的用户Id和Id编辑框当前值相等，则清空
						username = "";
						password = "";
						et_username.setText(username);
						et_password.setText(password);
					}
					userList.remove(getItem(position));
					moreAdapter.notifyDataSetChanged(); // 更新ListView
				}
			});
			return convertView;
		}
		
		
		
	}



	public EditText getEt_username() {
		return et_username;
	}
	public void setEt_username(EditText et_username) {
		this.et_username = et_username;
	}
	public EditText getEt_password() {
		return et_password;
	}
	public void setEt_password(EditText et_password) {
		this.et_password = et_password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ArrayList<User> getUserList() {
		return userList;
	}
	
}
