package tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	public static String getTime(){
		String time = null;
		Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time=sdf.format(date);
		System.out.println(time);
		
		return time; 
	}

	public static boolean checkIsOverTime(String lastcheckertime) {
		// TODO Auto-generated method stub
		boolean flag=true;
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date lastdate = sdf.parse(lastcheckertime);
			Date nowdate=new Date();
			long delta=nowdate.getTime()-lastdate.getTime();
			if(delta>=0&&delta<=1000*30) {
				flag=false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}
	
	
	
}
