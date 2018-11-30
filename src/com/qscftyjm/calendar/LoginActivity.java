package com.qscftyjm.calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import postutil.AsynTaskUtil;
import postutil.AsynTaskUtil.AsynNetUtils.Callback;
import tools.MD5Util;
import tools.ParamToJSON;

public class LoginActivity extends Activity {

	private Button login_btn,register_btn,forget_password_btn;
	private EditText account_et,password_et;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		login_btn=(Button)findViewById(R.id.login_btn);
		register_btn=(Button)findViewById(R.id.register_btn);
		forget_password_btn=(Button)findViewById(R.id.forget_password_btn);
		account_et=(EditText)findViewById(R.id.login_account);
		password_et=(EditText)findViewById(R.id.login_password);
		
		login_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String input_account=account_et.getText().toString();
				String input_password=password_et.getText().toString();
				if(!input_account.equals("")&&!input_password.equals("")) {
					
					input_password=MD5Util.getMd5(input_password);
					//Toast.makeText(LoginActivity.this, "Account : "+input_account+" Password : "+input_password, Toast.LENGTH_SHORT).show();
					String account =input_account;
					String password=input_password;

					
					AsynTaskUtil.AsynNetUtils.post("http://192.168.42.252:8080/CalendarServer/CalendarPost", ParamToJSON.formLoginJson(account, password), new Callback() {
						
						@Override
						public void onResponse(String response) {
							// TODO Auto-generated method stub
							String result=response;
							//Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
							JSONObject jsonObj=null;
							if(response!=null) {
								try {
									jsonObj=new JSONObject(response);
									if(result!=null) {
										int status=jsonObj.optInt("Status");
										//Toast.makeText(LoginActivity.this, String.valueOf(status), Toast.LENGTH_SHORT).show();
										if(status==0) {
											JSONObject data=jsonObj.optJSONObject("Data");
											Toast.makeText(LoginActivity.this, "欢迎 "+data.optString("UserName")+" ！正在跳转到主界面......", Toast.LENGTH_SHORT).show();
											Intent intent=new Intent(LoginActivity.this, MainActivity.class);
											startActivity(intent);
											finish();
										}else if(status==1) {
											Toast.makeText(LoginActivity.this, "用户名或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
										}else {
											Toast.makeText(LoginActivity.this, "未知错误1，请稍后再试", Toast.LENGTH_SHORT).show();
										}
									} else {
										Toast.makeText(LoginActivity.this, "未知错误0，请稍后再试", Toast.LENGTH_SHORT).show();
									}
									
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}else {
								Toast.makeText(LoginActivity.this, "网络错误，请检查你的网络连接", Toast.LENGTH_SHORT).show();
							}
							
						}
					});
				}else {
					Toast.makeText(LoginActivity.this, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		register_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "注册账号-正在跳转...", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});
		
		forget_password_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "忘记密码-该功能将在后续推出，敬请期待......", Toast.LENGTH_SHORT).show();
				
			}
		});
		
	}

	
}
