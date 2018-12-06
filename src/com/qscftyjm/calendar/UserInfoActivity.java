package com.qscftyjm.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import listviewadapter.UserInfoAdapter;
import sqliteutil.SQLiteHelper;

public class UserInfoActivity extends Activity {

	private Button btn_gotoLogin;
	private LinearLayout LinearLocal;
	
	
	private LinearLayout LinearLogin;
	private ListView list_userinfo;
	private UserInfoAdapter adapter;
	private ArrayList<Map<String, Object>> userArray;
	SQLiteHelper sqLiteHelper=null;
	SQLiteDatabase database=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		btn_gotoLogin=(Button)findViewById(R.id.btn_to_login_page);
		LinearLocal=(LinearLayout)findViewById(R.id.userinfo_local);
		LinearLogin=(LinearLayout)findViewById(R.id.userinfo_login);
		list_userinfo=(ListView)findViewById(R.id.list_userinfo);
		
		sqLiteHelper=new SQLiteHelper(UserInfoActivity.this, "calendar.db", null, SQLiteHelper.DB_VERSION);
		database=sqLiteHelper.getWritableDatabase();
		Cursor cursor = database.query("logininfo", new String[] { "id", "account", "username", "priority", "lastchecktime","password" }, null, null, null, null, null, null);
		int count=0;
		if(cursor.moveToFirst()) {
			count=cursor.getCount();
			if(count>0) {
				LinearLocal.setVisibility(View.GONE);
				LinearLogin.setVisibility(View.VISIBLE);
				userArray=new ArrayList<Map<String, Object>>();
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
						//View v=view;
						Toast.makeText(UserInfoActivity.this, "第 "+position+" 项被点击", Toast.LENGTH_SHORT).show();
						
					}
				});
				list_userinfo.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
					
					@Override
					public void onCreateContextMenu(ContextMenu menu, View v,
							ContextMenuInfo menuInfo) {
						// TODO 自动生成的方法存根
						menu.setHeaderTitle("长按菜单");
						menu.add(0, 0, 0, "查看详细信息");
						menu.add(0, 1, 0, "添加日程");
						menu.add(0, 2, 0, "注销登录");
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
	public boolean onContextItemSelected(MenuItem menuItem){
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuItem.getMenuInfo();
		int index=(int) info.id;
		//Toast.makeText(UserInfoActivity.this, "长按点击了第"+index+"条的第"+menuItem.getItemId()+"项", Toast.LENGTH_SHORT).show();
		String optAccount=userArray.get(index).get("account").toString();
		String optUsername=userArray.get(index).get("username").toString();
		switch (menuItem.getItemId()) {
			case 0:
				Toast.makeText(UserInfoActivity.this, "account : "+optAccount+"\nusername : "+optUsername, Toast.LENGTH_SHORT).show();
				Intent intent0=new Intent(UserInfoActivity.this, AddScheduleActivity.class);
				startActivity(intent0);
				break;
			case 1:
				Toast.makeText(UserInfoActivity.this, "添加日程", Toast.LENGTH_SHORT).show();
				Intent intent1=new Intent(UserInfoActivity.this, AddScheduleActivity.class);
				Bundle bundle1=new Bundle();
				bundle1.putString("account", optAccount);
				bundle1.putString("username", optUsername);
				intent1.putExtras(bundle1);
				startActivity(intent1);
				break;
			case 2:
				database.execSQL("delete from logininfo where account = ?", new String[] { optAccount });
				Toast.makeText(UserInfoActivity.this, "用户 "+optAccount+" 注销成功", Toast.LENGTH_SHORT).show();
				userArray.remove(index);
				adapter.notifyDataSetChanged();
				break;
		}
		
		return super.onContextItemSelected(menuItem);
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
			
		case R.id.user_info_add_user:
			Intent intent1=new Intent(UserInfoActivity.this, LoginActivity.class);
			startActivity(intent1);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
