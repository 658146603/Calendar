package postutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;

import android.util.Log;

public class POSTUtli {
	
	protected static final String CalendarServer = "http://192.168.42.252:8080/CalendarServer/CalendarPost";
	static String data="";

	public static String CheckUserInfo(final String request) {
		data="";
		
		Thread thread = new Thread(new Runnable() {  
            @Override  
            public void run() {  
                BufferedReader bufferedReader = null;
                try {  
                    URL url = new URL(CalendarServer);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
                    conn.setConnectTimeout(10000);  
                    conn.setDoOutput(true);// 允许输出  
                    conn.setRequestMethod("POST");  
                    conn.setRequestProperty("Connection", "Keep-Alive");  
                    conn.setRequestProperty("Charset", "UTF-8");  
                    OutputStream os = conn.getOutputStream();  
                    //os.write("json={\"Type\":\"user\",\"Method\":\"login\",\"Data\":{\"PassWord\":\"25D55AD283AA40AF464C76D713C7AD\",\"ID\":-1,\"Account\":\"1096\",\"UserName\":\"\",\"Priority\":0}}".getBytes());
                    os.write(("json="+request).getBytes());
                    //Toast.makeText(context, "Connecting", Toast.LENGTH_SHORT).show();
                    if (conn.getResponseCode() == 200) {  
                        //System.out.println(conn.toString());  
                        //Toast.makeText(context, "Connecting Success", Toast.LENGTH_SHORT).show();
                        InputStream is = conn.getInputStream();  
                        InputStreamReader isr = new InputStreamReader(is, "UTF-8");  
                        bufferedReader = new BufferedReader(isr);  
                    }  
                    String result = "";  
                    String line = "";  
                    if (bufferedReader != null) {  
                        try {  
                            while ((line = bufferedReader.readLine()) != null) {  
                                result += line;  
                            }  
                        } catch (IOException e) {  
                            e.printStackTrace();  
                        }  
                    }
                    data=result;
                    Log.i("Calendar","From Sever: "+result);
                } catch (MalformedURLException e) {  
                    // URL格式错误  
                    e.printStackTrace();  
                } catch (UnsupportedEncodingException e) {  
                    // 不支持你设置的编码  
                    e.printStackTrace();  
                } catch (ProtocolException e) {  
                    // 请求方式不支持  
                    e.printStackTrace();  
                } catch (IOException e) {  
                    // 输入输出通讯出错  
                    e.printStackTrace();  
                }
                
            }  
        });  
        thread.start();
        
        while(data==""){
        	try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
        }
        
        return data;
		
	}
	
}
