package com.boren.bicycle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	
	
	public static void main(String[] args){
    	Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");     
        Matcher m = p.matcher("18717198971");
        System.out.println(m.matches());
    }
	
	
	
}
