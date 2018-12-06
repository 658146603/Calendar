package com.qscftyjm.calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MsgReceiver extends BroadcastReceiver {
	
	private Message message;
	
	public MsgReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO: This method is called when the BroadcastReceiver is receiving
		// an Intent broadcast.
		//throw new UnsupportedOperationException("Not yet implemented");
		Toast.makeText(context, " ’µΩπ„≤•", Toast.LENGTH_SHORT).show();
		String json=intent.getStringExtra("json");
		message.getMsg(json);
		
	}
	
	interface Message {
	     public void getMsg(String str);
	 }
		 
     public void setMessage(Message message) {
         this.message = message;
     }
}
