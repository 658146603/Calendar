package com.qscftyjm.calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;
import postutil.AsynTaskUtil.AsynNetUtils;
import postutil.AsynTaskUtil.AsynNetUtils.Callback;
import sqliteutil.SQLiteHelper;
import tools.ParamToJSON;
import tools.StringCollector;

public class GetGlobalMsgService extends Service {
	
	String json;
	Handler handler=new Handler();
	Runnable runnable;
	SQLiteDatabase database;
	SQLiteHelper dbHelper;
	
	public GetGlobalMsgService() {
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
//		json="[{\"Account\":\"1084\",\"Content\":\"helloworld\",\"Time\":\"2018-11-25 21:45:47\",\"ID\":\"70\"},{\"Account\":\"12\",\"Content\":\"12\",\"Time\":\"1121\",\"ID\":\"85\"},{\"Account\":\"12\",\"Content\":\"12\",\"Time\":\"1121\",\"ID\":\"86\"},{\"Account\":\"12\",\"Content\":\"12\",\"Time\":\"1121\",\"ID\":\"87\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"88\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"89\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"90\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"91\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"92\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"93\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"94\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"95\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"96\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"97\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"98\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"99\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"100\"}]";
//		Intent intent = new Intent();
//      intent.setAction("com.qscftyjm.calendar.HAS_NEW_MSG");
//      intent.putExtra("json", json);
//      sendBroadcast(intent);
		dbHelper=new SQLiteHelper(GetGlobalMsgService.this, "calendar.db", null, SQLiteHelper.DB_VERSION);
		database=dbHelper.getWritableDatabase();
		runnable=new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int lastId=0;
				handler.postDelayed(this, 5000);
				//Toast.makeText(GetGlobalMsgService.this, "SetTime", Toast.LENGTH_SHORT).show();
				Cursor cursor=database.query("message", new String[] { "msgid" }, null, null, null, null, "msgid desc", "0,1");
				if(cursor.moveToFirst()) {
					lastId=cursor.getInt(0);
				}
				AsynNetUtils.post(StringCollector.GetServer("message"), ParamToJSON.formGetGlobalMsgJson(lastId), new Callback() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						if(response!=null) {
							try {
								JSONObject jsonObj=new JSONObject(response);
								if(jsonObj.optInt("Status", -1)==0) {
									JSONArray msgArr=jsonObj.optJSONArray("Data");
									if(msgArr.length()>0) {
										json=msgArr.toString();
										Intent intent = new Intent();
										intent.setAction("com.qscftyjm.calendar.HAS_NEW_MSG");
										intent.putExtra("json", json);
										sendBroadcast(intent);
									}
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
				});
			}
		};
		
		
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		runnable.run();
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
}
