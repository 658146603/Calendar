package sqliteutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
	public final static int DB_VERSION = 4;
	final String CREATE_TABLE_LOGININFO="create table " 
			+ "logininfo" + "("
            + "id" + " Integer primary key autoincrement,"
            + "account" + " text,"
            + "password" + " text,"
            + "username" + " text,"
            + "priority" + " Integer,"
            + "lastchecktime" + " text,"
            + "datajson" + " text,"
            + "datablob" + " blob"
            + ")";
	
	final String CREATE_TABLE_MESSAGE="create table " 
			+ "message" + "("
            + "id" + " Integer primary key autoincrement,"
			+ "msgid" + " Integer,"
            + "fromaccount" + " text,"
            + "fromuseranme" + " text,"
            + "sendtime" + " text,"
            + "content" + " text,"
            + "extra" + " blob"
            + ")";
	
	final String CREATE_TABLE_CALENDAR="create table " 
			+ "calendar" + "("
            + "id" + " Integer primary key autoincrement,"
            + "activityname" + " text,"
            + "starttime" + " text,"
            + "endtime" + " text,"
            + "content" + " text,"
            + "isteam" + " text,"
            + "location" + " text,"
            + "data" + " blob"
            + ")";
	

	public SQLiteHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		db.execSQL(CREATE_TABLE_LOGININFO);
		db.execSQL(CREATE_TABLE_MESSAGE);
		db.execSQL(CREATE_TABLE_CALENDAR);
		Log.d("Calendar", "Creare Successful");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {
            case 2:
                upgradeToVersion2(db);
                break;
            case 3:
                upgradeToVersion3(db);
                break;
                 
            default:
                break;
            }
        }
	}

	private void upgradeToVersion2(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql1 = "ALTER TABLE message ADD COLUMN msgid INTEGER";
        db.execSQL(sql1);
	}

	private void upgradeToVersion3(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}
	
	

	
	
}
