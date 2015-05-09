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
import com.boren.bicycle.util.Utils;
import com.boren.bicycle.wegdit.LoadingDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotPass1Activity extends Activity implements OnClickListener,OnCheckedChangeListener{
	
	private static final int REGION_SELECT = 1; //用于标识 区域选择 Dialog : onCreateDialog(int id, Bundle args)
	private Context context; 
	//注册界面头部的 3 个 View
	Button btn_title_left, btn_title_right;
	TextView tv_top_title;
	
	Button btn_get_code;
	EditText et_cellphone;
	CheckBox cb_has_agree_note; //有状态监听器 this
	TextView tv_btn_regin_modify, tv_region_show;
	
	private LoadingDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bicycle_forgotpass_activity_first_1);
		context = this;
		initViews();
	}
	
	private void initViews(){
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);
		tv_top_title = (TextView) findViewById(R.id.tv_title);
		btn_title_right.setVisibility(View.GONE);
		btn_title_left.setOnClickListener(this);
		btn_title_right.setOnClickListener(this);
		//tv_top_title.setText("注册");
		
		btn_get_code = (Button) findViewById(R.id.btn_get_code);
		et_cellphone = (EditText) findViewById(R.id.et_mobileNo);
		cb_has_agree_note = (CheckBox) findViewById(R.id.chk_agree);
		tv_region_show = (TextView) findViewById(R.id.tv_region_show);
		tv_btn_regin_modify = (TextView) findViewById(R.id.tv_region_modify);
		
		cb_has_agree_note.setOnCheckedChangeListener(this);
		tv_btn_regin_modify.setOnClickListener(this);
		btn_get_code.setOnClickListener(this);
		setGetcodeButtonCannotClick(); //默认为不可 点击 状态，只有当 选中“已同意”并且手机号合法时才可点击
		et_cellphone.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,int count) {}
			@Override
			public void afterTextChanged(Editable s) {
				if(s.length() == 11){
					validAndSetGetcodeButtonCanClick();
				}else{ //设为不可用状态
					setGetcodeButtonCannotClick();
				}
			}
		});
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		case R.id.tv_region_modify:
			showDialog(REGION_SELECT);
			break;
		case R.id.btn_get_code:
			getCode();
			break;
		case R.id.btn_title_left:
			this.finish();
			break;
		case R.id.btn_title_right:
			break;
		}		
	}
	
	private void showLoginingDlg(){
		dialog = new LoadingDialog(this,"正在获取验证码...");
		dialog.show();
	}
	
	private void closeLoginingDlg(){
		if(dialog!=null && dialog.isShowing())
			dialog.dismiss();
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == Bicycle.MESSAGEWHAT.MESSAGE_WHAT_GET_REGISTRE_CODE){
				//处理获取注册手机验证码的请求响应
				final RequestResult rs = (RequestResult) msg.obj;
				LogUtil.toastTest(context, rs.toString());
				if(rs.isResultOk()){
					closeLoginingDlg();
					JSONObject obj = rs.getResultDataObject();
					if(obj!=null){
						String code = null;
						try {
							code = obj.getString("code");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						LogUtil.toastTest(context, "===return code is : "+code);
						SharedPreferences sp = context.getSharedPreferences(Bicycle.app_sharedPreferences_name, Context.MODE_PRIVATE);
						Editor spe = sp.edit();
						spe.putString("register_cellphone", et_cellphone.getText().toString());
						spe.putString("register_code", code);
						spe.commit();
					}
					Intent intent = new Intent(context, ForgotPass2Activity.class);
					startActivity(intent);
				}else{
					closeLoginingDlg();
					TextView tv = new TextView(context);
					tv.setBackgroundColor(Color.argb(100, 0, 0, 0));
					tv.setPadding(25,15,25,15);
					tv.setText(rs.getResultMessage());		tv.setTextColor(Color.RED);
					Toast toast = Toast.makeText(context, "=====", Toast.LENGTH_SHORT);
					toast.setView(tv);				toast.show();
				}
			}
		}
	};
	
	private void getCode(){
		showLoginingDlg();
		String region = tv_region_show.getText().toString();
		String region_code = region.substring(region.indexOf('+')+1, region.indexOf(' '));
		URL url = null;
		try{
			url = new URL(Bicycle.URLS.url_register_getCode+"?cellphone="+et_cellphone.getText().toString()+"&regionCode="+region_code);
			LogUtil.toastTest(context, Bicycle.URLS.url_register_getCode+"?cellphone="+et_cellphone.getText().toString()+"&regionCode="+region_code);
		}catch(MalformedURLException e){
			e.printStackTrace();
		}
		Runnable getCodeRunnable = new ThreadUtil.GetCodeThread(url, handler, Bicycle.MESSAGEWHAT.MESSAGE_WHAT_GET_REGISTRE_CODE);
		new Thread(getCodeRunnable).start();
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			validAndSetGetcodeButtonCanClick();
		}else{
			setGetcodeButtonCannotClick();
		}
	}
	
	private void validAndSetGetcodeButtonCanClick(){
		String cellphone = et_cellphone.getText().toString();
		boolean isAgree = cb_has_agree_note.isChecked();
		if( (cellphone.length() != 11) || !isAgree ){
			setGetcodeButtonCannotClick();
		}else{
			if(Utils.isMobileNO(cellphone)){
				btn_get_code.setAlpha(1.0f);
				btn_get_code.setClickable(true);
			}else{
				LogUtil.toast(this, "手机号不合法!");
			}
		}
	}
	
	private void setGetcodeButtonCannotClick(){
		btn_get_code.setAlpha(0.3f);
		btn_get_code.setClickable(false);
	}
	
	
	
	
	/**
	 * 重写了onCreateDialog方法来创建一个列表对话框
	 */
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch(id){
		case REGION_SELECT:
			final Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("请选择所在地");
			builder.setSingleChoiceItems(//第一个参数是要显示的列表，用数组展示；第二个参数是默认选中的项的位置；
					//第三个参数是一个事件点击监听器
					new String[]{"+86中国大陆","+853中国澳门","+852中国香港","+886中国台湾"},
					0, 
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							switch(which){
							case 0:
								tv_region_show.setText("+86 中国大陆");
								break;
							case 1:
								tv_region_show.setText("+853 中国澳门");
								break;
							case 2:
								tv_region_show.setText("+852 中国香港");
								break;
							case 3:
								tv_region_show.setText("+886 中国台湾");
								break;
							}
							dismissDialog(REGION_SELECT);//想对话框关闭
						}
					});
			return builder.create();
		}
		return null;
	}


	
	
	
}
