package com.qscftyjm.calendar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class GetGlobalMsgService extends Service {
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
		Intent intent = new Intent();
        intent.setAction("com.qscftyjm.calendar.HAS_NEW_MSG");
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
