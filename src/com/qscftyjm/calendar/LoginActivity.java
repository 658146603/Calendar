package com.qscftyjm.calendar;

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
					Toast.makeText(LoginActivity.this, "Account : "+input_account+" Password : "+input_password, Toast.LENGTH_SHORT).show();
					//Map<String, Object> LoginResult=AccountChecker.UserLogin(input_account, input_password);
					//Toast.makeText(LoginActivity.this, (String)LoginResult.get("result"), Toast.LENGTH_SHORT).show();
					Toast.makeText(LoginActivity.this, (String)AccountChecker.UserLogin(input_account, input_password).get("result"), Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(LoginActivity.this, "�û��������벻��Ϊ�գ�", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		register_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "ע���˺�-������ת...", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});
		
		forget_password_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "��������-������ת...", Toast.LENGTH_SHORT).show();
				
			}
		});
		
	}

	
}
