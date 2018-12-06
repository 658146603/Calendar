package listviewadapter;

import java.util.ArrayList;
import java.util.Map;

import com.qscftyjm.calendar.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MsgListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<Map<String, Object>> msgData;
	private ViewHolder viewHolder;
	
	public MsgListAdapter(Context context, ArrayList<Map<String, Object>> msgData) {
		this.mContext=context;
		this.msgData=msgData;
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return msgData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		if (view == null) {
            
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_msg_layout, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
		
		viewHolder.tv_time.setText(msgData.get(position).get("time").toString());
		viewHolder.tv_msg.setText(msgData.get(position).get("content").toString());
		viewHolder.tv_account.setText(msgData.get(position).get("account").toString());
		viewHolder.tv_username.setText(msgData.get(position).get("username").toString());
		
		return view;
	}
	
	class ViewHolder {
		TextView tv_msg;
		TextView tv_time;
		TextView tv_account;
		TextView tv_username;
		
		public ViewHolder(View view) {
			tv_account=(TextView) view.findViewById(R.id.msg_account);
			tv_time=(TextView) view.findViewById(R.id.msg_time);
			tv_username=(TextView) view.findViewById(R.id.msg_username);
			tv_msg=(TextView) view.findViewById(R.id.msg_content);
		}
		
		
	}

}
