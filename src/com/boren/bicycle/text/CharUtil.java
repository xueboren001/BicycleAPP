package com.boren.bicycle.text;

import com.boren.bicycle.util.LogUtil;

public class CharUtil {
	
			
	public static char[] generateCharsNumber(){
		StringBuilder sb = new StringBuilder();
		for(int i = '0'; i <= '9'; i++){
			sb.append((char)i);
		}
		return sb.toString().toCharArray();
	}
	
	public static char[] generateCharsLowerLetters(){
		StringBuilder sb = new StringBuilder();
		for(int i = 'a'; i <= 'z'; i++){
			sb.append((char)i);
		}
		return sb.toString().toCharArray();
	}
	
	public static char[] merge(char[]... chs){
		if(chs == null || chs.length == 0){
			return null;
		}
		return mergeNotNull(chs);
	}
	
	public static char[] mergeNotNull(char[]... chs){
		String resultStr = "";
		for(int i = 0; i < chs.length; i++){
			resultStr += String.valueOf(chs[i]);
		}
		return resultStr.toCharArray();
	}
	
	
	public static char[] generateCharsUpperLetters(){
		StringBuilder sb = new StringBuilder();
		for(int i = 'A'; i <= 'Z'; i++){
			sb.append((char)i);
		}
		return sb.toString().toCharArray();
	}
	
	public static char[] generateCharsLatters(){
		return mergeNotNull(generateCharsLowerLetters(), generateCharsUpperLetters());
	}
	
	public static char[] generateCharsLattersAndNumber(){
		return mergeNotNull(generateCharsNumber(), generateCharsLatters());
	}
	
	public static boolean charIsValid(char c, char[] chars, int allowMode){
		if(allowMode==TextWatcherFactory.MODE_ALLOW && charIsInChars(c, chars)){
			return true;
		}
		if(allowMode==TextWatcherFactory.MODE_NOT_ALLOW && !charIsInChars(c, chars)){
			return true;
		}
		return false;
	}
	
	public static boolean charIsInChars(char c, char[] chars){
		if(chars == null){ return false; }
		LogUtil.toast(null, "%%%   "+String.valueOf(chars));
		for(char c2 : chars){
			if(c2 == c){
				LogUtil.toast(null, "%%%  "+String.valueOf(c)+"   "+(c==c2));
				return true;
			}
		}
		return false;
	}
	
}
/*public static String generateCharsStringLowerLetters(){
StringBuilder sb = new StringBuilder();
for(int i = 'a'; i <= 'z'; i++){
	sb.append((char)i);
}
return sb.toString();
}
public static String generateCharsStringNumber(){
StringBuilder sb = new StringBuilder();
for(int i = '0'; i <= '9'; i++){
	sb.append((char)i);
}
return sb.toString();
}
public static String generateCharsStringUpperLetters(){
StringBuilder sb = new StringBuilder();
for(int i = 'A'; i <= 'Z'; i++){
	sb.append((char)i);
}
return sb.toString();
}*/