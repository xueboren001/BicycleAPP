package com.boren.bicycle.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.boren.bicycle.model.RequestResult;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {

	private Context context = null;
	
	public NetUtil(Context context){
		this.context = context;
	}
	
	public static JSONObject parseJSON(InputStream is) throws Exception{
		List<Object> list = new ArrayList<Object>();
		byte[] data = read(is);
		String jsonStr = new String(data);
		JSONObject json = new JSONObject(jsonStr);
		return json;
	}
	
	public static RequestResult parseJSONToResult(JSONObject json){
		boolean resultOk = false;
		String resultMessage = "";
		JSONObject resultJSONObject = null;
		JSONArray resultJSONArray = null;
		try{
			resultOk = json.getBoolean("resultOk");
		}catch(JSONException e){}
		try{
			resultMessage = json.getString("resultMessage");
		}catch(JSONException e){}
		try{
			resultJSONObject = json.getJSONObject("resultData");
		}catch(JSONException e1){
			resultJSONObject = null;
		}
		try{
			resultJSONArray = json.getJSONArray("resultData");
		}catch(JSONException e){
			resultJSONArray = null;
		}
		RequestResult result = null;
		if(resultJSONObject == null && resultJSONArray==null){
			result =  new RequestResult(resultOk, resultMessage);
		}
		if(resultJSONObject != null){
			result = new RequestResult(resultOk, resultMessage, resultJSONObject);
		}
		if(resultJSONArray != null){
			result = new RequestResult(resultOk, resultMessage, resultJSONArray);
		}
		return result;
	}
	
	public static byte[] read(InputStream is) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len = is.read(buffer)) != -1){
			bos.write(buffer, 0, len);
		}
		return bos.toByteArray();		
	}
	
	public boolean netIsOpen(){
		if(context == null){ return false; }
		this.context = context;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if(info == null){  return false; }
		return true;
	}
	
	public String getNetTypeName(){
		if(context == null){ return null; }
		this.context = context;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if(info == null){  return null; }
		return info.getTypeName();
	}
	
	public int getNetType(){
		if(context == null){ return -1; }
		this.context = context;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if(info == null){  return -1; }
		return info.getType();
	}
	
	
	
}

















