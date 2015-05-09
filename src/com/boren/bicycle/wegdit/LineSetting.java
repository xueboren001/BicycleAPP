package com.boren.bicycle.wegdit;

import com.boren.bicycle.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LineSetting extends RelativeLayout{
	
	private Context context;
	
	private TextView tv_text, tv_text_number;
	private ImageView iv_left;
	private ImageView iv_right;
	private Drawable d_left,d_right;
	private String str_tv_text = "设置";
	private String str_tv_text_number = "";
	
	public LineSetting(Context context, AttributeSet attrs ) {
		this(context, attrs, 0 );
	}

	public LineSetting(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LineSetting);
		int ta_count = ta.getIndexCount();
		for(int i = 0; i < ta_count; i++){
			int attrId = ta.getIndex(i);
			switch(attrId){
			case R.styleable.LineSetting_br_line_text:
				str_tv_text = ta.getString(attrId);
				break;
			case R.styleable.LineSetting_br_line_left_iv_background:
				d_left = ta.getDrawable(attrId);
				break;
			case R.styleable.LineSetting_br_line_right_iv_background:
				d_right = ta.getDrawable(attrId);
				break;
			case R.styleable.LineSetting_br_line_text_number:
				str_tv_text_number = ta.getString(attrId);
				break;
			}
		}
		
		View view = initView();
		this.addView(view);
	}
	

	private View initView(){
		LayoutInflater inflater = LayoutInflater.from(context);
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.wegdit_line_setting, null);
		RelativeLayout view = (RelativeLayout) root.findViewById(R.id.boren_rl_my_line);
		root.removeView(view);
		
		iv_left = (ImageView) view.findViewById(R.id.boren_rl_my_line_icon);
		tv_text = (TextView) view.findViewById(R.id.boren_rl_my_line_tv);
		tv_text_number = (TextView) view.findViewById(R.id.boren_rl_my_line_tv_number);
		iv_right = (ImageView) view.findViewById(R.id.boren_rl_my_line_iv);
		
		iv_left.setBackground(d_left);
		tv_text.setText(str_tv_text);
		tv_text_number.setText(str_tv_text_number);
		iv_right.setBackground(d_right);
		
		
		return view;
	}
	
	

}























