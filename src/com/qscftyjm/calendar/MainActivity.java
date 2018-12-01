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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import postutil.AsynTaskUtil.AsynNetUtils;
import postutil.AsynTaskUtil.AsynNetUtils.Callback;
import sqliteutil.SQLiteHelper;
import tools.ParamToJSON;
import tools.TimeUtil;

public class MainActivity extends Activity {

	private Button button1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
//		Bundle bundle = this.getIntent().getExtras();
//		if(bundle!=null&&bundle.containsKey("username")) {
//			
//			String uesrname=bundle.getString("username");
//			String account=bundle.getString("account");
//			String prority=bundle.getString("priority");
//			Toast.makeText(this, 
//					"登录成功！\nusername : "+uesrname
//					+ "\naccount : "+account
//					+ "\npriority : "+prority, Toast.LENGTH_SHORT).show();
//			
//		}
		
		
		SQLiteHelper dbHelper=new SQLiteHelper(MainActivity.this, "calendar.db", null, 1);
		final SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query("logininfo", new String[] {"account","password","lastchecktime"}, null, null, null, null, null, null);
		int count=0;
		if(cursor.moveToFirst()) {
			count=cursor.getCount();
			if(count>0) {
				do {
					final String account=cursor.getString(0);
					String password=cursor.getString(1);
					if(password.equals("000000")) {
						//检测过期账户，过期的密码为000000
						Log.d("Calendar", "用户登录数据 "+account+" 已过期");
						continue;
					}
					if(TimeUtil.checkIsOverTime(cursor.getString(2))) {
						Toast.makeText(MainActivity.this, "用户登录数据 "+account+" 已过期，无法进行云同步", Toast.LENGTH_LONG).show();
						password="000000";
						ContentValues values=new ContentValues();
						values.put("password", password);
						//values.put("lastchecktime", TimeUtil.getTime());
						database.update("logininfo", values, "account = ?", new String[] { account });
						Log.d("Calendar", "设置用户登录数据 "+account+" 已过期");
						continue;
					}
					AsynNetUtils.post("http://192.168.42.252:8080/CalendarServer/CalendarPost", ParamToJSON.formLoginJson(account, password), new Callback() {

						@Override
						public void onResponse(String response) {
							// TODO Auto-generated method stub
							JSONObject jsonObj=null;
							if(response!=null) {
								try {
									String result=response;
									
									jsonObj=new JSONObject(result);
									if(jsonObj.optInt("Status",-1)==0) {
										ContentValues values=new ContentValues();
										values.put("lastchecktime", TimeUtil.getTime());
										database.update("logininfo", values, "account = ?", new String[] { account });
										Toast.makeText(MainActivity.this, "用户 "+account+" 的账号更新成功", Toast.LENGTH_SHORT).show();
										Log.d("Calendar", "用户 "+account+" 的账号更新成功");
									} else {
										Toast.makeText(MainActivity.this, "用户 "+account+" 的账号已不存在", Toast.LENGTH_LONG).show();
										Log.d("Calendar", "用户 "+account+" 的账号已不存在");
										database.execSQL("delete from logininfo where account = ?", new String[] { account });
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								Log.d("Calendar", "网络异常");
							}
							
							
						}
					});
					
				} while(cursor.moveToNext());
				cursor.close();
				
//				cursor=database.query("logininfo", new String[] { "lastchecktime" }, null, null, null, null, null, null);
//				if(cursor.moveToFirst()) {
//					int ct=cursor.getCount();
//					String lastchecktime=null;
//					do {
//						lastchecktime=cursor.getString(0);
//					}while(cursor.moveToNext());
//				}
//				cursor.close();
			}
		}
		
		
		button1=(Button)findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(MainActivity.this,CalenderActivity.class);
				startActivity(intent);
			}
		});
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id){
			case R.id.main_userItem:
				Log.i("Clander","UserInfo");
				Intent intent=new Intent(MainActivity.this,UserInfoActivity.class);
				startActivity(intent);
				break;
			
			case R.id.main_addItem:
				Log.i("Clander","ADD");
				break;
				
			case R.id.main_refreshItem:
				Log.i("Clander","REFRESH");
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
