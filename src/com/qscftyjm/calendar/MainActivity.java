package com.qscftyjm.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import listviewadapter.MsgListAdapter;
import postutil.AsynTaskUtil.AsynNetUtils;
import postutil.AsynTaskUtil.AsynNetUtils.Callback;
import sqliteutil.SQLiteHelper;
import tools.AlertDialogUtil;
import tools.ParamToJSON;
import tools.StringCollector;
import tools.TimeUtil;

public class MainActivity extends Activity implements MsgReceiver.Message{

	private Button button1, button2, button3, bt_send_msg, bt_choose_account;
	private Button bt_tab[]=new Button[4];
	private LinearLayout linear[]=new LinearLayout[4];
	private EditText et_send_msg;
	private ListView list_msg;
	ArrayList<Map<String, Object>> msgData=new ArrayList<Map<String, Object>>();;
	MsgListAdapter msgListAdapter;
	private static ArrayList<Map<String, Object>> availableAccount = new ArrayList<Map<String, Object>>();
	private static String chooseAccount = null;
	MsgReceiver msgReceiver;
	SQLiteDatabase database;
	SQLiteHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		button1=(Button)findViewById(R.id.button1);
		button2=(Button)findViewById(R.id.button2);
		button3=(Button)findViewById(R.id.button3);
		bt_send_msg=(Button)findViewById(R.id.btn_send_msg);
		bt_choose_account=(Button)findViewById(R.id.bt_choose_account);
		bt_tab[0]=(Button)findViewById(R.id.tab_btn_home);
		bt_tab[1]=(Button)findViewById(R.id.tab_btn_team);
		bt_tab[2]=(Button)findViewById(R.id.tab_btn_message);
		bt_tab[3]=(Button)findViewById(R.id.tab_btn_info);
		et_send_msg=(EditText)findViewById(R.id.input_msg);
		linear[0]=(LinearLayout)findViewById(R.id.main_linear_home);
		linear[1]=(LinearLayout)findViewById(R.id.main_linear_team);
		linear[2]=(LinearLayout)findViewById(R.id.main_linear_message);
		linear[3]=(LinearLayout)findViewById(R.id.main_linear_info);
		list_msg=(ListView)findViewById(R.id.list_msg);
		
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
		
//		msgReceiver=new MsgReceiver();
//		IntentFilter intentFilter = new IntentFilter();
//		intentFilter.addAction("com.qscftyjm.calendar.HAS_NEW_MSG");
//		registerReceiver(msgReceiver, intentFilter);
//		msgReceiver.setMessage(this);
//		
//		Intent startGetglobalMsg=new Intent(MainActivity.this, GetGlobalMsgService.class);
//		startService(startGetglobalMsg);
		
		dbHelper=new SQLiteHelper(MainActivity.this, "calendar.db", null, SQLiteHelper.DB_VERSION);
		database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query("logininfo", new String[] {"account","password","lastchecktime"}, null, null, null, null, null, null);
		int count=0;
		availableAccount.clear();
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
					Map<String, Object> tempAccount=new HashMap<String, Object>();
					tempAccount.put("account", account);
					
					tempAccount.put("password", password);
					availableAccount.add(tempAccount);
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
										//Toast.makeText(MainActivity.this, "用户 "+account+" 的账号更新成功", Toast.LENGTH_SHORT).show();
										chooseAccount=account;
										et_send_msg.setHint("以账号 "+chooseAccount+" 发送消息");
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
				
				
				
				Map<String, Object> msg=new HashMap<String, Object>();
				msg.put("time", "没有更多消息了哦");
				msg.put("content", "");
				msg.put("account", "");
				msg.put("username", "");
				msgData.add(msg);
				int lastMsgid=0;
				Cursor msgCursor = database.query("message", new String[] { "msgid", "fromaccount", "sendtime", "content" }, null, null, null, null, null, null);
				if(msgCursor.moveToFirst()) {
					do {
						Map<String, Object> newMsg=new HashMap<String, Object>();
						newMsg.put("time", msgCursor.getString(2));
						newMsg.put("content", msgCursor.getString(3));
						newMsg.put("account", msgCursor.getString(1));
						newMsg.put("username", "null");
						msgData.add(newMsg);
						lastMsgid=msgCursor.getInt(0);
					}while(msgCursor.moveToNext());
					
				}
				msgCursor.close();
				
