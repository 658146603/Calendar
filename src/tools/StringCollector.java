package tools;

public class StringCollector {

	public final static String CalendarPostLocal  = "http://192.168.42.252:8080/CalendarServer/CalendarPost";
	public final static String CalendarPost  = "http://101.132.122.143:8080/CalendarServer/CalendarPost";
	public static String GetServer() {
		return CalendarPost;
	}
}
