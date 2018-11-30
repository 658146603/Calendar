package com.qscftyjm.calendar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import sqliteutil.SQLiteHelper;

public class UserInfoActivity extends Activity {

	private Button btn_gotoLogin;
	private LinearLayout LinearLocal;
	
	
	private LinearLayout LinearLogin;
	private TextView tv_userlist;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		btn_gotoLogin=(Button)findViewById(R.id.btn_to_login_page);
		LinearLocal=(LinearLayout)findViewById(R.id.userinfo_local);
		LinearLogin=(LinearLayout)findViewById(R.id.userinfo_login);
		tv_userlist=(TextView)findViewById(R.id.tv_user_list);
		String UserList="用户列表：\n";
		SQLiteHelper sqLiteHelper=new SQLiteHelper(UserInfoActivity.this, "calendar.db", null, 1);
		SQLiteDatabase database=sqLiteHelper.getWritableDatabase();
		
		Cursor cursor = database.query("logininfo", new String[] {"id","username","account"}, null, null, null, null, null, null);
		int count=0;
		if(cursor.moveToFirst()) {
			count=cursor.getCount();
			if(count>0) {
				LinearLocal.setVisibility(View.GONE);
				LinearLogin.setVisibility(View.VISIBLE);
				do {
					UserList+=cursor.getString(1)+"\n";
					
				} while(cursor.moveToNext());
				tv_userlist.setText(UserList);
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
