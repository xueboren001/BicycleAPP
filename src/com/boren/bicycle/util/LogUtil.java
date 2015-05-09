package com.boren.bicycle.util;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class LogUtil {
	
	public static final void toastCustome(Context context,String msg){
		if(context != null){
			TextView tv = new TextView(context);
			tv.setText(msg);
			tv.setBackgroundColor(Color.argb(100, 0,0,0));
			tv.setTextColor(Color.RED);
			tv.setPadding(25,15,25,15);
			Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
			toast.setView(tv);
			toast.show();
		}
		Log.i(Bicycle.TAG, msg);
		System.out.println(msg);
	}
	
	public static final void toastCustome(Context context,String msg,int msgColor){
		if(context != null){
			TextView tv = new TextView(context);
			tv.setText(msg);
			tv.setBackgroundColor(Color.argb(100, 0,0,0));
			tv.setTextColor(msgColor);
			tv.setPadding(25,15,25,15);
			Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
			toast.setView(tv);
			toast.show();
		}
		Log.i(Bicycle.TAG, msg);
		System.out.println(msg);
	}
	
	public static final void toastTest(Context context, String msg){
		if(context != null){
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
		Log.i(Bicycle.TAG, msg);
		System.out.println(msg);
	}
	
	public static final void toast(Context context, String msg){
		if(context != null){
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
		Log.i(Bicycle.TAG, msg);
		System.out.println(msg);
	}
	
}
