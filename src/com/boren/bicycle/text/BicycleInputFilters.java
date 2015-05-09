package com.boren.bicycle.text;

import android.text.InputFilter;
import android.text.Spanned;

public class BicycleInputFilters {
	
	private InputFilter[] ifs_password[];
	
	public static boolean charIsNumber(char c){
		return ('0'<=c && c<='9');
	}
	
	public static boolean charIsLowercase(char c){
		return ('a'<=c && c<='z');
	}
	
	public static boolean charIsUppercase(char c){
		return ('A'<=c && c<='Z');
	}
	
	public static boolean charIsEnglishLetters(char c){
		return (charIsUppercase(c) || charIsLowercase(c));
	}
	
	public static class PasswordFilter implements InputFilter{
		private int maxLength = 20;
		private int minLength = 6;
		
		//允许的特殊字符
		public  char[] accepteChars = new char[]{'!','@','#','$','%','^','&','*'};
		
		private boolean ok(char[] cs, char c){
			if( charIsEnglishLetters(c) || charIsNumber(c) ){
				for(char ch : cs){
					if(ch == c){
						return true;
					}
				}
			}
			return false;
		}
		
		@Override
		public CharSequence filter(CharSequence source, int start, int end,
				Spanned dest, int dstart, int dend) {
			
			int i;
			for(i = start; i < end; i++){
				if(!ok(accepteChars, source.charAt(i))){
					break;
				}
			}
			
			if( i == end ){ //all ok!
				return null;
			}
			if( end-start == 1){ 
				return "";
			}
			
			
			
			
			return null;
		}
	} 

}
