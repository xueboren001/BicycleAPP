package com.boren.bicycle.activity;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.boren.bicycle.R;
import com.boren.bicycle.model.RequestResult;
import com.boren.bicycle.util.Bicycle;
import com.boren.bicycle.util.LogUtil;
import com.boren.bicycle.util.ThreadUtil;
import com.boren.bicycle.wegdit.LoadingDialog;
import com.boren.bicycle.wegdit.SwitchCountDownTimerTvBtn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotPass2Activity extends Activity implements OnClickListener{
	
	private Context context;
	//注册界面头部的 3 个 View
	private Button btn_title_left, btn_title_right;
	private TextView tv_top_title;
	
	private EditText et_code;
	
	private Button btn_valid_geted_code;
	private CountDownTimer cd;
	
	private LoadingDialog dialog;
	private String cellphone;
	private SwitchCountDownTimerTvBtn switch_countDownTvBtn;
	private Button btn_switch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bicycle_forgotpass_activity_first_2);
		context = this;
		initViews();
	}
	
	
	private void initViews(){
		et_code = (EditText) findViewById(R.id.boren_register_valid_code);
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);
		tv_top_title = (TextView) findViewById(R.id.tv_title);
		btn_title_left.setOnClickListener(this);
		btn_title_right.setVisibility(View.GONE);
		
		switch_countDownTvBtn = (SwitchCountDownTimerTvBtn) findViewById(R.id.boren_switch_count_down_timer);
		btn_switch = switch_countDownTvBtn.getClickButton();
		btn_switch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				repeartGetCode();
			}
		});
		
		btn_valid_geted_code = (Button) findViewById(R.id.btn_valid_geted_code);
		btn_valid_geted_code.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_title_left:
			this.finish();
			break;
		case R.id.btn_valid_geted_code:
			validGetedCode();
			break;
		}
	}
	
	/**
	 * 确认验证码是否正确
	 */
	private void validGetedCode(){
		String code = et_code.getText().toString();
		SharedPreferences sp = this.getSharedPreferences(Bicycle.app_sharedPreferences_name, Context.MODE_PRIVATE);
		String spCode = sp.getString("code", "");
		if(!spCode.equals("") && code.equals(spCode)){
			Intent intent = new Intent(this, ForgotPass3Activity.class);
			this.startActivity(intent);
			this.finish();
		}else{
			LogUtil.toastCustome(this, "验证码输入有误!");
		}
	}
	
	private void repeartGetCode(){ //重新获取验证码
		showLoginingDlg();
		URL url = null;
		SharedPreferences sp = this.getSharedPreferences(Bicycle.app_sharedPreferences_name, Context.MODE_PRIVATE);
		cellphone = sp.getString("register_code","");
		if(cellphone.equals("")){
			this.finish();
			return;
		}
		try{
			url = new URL(Bicycle.URLS.url_register_getCode+"?cellphone="+cellphone);
		}catch(MalformedURLException e){
			e.printStackTrace();
		}
		Runnable getCodeRunnable = new ThreadUtil.GetCodeThread(url, handler, Bicycle.MESSAGEWHAT.MESSAGE_WHAT_GET_REGISTRE_CODE);
		new Thread(getCodeRunnable).start();
	}
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==Bicycle.MESSAGEWHAT.MESSAGE_WHAT_GET_REGISTRE_CODE){
				RequestResult result = (RequestResult) msg.obj;
				if(result.isResultOk()){
					closeLoginingDlg();
					LogUtil.toastCustome(context, result.getResultMessage(), Color.GREEN);
					JSONObject obj = result.getResultDataObject();
					if(obj!=null){
						String code = "";
						try {
							code = obj.getString("code");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						SharedPreferences sp = context.getSharedPreferences(Bicycle.app_sharedPreferences_name, Context.MODE_PRIVATE);
						Editor spe = sp.edit();
						spe.putString("register_code", code);
						spe.commit();
						et_code.setText("");
						switch_countDownTvBtn.showTextView();
					}
				}else{
					LogUtil.toastCustome(context, result.getResultMessage());
				}			
			}
		}
	};
	
	private void showLoginingDlg(){
		dialog = new LoadingDialog(this,"正在获取验证码...");
		dialog.show();
	}
	
	private void closeLoginingDlg(){
		if(dialog!=null && dialog.isShowing())
			dialog.dismiss();
	}
	
}
