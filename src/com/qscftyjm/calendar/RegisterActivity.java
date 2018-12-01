package com.qscftyjm.calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import postutil.AsynTaskUtil.AsynNetUtils;
import postutil.AsynTaskUtil.AsynNetUtils.Callback;
import tools.AlertDialogUtil;
import tools.MD5Util;
import tools.ParamToJSON;

public class RegisterActivity extends Activity {

	
	private EditText set_username;
	private EditText set_password;
	private EditText confirm_password;
	private Button submit_request;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		set_username=(EditText)findViewById(R.id.register_username);
		set_password=(EditText)findViewById(R.id.register_password);
		confirm_password=(EditText)findViewById(R.id.register_password_confirm);
		submit_request=(Button)findViewById(R.id.register_submit);
		
		submit_request.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String username=set_username.getText().toString();
				String password=set_password.getText().toString();
				String confirm=confirm_password.getText().toString();
				if(!username.equals("")&&!password.equals("")&&!confirm.equals("")) {
					if(password.equals(confirm)) {
						if(password.length()>=6&&password.length()<=18) {
							Toast.makeText(RegisterActivity.this, password, Toast.LENGTH_SHORT).show();
							
							AsynNetUtils.post("http://192.168.42.252:8080/CalendarServer/CalendarPost", ParamToJSON.formRegisterJson(username, MD5Util.getMd5(password)), new Callback() {

								@Override
								public void onResponse(String response) {
									// TODO Auto-generated method stub
									String result=response;
									JSONObject jsonObj=null;
									if(response!=null) {
										try {
											jsonObj=new JSONObject(result);
											int status=jsonObj.optInt("Status", -1);
											if(status==0) {
												JSONObject data=jsonObj.getJSONObject("Data");
												Toast.makeText(RegisterActivity.this, "ע��ɹ���������ת��¼����", Toast.LENGTH_SHORT).show();
												AlertDialogUtil.makeRegisterResultDialog(RegisterActivity.this, data.optString("Account","null"), data.optString("UserName", "null"));
												//finish();
											} else {
												Toast.makeText(RegisterActivity.this, "�����������������Ժ�����", Toast.LENGTH_SHORT).show();
											}
											
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									} else {
										Toast.makeText(RegisterActivity.this, "����������Ժ�����", Toast.LENGTH_SHORT).show();
									}
									
								}
							});
						}else {
							Toast.makeText(RegisterActivity.this, "���볤��Ӧ����6~18λ���������޸�����ǿ��", Toast.LENGTH_SHORT).show();
						}
						
					}else {
						Toast.makeText(RegisterActivity.this, "�������벻һ�£�����������", Toast.LENGTH_SHORT).show();
							set_password.setText("");
							confirm_password.setText("");
					}
						
				}else {
					Toast.makeText(RegisterActivity.this, "�û��������벻��Ϊ��", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		
		
	}

}
