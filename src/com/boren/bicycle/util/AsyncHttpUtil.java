package com.boren.bicycle.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.AsyncTask;

public class AsyncHttpUtil extends AsyncTask<Object, Object, Object>{
	
	private String urlStr;
	
	@Override
	protected Object doInBackground(Object... params) {
		String strResult = "";
		urlStr = params[0].toString();
		HttpGet httpRequest = new HttpGet(urlStr);
		try{
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if( httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK ){
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}else{
				strResult = "�������";
			}
			LogUtil.toast(null, strResult);
			return strResult;
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;
	}
	
	@Override
	protected void onCancelled(Object result) {
		super.onCancelled(result);
	}
	
	@Override
	protected void onPreExecute() {
		LogUtil.toast(null, "��ʼ HTTP GET ����......   "+urlStr);
	}
	
	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);
	}
	
	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		try{
			JSONObject json = new JSONObject(result.toString()).getJSONObject("result");
			String username = json.getString("username");
			String password = json.getString("password");
			LogUtil.toast(null, " username: "+username+"  password:"+password);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}