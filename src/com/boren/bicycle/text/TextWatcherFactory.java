package com.boren.bicycle.text;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

public class TextWatcherFactory {
	
	public static int MODE_ALLOW = 1;
	public static int MODE_ALLOW_DEFAULT = MODE_ALLOW;
	public static int MODE_NOT_ALLOW = 2;
	//仅包含 英文字母(大、小写) 与 数字 
	public static char[] CHARS_ALLOW_DEFAULT = CharUtil.generateCharsLattersAndNumber(); 
	public static char[] CHARS_ALLOW_PASSWORD_DEFAULT = getDefaultPasswordAllowChars();
	public static char[] CHARS_ALLOW_USERNAME_DEFAULT = getDefaultUsernameAllowChars();
	
	public static char[] getDefaultPasswordAllowChars(){
		return CharUtil.mergeNotNull(CHARS_ALLOW_DEFAULT, new char[]{'@','#','$','%','&'});
	}
	
	public static char[] getDefaultUsernameAllowChars(){
		return CharUtil.mergeNotNull(CHARS_ALLOW_DEFAULT, new char[]{'@','#','$','%','&','.'});
	}
	
	public static TextWatcher createPasswordWatcher(Context context){
		return createPasswordWatcher(context, CHARS_ALLOW_PASSWORD_DEFAULT, TextWatcherFactory.MODE_ALLOW_DEFAULT);
	}
	public static TextWatcher createPasswordWatcher(Context context, char[] allowOrNotAllowChars, int allowMode){
		if(context == null){ return null; }
		int mode = (allowMode<1) ? MODE_ALLOW_DEFAULT : allowMode ;
		char[] chars = new char[]{};
		if(mode == MODE_ALLOW){
			chars = (allowOrNotAllowChars==null || allowOrNotAllowChars.length==0) ? CHARS_ALLOW_PASSWORD_DEFAULT : CharUtil.mergeNotNull(CHARS_ALLOW_DEFAULT, allowOrNotAllowChars);
		}else if(mode == MODE_NOT_ALLOW){
			chars = CharUtil.mergeNotNull(allowOrNotAllowChars);
		}
		TextWatcher ptw = new BorenTextWatcher(context, chars, mode);
		return ptw;
	}
	
	public static TextWatcher createUsernameWatcher(Context context){
		return createUsernameWatcher(context, TextWatcherFactory.CHARS_ALLOW_USERNAME_DEFAULT, TextWatcherFactory.MODE_ALLOW_DEFAULT);
	}
	public static TextWatcher createUsernameWatcher(Context context, char[] allowOrNotAllowChars, int allowMode){
		if(context == null){ return null; }
		int mode = (allowMode<1) ? MODE_ALLOW_DEFAULT : allowMode ;
		char[] chars = new char[]{};
		if(mode == MODE_ALLOW){
			chars = (allowOrNotAllowChars==null || allowOrNotAllowChars.length==0) ? CHARS_ALLOW_USERNAME_DEFAULT : CharUtil.mergeNotNull(CHARS_ALLOW_DEFAULT, allowOrNotAllowChars);
		}else if(mode == MODE_NOT_ALLOW){
			chars = CharUtil.mergeNotNull(allowOrNotAllowChars);
		}
		TextWatcher utw = new BorenTextWatcher(context, chars, mode);
		return utw;
	}
	
	public static TextWatcher createTextWatcher(Context context){
		return createTextWatcher(context, CHARS_ALLOW_DEFAULT, MODE_ALLOW_DEFAULT);
	}
	public static TextWatcher createTextWatcher(Context context, char[] allowOrNotAllowChars){
		return createTextWatcher(context, allowOrNotAllowChars, MODE_ALLOW_DEFAULT);
	}
	public static TextWatcher createTextWatcher(Context context, char[] allowOrNotAllowChars, int allowMode){
		if(context == null){ return null; }
		int mode = (allowMode<1) ? MODE_ALLOW_DEFAULT : allowMode ;
		char[] chars = new char[]{};
		if(mode == MODE_ALLOW){
			chars = (allowOrNotAllowChars==null || allowOrNotAllowChars.length==0) ? CHARS_ALLOW_DEFAULT : CharUtil.mergeNotNull(CHARS_ALLOW_DEFAULT, allowOrNotAllowChars);
		}else if(mode == MODE_NOT_ALLOW){
			chars = CharUtil.mergeNotNull(allowOrNotAllowChars);
		}
		TextWatcher utw = new BorenTextWatcher(context, chars, mode);
		return utw;
	}
	
	
	private static class BorenTextWatcher implements TextWatcher{
		private Context context;
		private char[] allowChars = null;
		private char[] notAllowChars = null;
		private int allow_mode = TextWatcherFactory.MODE_ALLOW_DEFAULT;
		public BorenTextWatcher(Context context, char[] allowOrNotAllowChars, int allowMode){
			//LogUtil.toast(context, "PasswordWatcher=== "+allowMode+"  "+String.valueOf(allowOrNotAllowChars));
			this.context = context;
			this.allow_mode = allowMode;
			if(allowMode == TextWatcherFactory.MODE_ALLOW){
				allowChars = allowOrNotAllowChars;
			}
			if(allowMode == TextWatcherFactory.MODE_NOT_ALLOW){
				notAllowChars = allowOrNotAllowChars;
			}
			//LogUtil.toast(context, "PasswordWatcher=== "+allow_mode+"  alllow:"+String.valueOf(allowChars)+"   notAllow:");
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			//LogUtil.toast(context, s+"   "+start+"   "+count+"   "+after);
		}
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			//LogUtil.toast(context, s+"   "+start+"   "+before+"   "+count);
		}
		@Override
		public void afterTextChanged(Editable s) {
			String str = s.toString();
			char[] chars = ( allow_mode == TextWatcherFactory.MODE_NOT_ALLOW ? notAllowChars : allowChars ); 
			char[] cs = str.toCharArray();
			for(char c : cs){
				if(!CharUtil.charIsValid(c, chars, allow_mode)){
					int i = str.indexOf(c);
					s.delete(i, i+1);
				}
			}
			//LogUtil.toast(context, s.toString());
		}
	} 
	

}
