package listviewadapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.qscftyjm.calendar.R;

import android.content.Context;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListViewAdapter {

	public static void UserInfoAdapter(Context context,ListView listView, ArrayList<HashMap<String, Object>> dataArray) {
		
		SimpleAdapter adapter=new SimpleAdapter(context, dataArray, R.layout.list_item_layout, new String[]{"name","content","time","imgId"}, new int[]{R.id.user_name,R.id.content,R.id.time,R.id.icon});
		listView.setAdapter(adapter);
		
		
	}
}
