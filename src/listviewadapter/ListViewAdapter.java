package listviewadapter;

import java.util.ArrayList;
import java.util.Map;

import com.qscftyjm.calendar.R;

import android.content.Context;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListViewAdapter {

	public static ListView UserInfoAdapter(Context context,ListView listView, ArrayList<Map<String, Object>> dataArray) {
		
		SimpleAdapter adapter=new SimpleAdapter(context, dataArray, R.layout.list_item_userinfo_layout, new String[]{ "id", "account", "username", "priority", "lastchecktime" }, new int[]{ R.id.user_id, R.id.user_account, R.id.user_username, R.id.user_priority, R.id.user_lastchecktime });
		listView.setAdapter(adapter);
		
		return listView;
	}
}
