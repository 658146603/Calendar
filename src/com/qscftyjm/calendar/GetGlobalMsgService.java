package com.qscftyjm.calendar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class GetGlobalMsgService extends Service {
	
	String json;
	
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
		json="[{\"Account\":\"1084\",\"Content\":\"helloworld\",\"Time\":\"2018-11-25 21:45:47\",\"ID\":\"70\"},{\"Account\":\"12\",\"Content\":\"12\",\"Time\":\"1121\",\"ID\":\"85\"},{\"Account\":\"12\",\"Content\":\"12\",\"Time\":\"1121\",\"ID\":\"86\"},{\"Account\":\"12\",\"Content\":\"12\",\"Time\":\"1121\",\"ID\":\"87\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"88\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"89\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"90\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"91\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"92\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"93\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"94\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"95\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"96\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"97\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"98\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"99\"},{\"Account\":\"12\",\"Content\":\"王程飞\",\"Time\":\"1121\",\"ID\":\"100\"}]";
		Intent intent = new Intent();
        intent.setAction("com.qscftyjm.calendar.HAS_NEW_MSG");
        intent.putExtra("json", json);
        sendBroadcast(intent);
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
}
