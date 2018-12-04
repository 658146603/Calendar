package com.qscftyjm.calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import postutil.AsynTaskUtil.AsynNetUtils;
import postutil.AsynTaskUtil.AsynNetUtils.Callback;
import sqliteutil.SQLiteHelper;
import tools.ParamToJSON;
import tools.StringCollector;

public class ChangePasswordActivity extends Activity {

	private EditText et_acccount;
	private EditText et_oldPassword;
	private EditText et_newPassword;
	private EditText et_confirmPassword;
	private Button bt_submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		et_acccount=(EditText)findViewById(R.id.change_account);
		et_oldPassword=(EditText)findViewById(R.id.change_old_password);
		et_newPassword=(EditText)findViewById(R.id.change_new_password);
		et_confirmPassword=(EditText)findViewById(R.id.change_password_confirm);
		bt_submit=(Button)findViewById(R.id.submit_change_password);
		bt_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String account=et_acccount.getText().toString();
				String oldPassword=et_oldPassword.getText().toString();
				String newPassword=et_newPassword.getText().toString();
				String confirmPassword=et_confirmPassword.getText().toString();
				if(!account.equals("")&&!oldPassword.equals("")&&!newPassword.equals("")&&!confirmPassword.equals("")) {
					if(newPassword.equals(confirmPassword)) {
						AsynNetUtils.post(StringCollector.GetServer(), ParamToJSON.formCangePasswordJson(account, oldPassword, newPassword), new Callback() {

							@Override
							public void onResponse(String response) {
								// TODO Auto-generated method stub
								if(response!=null) {
									try {
										JSONObject jsonObj=new JSONObject(response);
										int status=jsonObj.optInt("Status", -1);
										if(status==0) {
											Toast.makeText(ChangePasswordActivity.this, "�޸�����ɹ��������µ�¼", Toast.LENGTH_SHORT).show();
											SQLiteHelper dbHelper=new SQLiteHelper(ChangePasswordActivity.this, "calendar.db", null, 1);
											SQLiteDatabase database = dbHelper.getWritableDatabase();
											ContentValues values=new ContentValues();
											values.put("password", "000000");
											database.update("logininfo", values, "account = ?", new String[] { account });
											finish();
										} else {
											Toast.makeText(ChangePasswordActivity.this, "ԭ�˺Ż��������������", Toast.LENGTH_SHORT).show();
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {
									Toast.makeText(ChangePasswordActivity.this, "���������쳣��������������", Toast.LENGTH_LONG).show();
								}
							}
							
						});
					} else {
						Toast.makeText(ChangePasswordActivity.this, "�����벻һ�£�", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(ChangePasswordActivity.this, "����д�������ϵ���Ϣ��", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		
	}
	
}
