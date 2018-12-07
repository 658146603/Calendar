package com.qscftyjm.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class AddScheduleActivity extends Activity {

	String username;
	String account;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_schedule);
		
		Bundle bundle=this.getIntent().getExtras();
		if(bundle!=null&&bundle.containsKey("username")) {
			
			username=bundle.getString("username");
			account=bundle.getString("account");
		} else {
			username="local";
			account="000000";
		}
		Toast.makeText(this, 
					"nusername : "+username
					+ "\naccount : "+account, Toast.LENGTH_SHORT).show();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_schedule, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.set_calendar_ok) {
			Toast.makeText(AddScheduleActivity.this, "ÒÑ±£´æ", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
