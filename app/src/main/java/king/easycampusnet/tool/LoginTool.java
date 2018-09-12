package king.easycampusnet.tool;
import java.io.*;
import java.net.*;
import java.util.*;
import android.widget.*;

public class LoginTool
{
	 private static final int GET=0x00;
	 private static final int POST=0x01;
	 
	 private final static String TAG="LoginTool";
     public static HttpURLConnection post(String url,String data){
		    
			return handle(POST,url,data);
	 }
		
	 private static HttpURLConnection handle(int flag,String url,String data){
		    //Logger.logInfo(TAG,"handle() doing...");
			//Logger.logInfo(TAG,"url:"+url);
			//Logger.logInfo(TAG,"data:"+data);
			//if(1==1)return null;
			if(url!=null){
				String method=null;
				if(flag==GET){
					method="GET";
				}else if(flag==POST){
					if(data==null)return null;
					method="POST";
				}
				
				HttpURLConnection connection=null;
				try {
					connection = (HttpURLConnection) new URL(url).openConnection();
					connection.setConnectTimeout(5000);
					connection.setReadTimeout(5000);
					connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon;)");
					//允许重定向,最多4次
					//connection.setInstanceFollowRedirects(true);
					if(method==null){
						method="GET";
						flag=GET;
					}
					connection.setRequestMethod(method);
					if(flag==POST){
						//至少要设置的两个请求头
						connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
						connection.setRequestProperty("Content-Length", data.length()+"");
						//post的方式提交实际上是流的方式提交给服务器
						connection.setDoOutput(true);
						OutputStream outputStream = connection.getOutputStream();
						outputStream.write(data.getBytes());
					}

					//获得状态码
					int responseCode = connection.getResponseCode();
					Logger.logInfo(TAG,"responseCode:"+responseCode);
					if(responseCode ==200||responseCode==301||responseCode==302){
						return connection;
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (ProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					if(connection!=null){
						connection.disconnect();
					}
				}
			}
			return null;
		}
		
	public static String mapToString(HashMap<String,Object> map) {
        StringBuffer sb = new StringBuffer();
        if (map.size() > 0) {
			int i=1;
			int size=map.size();
            for (String key : map.keySet()) {
				//Logger.logInfo(TAG,key+":"+map.get(key));
                sb.append(key + "=");
				String value = String.valueOf(map.get(key));
				if(value!=null){
					try {
						value = URLEncoder.encode(value, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						Logger.logError(TAG,e.toString());
					}
				}
				sb.append(value);
				if(i!=size)sb.append("&");
				i+=1;
            }
        }
		//Logger.logInfo(TAG,"mapToString:"+sb.toString());
        return sb.toString();
	}
}
