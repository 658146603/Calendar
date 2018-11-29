package postutil;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class AccountChecker {

	public static Map<String, Object> UserLogin(String account, String password){
		
		/*
		 {
			"Type": "user",
			"Method": "login",
			"Data": {
				"PassWord": "25D55AD283AA40AF464C76D713C7AD",
				"ID": -1,
				"Account": "1096",
				"UserName": "",
				"Priority": 0
			}
		 }
		*/
		
		
		Map<String, Object> LoginInfo=new HashMap<String, Object>();
		LoginInfo.put("Type", "user");
		LoginInfo.put("Method", "login");
		Map<String, Object> Data=new HashMap<String, Object>();
		Data.put("Account", account);
		Data.put("PassWord", password);
		Data.put("ID", -1);
		Data.put("UserName", "");
		Data.put("Priority", 0);
		LoginInfo.put("Data", Data);
		String result=POSTUtli.CheckUserInfo(new JSONObject(LoginInfo).toString()).toString();
		
		Map<String, Object> returnMap=new HashMap<String, Object>();
		returnMap.put("result", result);
		
		
		return returnMap;
	}
	
	public static Map<String, Object> ChangePassword(String account, String oldPassword, String newPassword){
		
		/*
		 {
			"Type": "user",
			"Method": "register",
			"Data": {
				"PassWord": "25D55AD283AA40AF464C76D713C7AD",
				"ID": -1,
				"Account": "NULL",
				"UserName": "test_1",
				"Priority": 0
			}
		 }
		*/
		
		
		Map<String, Object> LoginInfo=new HashMap<String, Object>();
		LoginInfo.put("Type", "user");
		LoginInfo.put("Method", "changepassword");
		Map<String, Object> Data=new HashMap<String, Object>();
		Data.put("Account", account);
		Data.put("PassWord", oldPassword);
		Data.put("ID", -1);
		Data.put("UserName", "");
		Data.put("Priority", 0);
		LoginInfo.put("Data", Data);
		String result=POSTUtli.CheckUserInfo(new JSONObject(LoginInfo).toString()).toString();
		
		Map<String, Object> returnMap=new HashMap<String, Object>();
		returnMap.put("result", result);
		
		
		return returnMap;
	}
	
public static Map<String, Object> UserRegister(String UserName, String newPassword){
		
		/*
		 {
			"Type": "user",
			"Method": "register",
			"Data": {
				"PassWord": "25D55AD283AA40AF464C76D713C7AD",
				"ID": -1,
				"Account": "NULL",
				"UserName": "test_1",
				"Priority": 0
			}
		 }
		*/
		
		
		Map<String, Object> LoginInfo=new HashMap<String, Object>();
		LoginInfo.put("Type", "user");
		LoginInfo.put("Method", "register");
		Map<String, Object> Data=new HashMap<String, Object>();
//		Data.put("Account", account);
//		Data.put("PassWord", oldPassword);
		Data.put("ID", -1);
		Data.put("UserName", "");
		Data.put("Priority", 0);
		LoginInfo.put("Data", Data);
		String result=POSTUtli.CheckUserInfo(new JSONObject(LoginInfo).toString()).toString();
		
		Map<String, Object> returnMap=new HashMap<String, Object>();
		returnMap.put("result", result);
		
		
		return returnMap;
	}
	
	
}
