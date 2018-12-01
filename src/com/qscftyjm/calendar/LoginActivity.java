package com.qscftyjm.calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.FlagToString;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import postutil.AsynTaskUtil;
import postutil.AsynTaskUtil.AsynNetUtils.Callback;
import sqliteutil.SQLiteHelper;
import tools.MD5Util;
import tools.ParamToJSON;
import tools.TimeUtil;

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
					final String account =input_account;
					final String password=input_password;

					
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
											
											Toast.makeText(LoginActivity.this, "��ӭ "+data.optString("UserName")+" ��������ת��������......", Toast.LENGTH_SHORT).show();
											SQLiteHelper sqLiteHelper=new SQLiteHelper(LoginActivity.this, "calendar.db", null, 1);
											SQLiteDatabase database=sqLiteHelper.getWritableDatabase();
											boolean isExist=false;
											Cursor cursor = database.query("logininfo", new String[] {"id","username","account"}, new String("account = ?"), new String[] { account }, null, null, null, null);
											int count=0;
											if(cursor.moveToFirst()) {
												count=cursor.getCount();
												if(count>0) {
													//��������
													isExist=true;
													ContentValues values=new ContentValues();
													values.put("lastchecktime", TimeUtil.getTime());
													values.put("password", password);
													values.put("username", data.optString("UserName","000000"));
													database.update("logininfo", values, "account = ?", new String[] { account });
													Log.d("Calendar", "�����˺����� "+account);
													
												}
											}
											cursor.close();
											if(!isExist) {
												//��������
												ContentValues values = new ContentValues();
								                values.put("account",account);
								                values.put("password",password);
								                values.put("username",data.optString("UserName","000000"));
								                values.put("priority",data.optInt("Priority", 0));
								                values.put("lastchecktime",TimeUtil.getTime());
								                long id=database.insert("logininfo",null,values);
								                
								                Log.d("Calendar", "����˺����� "+account);
											}
											
											Intent intent=new Intent(LoginActivity.this, MainActivity.class);
											startActivity(intent);
											finish();
										}else if(status==1) {
											Toast.makeText(LoginActivity.this, "�˺Ż������������������", Toast.LENGTH_SHORT).show();
										}else {
											Toast.makeText(LoginActivity.this, "δ֪����1�����Ժ�����", Toast.LENGTH_SHORT).show();
										}
									} else {
										Toast.makeText(LoginActivity.this, "δ֪����0�����Ժ�����", Toast.LENGTH_SHORT).show();
									}
									
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}else {
								Toast.makeText(LoginActivity.this, "����������������������", Toast.LENGTH_SHORT).show();
							}
							
						}
					});
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
				Toast.makeText(LoginActivity.this, "��������-�ù��ܽ��ں����Ƴ��������ڴ�......", Toast.LENGTH_SHORT).show();
				
			}
		});
		
	}

	
}
