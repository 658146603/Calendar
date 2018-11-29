package com.qscftyjm.calendar;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import postutil.AccountChecker;
import tools.MD5Util;

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
					JSONObject result=AccountChecker.UserLogin(input_account, input_password);
					
					if(result!=null) {
						int status=result.optInt("Status");
						//Toast.makeText(LoginActivity.this, String.valueOf(status), Toast.LENGTH_SHORT).show();
						if(status==0) {
							JSONObject data=result.optJSONObject("Data");
							Toast.makeText(LoginActivity.this, "欢迎 "+data.optString("UserName")+" ！正在跳转到主界面......", Toast.LENGTH_SHORT).show();
							Intent intent=new Intent(LoginActivity.this, MainActivity.class);
//							Bundle bundle=new Bundle();
//							bundle.putString("account", data.optString("Account"));
//							bundle.putString("priority", data.optString("Priority"));
//							bundle.putString("username", data.optString("UserName"));
//							intent.putExtras(bundle);
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
				Toast.makeText(LoginActivity.this, "忘记密码-正在跳转...", Toast.LENGTH_SHORT).show();
				
			}
		});
		
	}

	
}
