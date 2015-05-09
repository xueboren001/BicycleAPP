package com.boren.bicycle.wegdit;

import com.boren.bicycle.R;
import com.boren.bicycle.util.LogUtil;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SwitchCountDownTimerTvBtn extends RelativeLayout{
	
	private Context context;
	
	private TypedArray typedArray;
	private CountDownTimer countDownTimer;
	//内部的两个控件
	private TextView tv;
	private Button btn;
	//内部控件相关属性
	private String text_tv, text_btn;
	private String text_tv_str;
	//计时器的两个属性
	private int count_millisInFuture, count_countDownInterval;

	public SwitchCountDownTimerTvBtn(Context context) {
		super(context);
	}
	public SwitchCountDownTimerTvBtn(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		
		LayoutInflater li = LayoutInflater.from(context);
		RelativeLayout relativeLayout = (RelativeLayout) li.inflate(R.layout.wegdit_switch_countdowntv_button, null);
		tv = (TextView) relativeLayout.findViewById(R.id.wegdit_switchtimertvbtn_tv);
		btn = (Button) relativeLayout.findViewById(R.id.wegdit_switchtimertvbtn_btn);
		text_tv = tv.getText().toString(); 
		text_btn = btn.getText().toString();
		
		typedArray = context.obtainStyledAttributes(R.styleable.SwitchCountDownTimerTvBtn);
		String text_tv_str0 = typedArray.getString(R.styleable.SwitchCountDownTimerTvBtn_br_stb_tv_text);
		String text_btn_str0 = typedArray.getString(R.styleable.SwitchCountDownTimerTvBtn_br_stb_btn_text);
		count_countDownInterval = typedArray.getInt(R.styleable.SwitchCountDownTimerTvBtn_br_stb_count_millisInFuture, 1000);
		count_millisInFuture = typedArray.getInt(R.styleable.SwitchCountDownTimerTvBtn_br_stb_count_countDownInterval, 60000);
		text_tv = (text_tv_str==null || text_tv_str0.equals("")) ? text_tv : text_tv_str0 ;
		text_btn = ( text_btn_str0==null || text_btn_str0.equals("")) ? text_btn : text_btn_str0;
		
		LogUtil.toast(context, text_tv+"   "+text_btn+"   "+count_countDownInterval+"   "+count_millisInFuture);
		
		tv.setText(text_tv);		btn.setText(text_btn);
		text_tv_str = text_tv;
		
		countDownTimer = new MyCount(count_millisInFuture, count_countDownInterval);
		countDownTimer.start();
		
		//android.view.ViewGroup.LayoutParams params = (LayoutParams) relativeLayout.getLayoutParams();
		//params.height = 30;
		//relativeLayout.setLayoutParams(params);
		this.addView(relativeLayout);
		typedArray.recycle();
	}
	public SwitchCountDownTimerTvBtn(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public Button getClickButton(){
		return btn;
	}
	
	public void showTextView(){
		btn.setVisibility(View.GONE);
		tv.setVisibility(View.VISIBLE);
		countDownTimer.start();
	}
	
	/**自定义一个继承CountDownTimer的内部类，用于实现计时器的功能*/
	private class MyCount extends CountDownTimer{
		/**
		 * MyCount的构造方法
		 * @param millisInFuture 要倒计时的时间
		 * @param countDownInterval 时间间隔
		 */
		private long millisInFuture = 60000;
		private long countDownInterval = 1000;
		private long perSecond = 1000;
		public MyCount(long millisInFuture, long countDownInterval){
			super(millisInFuture, countDownInterval);
			perSecond = countDownInterval;
			tv.setVisibility(View.VISIBLE);
			btn.setVisibility(View.GONE);
		}
		@Override
		public void onTick(long millisUntilFinished) { //在进行倒计时的时候执行的操作
			long second = (millisUntilFinished / perSecond);
			/*if(TextUtils.isEmpty(text_tv_str)){
				int i = text_tv.indexOf('{');
				int j = text_tv.indexOf('}');
				if(i != -1 && j != -1){
					String sub = text_tv.substring(i, j);
					text_tv = text_tv.replace(sub, "{1}");
					text_tv_str = text_tv.substring(j,text_tv.length());
				}
			}*/
			String resultStr = (text_tv_str.replace("{1}", String.valueOf((second-1))));
			//LogUtil.toast(context, "=====  "+text_tv_str+"   "+resultStr);
			tv.setText(resultStr);
			//tv.setText((second-1) +" 秒后可重新获取验证码");
		}
		@Override
		public void onFinish() { //倒计时结束后要做的事情
			tv.setVisibility(View.GONE);
			btn.setVisibility(View.VISIBLE);
		}
	}
	
	
}
