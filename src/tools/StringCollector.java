package tools;

public class StringCollector {

	public final static String CalendarPostLocal  = "http://192.168.42.252:8080/CalendarServer/CalendarPost";
	public final static String CalendarPost  = "http://101.132.122.143:8080/CalendarServer/CalendarPost";
	
	public final static String MessagePostLocal  = "http://192.168.42.252:8080/CalendarServer/CalendarPost";
	public final static String MessagePost  = "http://101.132.122.143:8080/CalendarServer/MessagePost";
	
	public static String GetServer() {
		return CalendarPostLocal;
	}
	public static String GetServer(String tool) {
		// TODO Auto-generated method stub
		switch (tool) {
		case "message":
			return  MessagePostLocal;
			
		}
		return MessagePostLocal;
	}
}
