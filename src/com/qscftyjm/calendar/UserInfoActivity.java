package com.qscftyjm.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import listviewadapter.ListViewUtil;
import listviewadapter.UserInfoAdapter;
import sqliteutil.SQLiteHelper;
import tools.TimeUtil;

public class UserInfoActivity extends Activity {

	private Button btn_gotoLogin;
	private LinearLayout LinearLocal;
	
	
	private LinearLayout LinearLogin;
	private ListView list_userinfo;
	private UserInfoAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		btn_gotoLogin=(Button)findViewById(R.id.btn_to_login_page);
		LinearLocal=(LinearLayout)findViewById(R.id.userinfo_local);
		LinearLogin=(LinearLayout)findViewById(R.id.userinfo_login);
		list_userinfo=(ListView)findViewById(R.id.list_userinfo);
		SQLiteHelper sqLiteHelper=new SQLiteHelper(UserInfoActivity.this, "calendar.db", null, 1);
		SQLiteDatabase database=sqLiteHelper.getWritableDatabase();
		
		Cursor cursor = database.query("logininfo", new String[] { "id", "account", "username", "priority", "lastchecktime","password" }, null, null, null, null, null, null);
		int count=0;
		if(cursor.moveToFirst()) {
			count=cursor.getCount();
			if(count>0) {
				LinearLocal.setVisibility(View.GONE);
				LinearLogin.setVisibility(View.VISIBLE);
				ArrayList<Map<String, Object>> userArray=new ArrayList<Map<String, Object>>();
				do {
					int id=cursor.getInt(0);
					String account=cursor.getString(1);
					String username=cursor.getString(2);
					int priority=cursor.getInt(3);
					String lastchecktime=cursor.getString(4);
					boolean isOverTime=cursor.getString(5).equals("000000");
					Map<String, Object> tempMap=new HashMap<String, Object>();
					tempMap.put("id", id);
					tempMap.put("account", account);
					tempMap.put("username", username);
					tempMap.put("priority", priority);
					tempMap.put("lastchecktime", lastchecktime);
					tempMap.put("overtime", isOverTime);
					userArray.add(tempMap);
					//list_userinfo=ListViewUtil.UserInfoAdapter(UserInfoActivity.this, list_userinfo, userArray);
				} while(cursor.moveToNext());
				adapter=new UserInfoAdapter(UserInfoActivity.this, userArray);
				list_userinfo.setAdapter(adapter);
				list_userinfo.setOnItemClickListener(new OnItemClickListener() {
					
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
						View v=view;
						Toast.makeText(UserInfoActivity.this, "第 "+position+" 项被点击", Toast.LENGTH_SHORT).show();
						
					}
				});
			}else {
				LinearLogin.setVisibility(View.GONE);
				LinearLocal.setVisibility(View.VISIBLE);
			}
		}
		
		
		
		btn_gotoLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent(UserInfoActivity.this, LoginActivity.class);
				startActivity(intent);
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.action_settings:
			Toast.makeText(UserInfoActivity.this, "设置", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.menu_user_info_add_user:
			Intent intent1=new Intent(UserInfoActivity.this, LoginActivity.class);
			startActivity(intent1);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
