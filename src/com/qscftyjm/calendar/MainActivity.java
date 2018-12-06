package com.qscftyjm.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import postutil.AsynTaskUtil.AsynNetUtils;
import postutil.AsynTaskUtil.AsynNetUtils.Callback;
import sqliteutil.SQLiteHelper;
import tools.AlertDialogUtil;
import tools.ParamToJSON;
import tools.StringCollector;
import tools.TimeUtil;

public class MainActivity extends Activity {

	private Button button1, button2, button3;
	private Button bt_tab[]=new Button[4];
	private LinearLayout linear[]=new LinearLayout[4];
	private ListView list_msg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
//		Bundle bundle = this.getIntent().getExtras();
//		if(bundle!=null&&bundle.containsKey("username")) {
//			
//			String username=bundle.getString("username");
//			String account=bundle.getString("account");
//			String prority=bundle.getString("priority");
//			Toast.makeText(this, 
//					"登录成功！\nusername : "+username
//					+ "\naccount : "+account
//					+ "\npriority : "+prority, Toast.LENGTH_SHORT).show();
//			
//		}
//		
//		if(!isServiceWork(MainActivity.this, "com.qscftyjm.calendar.GetGlobalMsgService")) {
//			Intent startGetglobalMsg=new Intent(MainActivity.this, GetGlobalMsgService.class);
//			startService(startGetglobalMsg);
//		}
		Intent startGetglobalMsg=new Intent(MainActivity.this, GetGlobalMsgService.class);
		startService(startGetglobalMsg);
		
		SQLiteHelper dbHelper=new SQLiteHelper(MainActivity.this, "calendar.db", null, SQLiteHelper.DB_VERSION);
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
						database.update("logininfo", values, "account = ?", new String[] { account });
						Log.d("Calendar", "设置用户登录数据 "+account+" 已过期");
						continue;
					}
					AsynNetUtils.post(StringCollector.GetServer(), ParamToJSON.formLoginJson(account, password), new Callback() {

						@Override
						public void onResponse(String response) {
							// TODO Auto-generated method stub
							JSONObject jsonObj=null;
							if(response!=null) {
								try {
									String result=response;
									
									jsonObj=new JSONObject(result);
									if(jsonObj.optInt("Status",-1)==0) {
										JSONObject data=jsonObj.optJSONObject("Data");
										ContentValues values=new ContentValues();
						                values.put("username",data.optString("UserName","000000"));
						                values.put("priority",data.optInt("Priority", 0));
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
				int msgid=0;
				cursor = database.query("message", new String[] { "msgid", "fromaccount", "sendtime", "content" }, null, null, null, null, null, null);
				if(cursor.moveToFirst()) {
					count=cursor.getCount();
					if(count>0) {
						
					}
					
				}
				
			}
		}
		
		
		button1=(Button)findViewById(R.id.button1);
		button2=(Button)findViewById(R.id.button2);
		button3=(Button)findViewById(R.id.button3);
		bt_tab[0]=(Button)findViewById(R.id.tab_btn_home);
		bt_tab[1]=(Button)findViewById(R.id.tab_btn_team);
		bt_tab[2]=(Button)findViewById(R.id.tab_btn_message);
		bt_tab[3]=(Button)findViewById(R.id.tab_btn_info);
		linear[0]=(LinearLayout)findViewById(R.id.main_linear_home);
		linear[1]=(LinearLayout)findViewById(R.id.main_linear_team);
		linear[2]=(LinearLayout)findViewById(R.id.main_linear_message);
		linear[3]=(LinearLayout)findViewById(R.id.main_linear_info);
		list_msg=(ListView)findViewById(R.id.list_msg);
		
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(MainActivity.this,CalenderActivity.class);
				startActivity(intent);
			}
		});
		
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,ChangePasswordActivity.class);
				startActivity(intent);
			}
		});
		
		bt_tab[0].setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				linear[0].setVisibility(View.VISIBLE);
				linear[1].setVisibility(View.GONE);
				linear[2].setVisibility(View.GONE);
				linear[3].setVisibility(View.GONE);
			}
		});
		
		bt_tab[1].setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				linear[1].setVisibility(View.VISIBLE);
				linear[0].setVisibility(View.GONE);
				linear[2].setVisibility(View.GONE);
				linear[3].setVisibility(View.GONE);
			}
		});
		
		bt_tab[2].setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				linear[2].setVisibility(View.VISIBLE);
				linear[0].setVisibility(View.GONE);
				linear[1].setVisibility(View.GONE);
				linear[3].setVisibility(View.GONE);
			}
		});
		
		bt_tab[3].setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				linear[3].setVisibility(View.VISIBLE);
				linear[0].setVisibility(View.GONE);
				linear[1].setVisibility(View.GONE);
				linear[2].setVisibility(View.GONE);
			}
		});
		
		button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialogUtil.makeAddItemChoiceDialog(MainActivity.this);
			}
		});
		
		
		
		
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
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
				Toast.makeText(MainActivity.this, "刷新中...", Toast.LENGTH_SHORT).show();
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public boolean isServiceWork(Context mContext, String serviceName) {
		boolean isWork = false;
		ActivityManager myAM = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> myList = myAM.getRunningServices(40);
		if (myList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < myList.size(); i++) {
			String mName = myList.get(i).service.getClassName().toString();
			if (mName.equals(serviceName)) {
				isWork = true;
				break;
			}
		}
		return isWork;
	}
	
	public class MyReceiver extends BroadcastReceiver {
	     @Override
	     public void onReceive(Context context, Intent intent) {
	      Bundle bundle=intent.getExtras();
	      
	     }
	}
	
	
}
