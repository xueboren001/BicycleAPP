package com.boren.bicycle.activity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONObject;

import com.boren.bicycle.R;
import com.boren.bicycle.model.User;
import com.boren.bicycle.util.Bicycle;
import com.boren.bicycle.util.LogUtil;
import com.boren.bicycle.util.NetUtil;
import com.boren.bicycle.util.Utils;
import com.boren.bicycle.wegdit.LoadingDialog;
import com.boren.bicycle.wegdit.LoginLinearLayout;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener{
	
	//protected static final String TAG = "BicycleLoginActivity";
	private LinearLayout mLoginLinearLayout; // 登录内容的容器(包含 自定义  LoginLinearLayout与登录按钮)
	private Dialog mLoginingDlg; // 显示正在登录的Dialog
	private Button mLoginButton; // 登录按钮
	boolean mIsSave = true; // 当前登录成功的用户是否需要保存（若已经存在，则不需要保存）
	private Button btn_notLogin, btn_register;
	private TextView tv_forgotPass; //忘记密码
	
	private LoginLinearLayout my_login_linearlayout;
	private EditText et_username, et_password;
	private String username,password;
	
	private NetUtil netUtil;
	private SharedPreferences sp;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		this.context = this;
		
		netUtil = new NetUtil(this);
		sp = getSharedPreferences(Bicycle.app_sharedPreferences_name, Context.MODE_PRIVATE);
		initView();
		setListener();
		initAndStartLoginAnimation();//mLoginLinearLayout
	}

	private void initView() {
		mLoginButton = (Button) findViewById(R.id.login_btnLogin);
		btn_notLogin = (Button) findViewById(R.id.boren_btn_not_login);
		btn_register = (Button) findViewById(R.id.boren_btn_register);
		tv_forgotPass = (TextView) findViewById(R.id.login_txtForgotPwd);
		
		mLoginLinearLayout = (LinearLayout) findViewById(R.id.login_linearLayout);
		my_login_linearlayout = (LoginLinearLayout) findViewById(R.id.login_my_wegdit_login_linearlayou);
		et_username = my_login_linearlayout.getEt_username();
		et_password = my_login_linearlayout.getEt_password();
		initLoginingDlg();
	}
	
	private void setListener() {
		mLoginButton.setOnClickListener(this);
		btn_notLogin.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		tv_forgotPass.setOnClickListener(this);
	}
	
	public void initAndStartLoginAnimation(){//mLoginLinearLayout
		AnimatorSet set = new AnimatorSet();
		ObjectAnimator oa = ObjectAnimator.ofFloat(btn_register, "alpha", 0.0f, 1.0f);
		oa.setDuration(5000);
		ObjectAnimator oa2 = ObjectAnimator.ofFloat(btn_notLogin, "alpha", 0.0f, 1.0f);
		oa2.setDuration(5000);
		set.playTogether(oa2, oa);
		set.start();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.boren_btn_not_login:
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);		this.finish();
			break;
		case R.id.boren_btn_register:
			Intent intentRegister = new Intent(this, Register1Activity.class);
			this.startActivity(intentRegister);		this.finish();
			break;
		case R.id.login_btnLogin:
			login();
			break;
		case R.id.login_txtForgotPwd:
			Intent i = new Intent(this,ForgotPass1Activity.class);
			startActivity(i);
			break;
		default:
			break;
		}
	}
	
	private void login(){ // 启动登录
		if(!netUtil.netIsOpen()){
			LogUtil.toast(this, "未联网,请先检查网络设置");
			return;
		}
		username = et_username.getText().toString();
		password = et_password.getText().toString();
		if(TextUtils.isEmpty(username) ){
			LogUtil.toast(this, "登录名不能为空!");
			return;
		}
		if(TextUtils.isEmpty(password)){
			LogUtil.toast(this, "用户密码不能为空");
			return;
		}
		showLoginingDlg(); // 显示"正在登录"对话框,因为此Demo没有登录到web服务器,所以效果可能看不出.可以结合情况使用
		try {
			for (User user : my_login_linearlayout.getUserList()) { // 判断本地文档是否有此ID用户
				if (user.getUsername().equals(username)) {
					mIsSave = false;
					break;
				}
			}
			new Thread(){
				@Override
				public void run() {
					Looper.prepare();
					try {
						Thread.currentThread();
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					login_net(username, password);
					Looper.loop();
				}
			}.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void login_net(String username, String password){
		String urlStr = Bicycle.URLS.url_login +"?username="+username+"&password="+password;
		//urlStr = "http://192.168.1.104:8080/BicycleWeb/logintest";
		LogUtil.toast(this, urlStr);
		InputStream is = null;
		HttpURLConnection conn = null;
		String resultMessage = "登录失败！";
		try{
			URL loginUrl = new URL(urlStr);
			conn = (HttpURLConnection) loginUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(5000);
			int responseCode = conn.getResponseCode();
			if(responseCode == HttpURLConnection.HTTP_OK){ //200
				is = conn.getInputStream();
				JSONObject json = NetUtil.parseJSON(is);
				boolean resultOk = json.getBoolean("resultOk");
				resultMessage = json.getString("resultMessage");
				LogUtil.toast(this, resultOk+"   "+resultMessage);
				if(resultOk){ //登录成功 : 把用户名记入 SharedPreferences 中，
					LogUtil.toast(this, "登录成功！");
					ArrayList<User> mUsers = my_login_linearlayout.getUserList();
					JSONObject data = json.getJSONObject("resultData");
					JSONObject user = data.getJSONObject("user");
					Editor spe = sp.edit();
					spe.putInt("user_id", user.getInt("id"));
					spe.putString("user_login_name", username);
					spe.putString("user_name", user.getString("name"));
					spe.putString("user_nick_name", user.getString("nickName"));
					spe.putString("user_cellphone", user.getString("cellphone"));
					spe.putString("user_email", user.getString("email"));
					spe.putString("user_password", user.getString("password"));
					spe.putString("user_create_time", user.getString("createTime"));
					spe.putString("user_laste_update_time", user.getString("lasteUpdatedTime"));
					spe.commit();
					if (mIsSave) { // 将新用户加入users
						User tempUser = new User(username, password);
						mUsers.add(tempUser);
					}
					Utils.saveUserList(this, mUsers);
					//mLoginingDlg.dismiss();
					closeLoginingDlg();// 关闭对话框
					Intent intent = new Intent(this, MainActivity.class);
					this.startActivity(intent);
					this.finish();
				}else{ //登录失败
					LogUtil.toast(this, resultMessage);
					mLoginingDlg.dismiss();
				}
			}else{
				LogUtil.toast(this, "请求失败!请重新发送请求");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if( conn!= null){
				conn.disconnect();
			}
		}
	}
	
	
	
	/* 初始化正在登录对话框 */
	private void initLoginingDlg() {
		mLoginingDlg = new LoadingDialog(this);
	}

	/* 显示正在登录对话框 */
	private void showLoginingDlg() {
		if (mLoginingDlg != null && !mLoginingDlg.isShowing())
			mLoginingDlg.show();
	}

	/* 关闭正在登录对话框 */
	private void closeLoginingDlg() {
		if (mLoginingDlg != null && mLoginingDlg.isShowing())
			mLoginingDlg.dismiss();
	}
	

}