				msgListAdapter=new MsgListAdapter(MainActivity.this, msgData);
				//msgData.getClass();
				list_msg.setAdapter(msgListAdapter);
				list_msg.setSelection(msgListAdapter.getCount()-1);
				
				
				
				
				AsynNetUtils.post(StringCollector.GetServer("message"), ParamToJSON.formGetGlobalMsgJson(lastMsgid), new Callback() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						if(response!=null) {
							try {
								JSONObject jsonObj=new JSONObject(response);
								int status=jsonObj.optInt("Status", -1);
								if(status==0) {
									JSONArray data=jsonObj.optJSONArray("Data");
									if(data!=null) {
										int newCount=data.length();
										if(newCount>0) {
											for(int i=0;i<newCount;i++) {
												Map<String, Object> newMsg=new HashMap<String, Object>();
												JSONObject newObj=data.getJSONObject(i);
												newMsg.put("time", newObj.get("Time").toString());
												newMsg.put("content", newObj.get("Content").toString());
												newMsg.put("account", newObj.get("Account").toString());
												newMsg.put("username", "null");
												msgData.add(newMsg);
												
												ContentValues values=new ContentValues();
												values.put("msgid", Integer.valueOf(newObj.get("ID").toString()));
												values.put("fromaccount", newObj.get("Account").toString());
												values.put("sendtime", newObj.get("Time").toString());
												values.put("content", newObj.get("Content").toString());
												database.insert("message", null, values);
											}
											msgListAdapter.notifyDataSetChanged();
											list_msg.setSelection(msgListAdapter.getCount()-1);
										}else {
											Toast.makeText(MainActivity.this, "没有新消息", Toast.LENGTH_SHORT).show();
										}
										
									}
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							Toast.makeText(MainActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
						}
					}
					
				});
				
			}
		}
		
		
		msgReceiver=new MsgReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.qscftyjm.calendar.HAS_NEW_MSG");
		registerReceiver(msgReceiver, intentFilter);
		msgReceiver.setMessage(this);
		
		Intent startGetglobalMsg=new Intent(MainActivity.this, GetGlobalMsgService.class);
		startService(startGetglobalMsg);
		
		
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
		
		bt_send_msg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String Content=et_send_msg.getText().toString();
				if(Content.equals("")) {
					Toast.makeText(MainActivity.this, "消息内容不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
				ArrayList<String> user=new ArrayList<String>();
				String Password=null;
				for (Map<String, Object> item : availableAccount) {
					user.add((String)item.get("account"));
					if(((String)item.get("account")).equals(chooseAccount)) {
						Password=(String)item.get("password");
					}
				}
				if(chooseAccount!=null) {
					if(Password!=null) {
//						Map<String, Object> item=new HashMap<String, Object>();
//						item.put("time", TimeUtil.getTime());
//						item.put("content", Content);
//						item.put("account", chooseAccount);
//						item.put("username", "null");
//						msgData.add(item);
//						msgListAdapter.notifyDataSetChanged();
//						list_msg.setSelection(list_msg.getCount()-1);
						AsynNetUtils.post(StringCollector.GetServer("message"), ParamToJSON.formSendGlobalMsgJson(chooseAccount, Password, Content), new Callback() {

							@Override
							public void onResponse(String response) {
								// TODO Auto-generated method stub
								if(response!=null) {
									try {
										JSONObject jsonObj=new JSONObject(response);
										int status=jsonObj.optInt("Status", -1);
										if(status==0) {
											Toast.makeText(MainActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
										} else {
											Toast.makeText(MainActivity.this, "账号 "+chooseAccount+" 已经过期，请切换账号或重新登录", Toast.LENGTH_SHORT).show();
											et_send_msg.setHint("账号 "+chooseAccount+" 已过期");
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
								} else {
									Toast.makeText(MainActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
								}
							}
							
						});
					}
					
				} else {
					ListView userList=new ListView(MainActivity.this, null);
					
					ListAdapter adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, user);
					userList.setAdapter(adapter);
					final ArrayList<String> templist=user;
					final AlertDialog builder=new AlertDialog.Builder(MainActivity.this).setTitle("选择你的账号")
									                .setView(userList)
									                .show();
					userList.setOnItemClickListener(new OnItemClickListener() {
						
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							// TODO Auto-generated method stub
							chooseAccount=templist.get(position);
							et_send_msg.setHint("以账号 "+chooseAccount+" 发送消息");
							builder.dismiss();
						}
					});
				}
				
				et_send_msg.setText("");
			}
		});
		
		bt_choose_account.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ListView userList=new ListView(MainActivity.this, null);
				ArrayList<String> user=new ArrayList<String>();
				
				for (Map<String, Object> item : availableAccount) {
					user.add((String)item.get("account"));
				}
				final ArrayList<String> templist=user;
				ListAdapter adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, user);
				userList.setAdapter(adapter);
				final AlertDialog builder=new AlertDialog.Builder(MainActivity.this).setTitle("选择你的账号")
								                .setView(userList)
								                .show();
				
				userList.setOnItemClickListener(new OnItemClickListener() {
					
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
						chooseAccount=templist.get(position);
						et_send_msg.setHint("以账号 "+chooseAccount+" 发送消息");
						builder.dismiss();
					}
				});
			}
		});
		
		
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(msgReceiver);
		
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
				Intent intent2=new Intent(MainActivity.this, AddScheduleActivity.class);
				startActivity(intent2);
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

	@Override
	public void getMsg(String msg) {
		// TODO Auto-generated method stub
		if(msg!=null) {
			try {
				JSONArray jsonArr=new JSONArray(msg);
				int msgCt=jsonArr.length();
				for(int i=0;i<msgCt;i++) {
					Map<String, Object> newMsg=new HashMap<String, Object>();
					JSONObject newObj=jsonArr.getJSONObject(i);
					newMsg.put("time", newObj.get("Time").toString());
					newMsg.put("content", newObj.get("Content").toString());
					newMsg.put("account", newObj.get("Account").toString());
					newMsg.put("username", "null");
					msgData.add(newMsg);
					
//					ContentValues values=new ContentValues();
//					values.put("msgid", Integer.valueOf(newObj.get("ID").toString()));
//					values.put("fromaccount", newObj.get("Account").toString());
//					values.put("sendtime", newObj.get("Time").toString());
//					values.put("content", newObj.get("Content").toString());
//					database.insert("message", null, values);
				}
				if(msgListAdapter!=null&&list_msg!=null) {
					msgListAdapter.notifyDataSetChanged();
					list_msg.setSelection(list_msg.getCount()-1);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
