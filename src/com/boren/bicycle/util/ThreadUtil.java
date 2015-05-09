package com.boren.bicycle.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.boren.bicycle.model.RequestResult;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ThreadUtil {
	
	public static class GetCodeThread implements Runnable{
		private final Handler handler;
		private int msgWhat;
		private URL url;
		public GetCodeThread(URL url, Handler handler, int msgWhat){
			super();
			this.url = url;
			this.handler = handler;
			this.msgWhat = msgWhat;
		}
		@Override
		public void run() {
			Log.i(Bicycle.TAG, "---URL===: "+url.toString());
			HttpURLConnection conn = null;
			InputStream is = null;
			try{
				Log.i(Bicycle.TAG, "--- before url.openConnection");
				conn = (HttpURLConnection) url.openConnection();
				Log.i(Bicycle.TAG, "--- alfter url.openConnection");
				conn.setRequestMethod("POST");
				conn.setReadTimeout(10000);
				int responseCode = conn.getResponseCode();
				if(responseCode == HttpURLConnection.HTTP_OK){
					is = conn.getInputStream();
				}
				JSONObject json = NetUtil.parseJSON(is);
				RequestResult result = NetUtil.parseJSONToResult(json);
				Message msg = new Message();
				msg.what = msgWhat;
				msg.obj = result;				
				handler.sendMessage(msg);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
