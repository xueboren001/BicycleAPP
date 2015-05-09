package com.boren.bicycle.activity;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import com.boren.bicycle.R;
import com.boren.bicycle.model.RequestResult;
import com.boren.bicycle.model.User;
import com.boren.bicycle.text.TextWatcherFactory;
import com.boren.bicycle.util.Bicycle;
import com.boren.bicycle.util.LogUtil;
import com.boren.bicycle.util.ThreadUtil;
import com.boren.bicycle.wegdit.LoadingDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotPass3Activity extends Activity implements OnClickListener{
	
	//注册界面头部的 3 个 View
	private Button btn_title_left, btn_title_right;
	private TextView tv_top_title;
	
	private EditText et_password, et_password2;
	private Button btn_set_password;
	private Context context;
	
	private LoadingDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bicycle_forgotpass_activity_second);
		context = this;
		initViews();
	}
	
	private void initViews(){
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);
		tv_top_title = (TextView) findViewById(R.id.tv_title);
		btn_title_left.setOnClickListener(this);
		btn_title_right.setVisibility(View.GONE);
		
		et_password = (EditText) findViewById(R.id.et_password);
		et_password2 = (EditText) findViewById(R.id.et_password_confirm);
		btn_set_password = (Button) findViewById(R.id.btn_set_password);
		btn_set_password.setOnClickListener(this);
		
		TextWatcher passwordTextWatcher = TextWatcherFactory.createPasswordWatcher(this);
		et_password.addTextChangedListener(passwordTextWatcher);
	    et_password2.addTextChangedListener(passwordTextWatcher);
	}
	
	private void setPassword(){
		/*
		 * 弹出对话框并设置 遮罩 ，防止 在设置password的过程中，用户修改了密码
		 */
		et_password.setEnabled(false);
		et_password2.setEnabled(false);
		String password = et_password.getText().toString();
		String password2 = et_password2.getText().toString();
		
		boolean valid = localValidPassword(password, password2);
		if(valid){
			SharedPreferences sp = this.getSharedPreferences(Bicycle.app_sharedPreferences_name, Context.MODE_PRIVATE);
			String cellphone = sp.getString("register_cellphone", null);
			setPasswordService(cellphone,password);
		}else{
			et_password.setEnabled(true);
			et_password2.setEnabled(true);
		}
	}
	
	private void setPasswordService(String cellphone, String password){
		/*
		 * 真正设置密码（与服务器端交互）的功能实现代码 
		 */
		showLoginingDlg();
		URL url = null;
		try{
			url = new URL(Bicycle.URLS.url_set_register_assword+"?cellphone="+cellphone+"&password="+password);
		}catch(MalformedURLException e){
			e.printStackTrace();
		}
		Runnable getCodeRunnable = new ThreadUtil.GetCodeThread(url, handler, Bicycle.MESSAGEWHAT.MESSAGE_WHAT_SET_PASSWORD_CODE);
		new Thread(getCodeRunnable).start();
	}
	
	private void finishMyself(){
		this.finish();
	}
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==Bicycle.MESSAGEWHAT.MESSAGE_WHAT_SET_PASSWORD_CODE){
				RequestResult result = (RequestResult) msg.obj;
				if(result.isResultOk()){
					closeLoginingDlg();
					JSONObject obj = result.getResultDataObject();
					User user = User.parseJSON(obj);
					Intent intent = new Intent(context, ForgotPass4Activity.class);
					context.startActivity(intent);
					finishMyself();
					//context.finish();
				}else{
					closeLoginingDlg();
					LogUtil.toastCustome(context, result.getResultMessage(), Color.RED);
				}				
			}
		}
	};
	
	private boolean localValidPassword(String password, String password2){
		String tip = "";
		if(TextUtils.isEmpty(password)){
			tip = "密码不能为空！";
			et_password.requestFocus();
		}else if(password.length() < 6){ //密码不能少于6位
			tip = "密码不能少于6位！";
			et_password.requestFocus();
		}else if(TextUtils.isEmpty(password2)){
			tip = "请再次输入确认密码！";
			et_password2.requestFocus();
		}else if(!password.equals(password2)){
			tip = "再次输入不一致！";
			et_password2.requestFocus();
		}else{
			return true;
		}
		LogUtil.toast(this, tip);
		return false;
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_title_left:
			this.finish();
			break;
		case R.id.btn_set_password:
			setPassword();
			break;
		}
	}
	
	private void showLoginingDlg(){
		dialog = new LoadingDialog(this,"正在设置密码...");
		dialog.show();
	}
	
	private void closeLoginingDlg(){
		if(dialog!=null && dialog.isShowing())
			dialog.dismiss();
	}
	
}
