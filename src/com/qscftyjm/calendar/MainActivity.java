package com.qscftyjm.calendar;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import sqliteutil.SQLiteHelper;

public class MainActivity extends Activity {

	private Button button1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		//Toast.makeText(this, POSTUtli.CheckUserInfo(), Toast.LENGTH_SHORT).show();
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
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		
		
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
			case R.id.main_addItem:
				Log.i("Clander","UserInfo");
				Intent intent=new Intent(MainActivity.this,UserInfoActivity.class);
				startActivity(intent);
				break;
			case R.id.main_refreshItem:
				Log.i("Clander","REFRESH");
				break;
			case R.id.main_searchItem:
				Log.i("Clander","SEARCH");
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
