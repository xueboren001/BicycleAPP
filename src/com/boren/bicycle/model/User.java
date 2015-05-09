package com.boren.bicycle.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.boren.bicycle.util.AESUtils;

public class User {
	
	private int id;
	private String nickName;
	private String username;
	private String name;
	private String password;
	private String cellphone;
	private String email;
	private Date createTime;
	private Date lasteUpdatedTime;
	private static final String masterPassword = "FORYOU"; // AES�����㷨������
	
	public User(){}
	
	public User(JSONObject jsonUsernameAndPassword) throws Exception {
		if (jsonUsernameAndPassword.has("username")) {
			String username = jsonUsernameAndPassword.getString("username");
			String password = jsonUsernameAndPassword.getString("password");
			// ���ܺ���
			this.username = AESUtils.decrypt(masterPassword, username);
			this.password = AESUtils.decrypt(masterPassword, password);
		}
	}
	
	public User(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public User(int id, String nickName, String username, String name,
			String password, String cellphone, String email) {
		super();
		this.id = id;
		this.nickName = nickName;
		this.username = username;
		this.name = name;
		this.password = password;
		this.cellphone = cellphone;
		this.email = email;
	}

	public User(int id, String nickName, String username, String name,
			String password, String cellphone, String email, Date createTime,
			Date lasteUpdatedTime) {
		super();
		this.id = id;
		this.nickName = nickName;
		this.username = username;
		this.name = name;
		this.password = password;
		this.cellphone = cellphone;
		this.email = email;
		this.createTime = createTime;
		this.lasteUpdatedTime = lasteUpdatedTime;
	}
	
	public String toJSONStrOnlyUsernameAndPassword(){
		String str = "{";
		str += "\"username\":\""+this.username+"\"";
		str += ",\"password\":\""+this.password+"\"";
		str += "}";
		return str;
	}
	
	public String toJSONStr(){
		String str = "{";
		str += "\"id\":\""+this.id+"\"";
		str += "\"username\":\""+this.username+"\"";
		str += ",\"nickName\":\""+this.nickName+"\"";
		str += ",\"name\":\""+this.name+"\"";
		str += ",\"password\":\""+this.password+"\"";
		str += ",\"cellphone\":\""+this.cellphone+"\"";
		str += ",\"email\":\""+this.email+"\"";
		str += ",\"createTime\":\""+this.createTime+"\"";
		str += ",\"lasteUpdatedTime\":\""+this.lasteUpdatedTime+"\"";
		str += "}";
		return str;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", nickName=" + nickName + ", username="
				+ username + ", name=" + name + ", password=" + password
				+ ", cellphone=" + cellphone + ", email=" + email
				+ ", createTime=" + createTime + ", lasteUpdatedTime="
				+ lasteUpdatedTime + "]";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLasteUpdatedTime() {
		return lasteUpdatedTime;
	}
	public void setLasteUpdatedTime(Date lasteUpdatedTime) {
		this.lasteUpdatedTime = lasteUpdatedTime;
	}
	
	@SuppressWarnings("deprecation")
	public static User parseJSON(JSONObject obj){
		int id = 0;
		String cellphone = "";
		String username = "";
		String nickname = "";
		String name = "";
		String email = "";
		String password = "";
		Date createTime = null;
		Date lastUpdatedTime = null;
		try{
			id = obj.getInt("id");
		}catch(JSONException e){
			id = 0;
		}
		try{
			cellphone = obj.getString("cellphone");
			cellphone = (cellphone.equals("null"))? "" : cellphone;
		}catch(JSONException e){
			cellphone = "";
		}
		try{
			username = obj.getString("username");
			username = (username.equals("null")) ? "" : username;
		}catch(JSONException e){
			username = "";			
		}
		try{
			nickname = obj.getString("nickname");
			nickname = (nickname.equals("null")) ? "" : nickname;
		}catch(JSONException e){
			nickname = "";
		}
		try{
			name = obj.getString("name");
			name = (name.equals("null")) ? "" : name;
		}catch(JSONException e){
			name = "";
		}
		try{
			email = obj.getString("email");
			email = (email.equals("null")) ? "" : email;
		}catch(JSONException e){
			email = "";
		}
		try{
			password = obj.getString("password");
			password = (password.equals("null")) ? "" : password;
		}catch(JSONException e){
			password = "";
		}
		try{
			String createTimeStr = obj.getString("createTime");
			if(createTimeStr.equals("null")){
				createTime = null;
			}else{
				SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
				createTime = format.parse(createTimeStr);
			}
		}catch(JSONException e){
			createTime = null;
		} catch (ParseException e) {
			createTime = null;
		}
		try{
			String lasteUpdatedTimeStr = obj.getString("lasteUpdatedTime");
			if(lasteUpdatedTimeStr.equals("null")){
				lastUpdatedTime = null;
			}else{
				SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
				lastUpdatedTime = format.parse(lasteUpdatedTimeStr);
			}
		}catch(JSONException e){
			lastUpdatedTime = null;
		}catch(ParseException e){
			lastUpdatedTime = null;
		}
		User user = new User(id, nickname, username, name, password, cellphone, email, createTime, lastUpdatedTime);
		return user;
	}
	
}
