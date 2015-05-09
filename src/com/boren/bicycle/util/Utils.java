package com.boren.bicycle.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.boren.bicycle.model.User;


import android.content.Context;

public class Utils {

	
	/**
 	 * 手机号码的验证，严格验证
 	 * @param mobiles 要验证的手机号码
 	 * @return
 	 */
    public static boolean isMobileNO(String mobiles){     
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");     
        Matcher m = p.matcher(mobiles);            
        return m.matches();     
    }
    
    /**
     * E_mail的验证
     * @param email 要验证的email
     * @return
     */
    private static final int cellphone_max_length = 11;
    public static boolean isEmail(String email){     
     String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(email);            
        return m.matches();     
    } 
	
	
	
	/* 保存用户登录信息列表 */
	public static void saveUserList(Context context, ArrayList<User> users)
			throws Exception {
		Writer writer = null;
		OutputStream out = null;
		String jsonArrayStr = "";
		jsonArrayStr += "[";
		for (User user : users) {
			jsonArrayStr += user.toJSONStrOnlyUsernameAndPassword();
			LogUtil.toast(context, "=== "+users.indexOf(user) +"  "+ users.size());
			LogUtil.toast(context, "===== "+user.toJSONStrOnlyUsernameAndPassword());
			if(users.indexOf(user) < (users.size()-1)){
				jsonArrayStr += ",";
			}
		}
		jsonArrayStr += "]";
		try {
			out = context.openFileOutput(Bicycle.app_login_ok_users_json_file, Context.MODE_PRIVATE); // 覆盖
			writer = new OutputStreamWriter(out);
			writer.write(jsonArrayStr);
			writer.flush();
		} finally {
			if (writer != null)
				writer.close();
		}

	}

	/* 获取用户登录信息列表 */
	public static ArrayList<User> getUserList(Context context) {
		FileInputStream in = null;
		ArrayList<User> users = new ArrayList<User>();
		try {
			in = context.openFileInput(Bicycle.app_login_ok_users_json_file);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			JSONArray jsonArray = new JSONArray();
			String line;
			while ((line = reader.readLine()) != null) {
				LogUtil.toast(context, line);
				jsonString.append(line);
			}
			//String jsonString2 = "[{\"username\":\"xueboren\",\"password\":\"081008\"}]";
			jsonArray = (JSONArray) new JSONTokener(jsonString.toString())
					.nextValue(); // 把字符串转换成JSONArray对象
		LogUtil.toast(context, "**************  getUserList  **************************");
		//LogUtil.toast(context, jsonArray.length() + "  --   " + jsonArray.toString());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject userJson =  jsonArray.getJSONObject(i);
				User user = new User();
				user.setUsername(userJson.getString("username"));
				user.setPassword(userJson.getString("password"));
				//LogUtil.toast(context, user.getUsername()+"   "+user.getPassword());
				users.add(user);
			}
			for(User u : users){
				LogUtil.toast(context, users.size()+"-----------"+  u.getUsername()+"   "+u.getPassword());
			}
			//LogUtil.toast(context, "****************  getUserList   **************************");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}



}
