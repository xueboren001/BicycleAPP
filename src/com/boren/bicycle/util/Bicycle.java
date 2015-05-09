package com.boren.bicycle.util;
/*
 * 定义一些APP全局变量 
 */
public class Bicycle {
	
	public static final String TAG = "BicycleAppTAG";
	
	//从sharedprefences中取值，用来判断是否是第一次启动，否则不用显示 启动 ViewPager
	public static final String ViewPagerHasInited = "ViewPagerHasInited"; 
	
	public static final String app_sharedPreferences_name = "bicycle";
	public static final String app_login_ok_users_json_file = "userinfo.json"; // �û������ļ���,���ڵ�¼ʱѡ���ĸ��û���¼
	
	
	/*
	 * 与服务器请求的所有 URL
	 */
	public static class URLS{
		private static final String app_host = "xueboren001.duapp.com";
		private static final String app_port = "80";
		private static final String app_url_base = "http://"+app_host+":"+app_port;
		//登陆模块
		public static final String url_login = app_url_base + "/login";
		//注册模块
		public static final String url_register_getCode = app_url_base+"/getValideCode";
		public static final String url_set_register_assword = app_url_base+"/setRegisterPassword";
		//忘记密码模块
		public static final String url_forgot_password_get_code = app_url_base+"/getResetPassowrdValidCode";
		public static final String url_forgot_password_reset_pass = app_url_base+"/resetPasswordByCellphone";
		//修改密码模块
		public static final String url_change_password_by_old_pass = app_url_base+"/resetPasswordByOldPassword";
		
	}
	
	/*
	 * Message.what : 用来使 Message唯一 
	 */
	public static class MESSAGEWHAT{
		//注册模块
		public static final int MESSAGE_WHAT_GET_REGISTRE_CODE = 1;
		public static final int MESSAGE_WHAT_SET_PASSWORD_CODE = 2;
		//忘记密码模块
		public static final int WHAT_FORGOT_PASS_GET_VALID_CODE = 11;
		public static final int WHAT_FORGOT_PASS_RESET_PASS = 12;
		//修改密码
		public static final int WHAT_RESET_PASSWORD_BY_OLD_PASS = 13;
		
	}
	
	
	
	
	
	
	
	
	
	/*
		//登陆模块相关请求
		loging : username(其实是loginname) password
		//注册模块相关请求
		getValideCode : cellphone	region_code   //注册帐号时，发送用户验证码，并返回给android客户端用来做下一步的验证
		setRegisterPassword: cellphone password //注册时设置密码
		//忘记密码模块相关请求
		getResetPassowrdValidCode: cellphone  region_code  //通过手机验证的方式找回密码
		resetPasswordByCellphone: cellphone password	   //修改密码（通过手机验证的方式 ）
		//修改密码模块相关请求
		resetPasswordByOldPassword: loginname oldPassword newPassword  //修改密码（通过密码认证的方式）
	*/
	
	
}
