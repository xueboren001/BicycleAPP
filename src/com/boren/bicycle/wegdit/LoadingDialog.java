package com.boren.bicycle.wegdit;

import com.boren.bicycle.R;
import com.boren.bicycle.util.LogUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class LoadingDialog extends Dialog{
	
	private Context context;
	private TextView tv_loadingText;
	public static String bodyStrDefault = "数据加载中...";
	private String bodyStr = bodyStrDefault;
	
	public LoadingDialog(Activity context) {
		this(context,bodyStrDefault,R.style.loginingDlg);
	}
	
	public LoadingDialog(Activity context, String bodyStr) {
		this(context, bodyStr, R.style.loginingDlg);
	}
	
	public LoadingDialog(Activity context,String bodyStr, int themeStyle) {
		super(context, themeStyle);
		this.context = context;
		this.bodyStr = bodyStr;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.logining_dlg);
		
		tv_loadingText = (TextView) this.findViewById(R.id.wegdit_logingDialog_tv);
		tv_loadingText.setText(bodyStr);
		
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		// 获取和mLoginingDlg关联的当前窗口的属性，从而设置它在屏幕中显示的位置
		// 获取屏幕的高宽
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = ((Activity)context).getWindowManager(); 
		Display display = wm.getDefaultDisplay();
		display.getMetrics(dm);
		int cxScreen = dm.widthPixels;
		int cyScreen = dm.heightPixels;
	LogUtil.toastTest(context, " LoadingDialog...: "+cxScreen+" "+cyScreen+"     "+display.getWidth()+"  "+display.getHeight());
		// 高42dp
		int height = (int) context.getResources().getDimension(	R.dimen.loginingdlg_height);
		// 左右边沿10dp
		int lrMargin = (int) context.getResources().getDimension(R.dimen.loginingdlg_lr_margin); 
		// 上沿20dp
		int topMargin = (int) context.getResources().getDimension(R.dimen.loginingdlg_top_margin); 

		params.y = (-(cyScreen - height) / 2) + topMargin; // -199
		params.y = 0;//正中间
		params.y = (-(cyScreen - height) / 4) + topMargin;
		
		/* 对话框默认位置在屏幕中心,所以x,y表示此控件到"屏幕中心"的偏移量 */

		params.width = cxScreen;
		params.height = height;
		LogUtil.toastTest(context, "LoadingDialog... : "+params.y + "   "+params.width+"   "+params.height);
		window.setAttributes(params);
		// width,height表示mLoginingDlg的实际大小

		setCanceledOnTouchOutside(false); // 设置点击Dialog外部任意区域关闭Dialog
		
	}
	
	public void setBodyStr(String bodyStr){
		this.bodyStr = bodyStr;
		tv_loadingText.setText(this.bodyStr);
	}
	
	public void setBodyStrColor(int color){
		tv_loadingText.setTextColor(color);
	}
	
	
}
