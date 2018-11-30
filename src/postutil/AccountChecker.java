//package postutil;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import postutil.AsynTaskUtil.AsynNetUtils.Callback;
//
//public class AccountChecker {
//
//	static String result=null;
//	static JSONObject jsonObj=null;
//	
//	public static JSONObject UserLogin(String account, String password){
//		
//		/*
//		 {
//			"Type": "user",
//			"Method": "login",
//			"Data": {
//				"PassWord": "25D55AD283AA40AF464C76D713C7AD",
//				"ID": -1,
//				"Account": "1096",
//				"UserName": "",
//				"Priority": 0
//			}
//		 }
//		*/
//		result=null;
//		jsonObj=null;
//		
//		Map<String, Object> LoginInfo=new HashMap<String, Object>();
//		LoginInfo.put("Type", "user");
//		LoginInfo.put("Method", "login");
//		Map<String, Object> Data=new HashMap<String, Object>();
//		Data.put("Account", account);
//		Data.put("PassWord", password);
//		Data.put("ID", -1);
//		Data.put("UserName", "");
//		Data.put("Priority", 0);
//		LoginInfo.put("Data", Data);
//		//String result=POSTUtli.CheckUserInfo(new JSONObject(LoginInfo).toString());
//		
//		AsynTaskUtil.AsynNetUtils.post("http://192.168.42.252:8080/CalendarServer/CalendarPost", "json="+new JSONObject(LoginInfo).toString(), new Callback() {
//			
//			@Override
//			public void onResponse(String response) {
//				// TODO Auto-generated method stub
//				result=response;
//				
//				try {
//					jsonObj=new JSONObject(result);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}
//		});
//		
//		
//		return jsonObj;
//		
//	}
//	
//	public static JSONObject ChangePassword(String account, String oldPassword, String newPassword){
//		
//		/*
//		 {
//			"Type": "user",
//			"Method": "changepassword",
//			"Data": {
//				"InnerData": {
//					"OldPassword": "25D55AD283AA40AF464C76D713C7AD",
//					"NewPassword": "96E79218965EB72C92A549DD5A33112"
//				},
//				"User": {
//					"PassWord": "25D55AD283AA40AF464C76D713C7AD",
//					"ID": -1,
//					"Account": "1006",
//					"UserName": "",
//					"Priority": 0
//				}
//			}
//		 }
//		*/
//		
//		
//		Map<String, Object> UserInfo=new HashMap<String, Object>();
//		UserInfo.put("Type", "user");
//		UserInfo.put("Method", "changepassword");
//		Map<String, Object> Data=new HashMap<String, Object>();
//		Map<String, Object> InnerData=new HashMap<String, Object>();
//		InnerData.put("NewPassword", newPassword);
//		InnerData.put("OldPassword", oldPassword);
//		Map<String, Object> User=new HashMap<String, Object>();
//		User.put("PassWord", oldPassword);
//		User.put("ID", -1);
//		User.put("Account", account);
//		User.put("UserName", "");
//		User.put("Priority", 0);
//		Data.put("InnerData", InnerData);
//		Data.put("User", User);
//		UserInfo.put("Data", Data);
//		String result=POSTUtli.CheckUserInfo(new JSONObject(UserInfo).toString()).toString();
//		
//		JSONObject jsonObj=null;
//		try {
//			jsonObj=new JSONObject(result);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return jsonObj;
//	}
//	
//	public static JSONObject UserRegister(String UserName, String Password){
//		
//		/*
//		 {
//			"Type": "user",
//			"Method": "register",
//			"Data": {
//				"PassWord": "25D55AD283AA40AF464C76D713C7AD",
//				"ID": -1,
//				"Account": "NULL",
//				"UserName": "test_1",
//				"Priority": 0
//			}
//		 }
//		*/
//		
//		Map<String, Object> RegInfo=new HashMap<String, Object>();
//		RegInfo.put("Type", "user");
//		RegInfo.put("Method", "register");
//		Map<String, Object> Data=new HashMap<String, Object>();
//		Data.put("ID", -1);
//		Data.put("UserName", UserName);
//		Data.put("Priority", 0);
//		Data.put("PassWord", Password);
//		Data.put("Account", null);
//		RegInfo.put("Data", Data);
//		String result=POSTUtli.CheckUserInfo(new JSONObject(RegInfo).toString());
//		JSONObject jsonObj = null;
//		try {
//			jsonObj=new JSONObject(result);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return jsonObj;
//	}
//	
//	
//}
