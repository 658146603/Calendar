package listviewadapter;

import java.util.ArrayList;
import java.util.Map;

import com.qscftyjm.calendar.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserInfoAdapter extends BaseAdapter {

	ViewHolder viewHolder = null;
	
	private Context mContext;
	private ArrayList<Map<String, Object>> data=new ArrayList<Map<String, Object>>();
	
	public UserInfoAdapter(Context context, ArrayList<Map<String, Object>> data) {
		// TODO Auto-generated constructor stub
		this.data=data;
		this.mContext=context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int i) {
		// TODO Auto-generated method stub
		return data.get(i);
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}

	@Override
	public View getView(final int i, View view, ViewGroup vGroup) {
		// TODO Auto-generated method stub
		
        if (view == null) {
            
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_userinfo_layout, null);
//            viewHolder.tv_id = (TextView) view.findViewById(R.id.user_id);
//            viewHolder.tv_account = (TextView) view.findViewById(R.id.user_account);
//            viewHolder.tv_username = (TextView) view.findViewById(R.id.user_username);
//            viewHolder.tv_priority = (TextView) view.findViewById(R.id.user_priority);
//            viewHolder.tv_lastchecktime = (TextView) view.findViewById(R.id.user_lastchecktime);
//            viewHolder.linear_extend = (LinearLayout) view.findViewById(R.id.user_linear_show_detail);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_id.setText("id : "+data.get(i).get("id").toString());
        viewHolder.tv_account.setText("account : "+data.get(i).get("account").toString());
        viewHolder.tv_username.setText("username : "+data.get(i).get("username").toString());
        viewHolder.tv_priority.setText("priority : "+data.get(i).get("priority").toString());
        boolean isOvertime=(boolean) data.get(i).get("overtime");
        viewHolder.bt_show_detail.setImageResource(R.drawable.turn_down);
        viewHolder.linear_extend.setVisibility(View.GONE);
        viewHolder.tv_lastchecktime.setText("lastchecktime : "+data.get(i).get("lastchecktime").toString()+(isOvertime?" 已过期":" 正常"));
        if(isOvertime) {
        	viewHolder.tv_lastchecktime.setTextColor(0xffff0000);
        } else {
        	viewHolder.tv_lastchecktime.setTextColor(0x7f066682);
        }
        viewHolder.setItemOnListener();
        
        return view;
	}
 
    class ViewHolder {
    	View view;
        TextView tv_id;
        TextView tv_account;
        TextView tv_username;
        TextView tv_priority;
        TextView tv_lastchecktime;
        ImageButton bt_show_detail;
        LinearLayout linear_extend;
        public ViewHolder(View view) {
    		this.view=view;
    		this.tv_id = (TextView) view.findViewById(R.id.user_id);
    		this.tv_account = (TextView) view.findViewById(R.id.user_account);
    		this.tv_username = (TextView) view.findViewById(R.id.user_username);
    		this.tv_priority = (TextView) view.findViewById(R.id.user_priority);
    		this.tv_lastchecktime = (TextView) view.findViewById(R.id.user_lastchecktime);
    		this.linear_extend = (LinearLayout) view.findViewById(R.id.user_linear_show_detail);
    		this.bt_show_detail = (ImageButton) view.findViewById(R.id.user_btn_show_detail);
    	}
        
        private void setItemOnListener() {
			bt_show_detail.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if(linear_extend.getVisibility()==View.VISIBLE) {
						linear_extend.setVisibility(View.GONE);
						viewHolder.bt_show_detail.setImageResource(R.drawable.turn_down);
					} else if(linear_extend.getVisibility()==View.GONE) {
						linear_extend.setVisibility(View.VISIBLE);
						//viewHolder.bt_show_detail.setBackgroundResource(0);
						viewHolder.bt_show_detail.setImageResource(R.drawable.turn_up);
					} else {
						linear_extend.setVisibility(View.GONE);
						viewHolder.bt_show_detail.setImageResource(R.drawable.turn_down);
					}
				}
			});			
		}
        
    }
    
   

}
