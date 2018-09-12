package king.easycampusnet.model;
import java.util.*;
import king.easycampusnet.tool.*;
import king.easycampusnet.manager.*;

public class EPortalRequest implements Request
{

	private String url;
	private HashMap<String,Object> payload;
	
	private EPortalUser user;
    private final static String BASED_URL="http://10.168.6.10:801/eportal/";
	private final static String REDIRECT_URL="https://www.baidu.com/";
	
	private final static String TAG="EPortalRequest";
	public EPortalRequest(){
       this(null);
	}
	
	public EPortalRequest(EPortalUser user)
	{
		this.user = user;
		this.redirectURL=REDIRECT_URL;
		create();
	}

	public void setUser(EPortalUser user)
	{
		this.user = user;
		parseUserData();
	}

	public EPortalUser getUser()
	{
		return user;
	}
	
	@Override
	public String getUrl()
	{
		return url;
	}

	@Override
	public HashMap<String, Object> getPayload()
	{
		return payload;
	}
	
	private void create(){
	   //url构造
	   String ip=getIP();
	   Logger.logInfo(TAG,"ip:"+ip);
	   sendMessage("ip:"+ip);
	   url=BASED_URL+"?c=ACSetting";
	   appendURL("a",          "Login");
	   appendURL("protocol",   "http");
	   appendURL("hostname",   "10.168.6.10");
	   appendURL("port",       "801");
	   appendURL("iTermType",  "2");
	   appendURL("wlanuserip", ip);
	   appendURL("wlanacip",   "10.168.6.9");
	   appendURL("wlanacname", "");
	   appendURL("mac",        "00-00-00-00-00-00");
	   appendURL("ip",         ip);
	   appendURL("redirect",   redirectURL);
	   appendURL("session",    "");
	   appendURL("enAdvert",   "0");                                    
	   appendURL("queryACIP",  "0");                                         
	   appendURL("loginMethod","1");
	   //payload构造
	   payload=new HashMap<>();
	   payload.put("R1","0");
	   payload.put("R2","0");
	   payload.put("R3","0");
	   payload.put("R6","1");
	   payload.put("para","00");
	   payload.put("OMKKey","123456");
	   parseUserData();
	}
	
	private void parseUserData(){
		if(user==null)return;
		String token=",1,"+user.getAccount()+user.getAccountType();
		String passwd=user.getPassword();
		Logger.logInfo(TAG,"user:"+user.getAccount());
		Logger.logInfo(TAG,"passwd:"+passwd);
		Logger.logInfo(TAG,"type:"+user.getAccountType());
		sendMessage("user:"+user.getAccount()+"\npasswd:"+passwd+"\ntype:"+user.getAccountType());
		payload.put("DDDDD",token);
		payload.put("upass",passwd);
	}
	
	private void appendURL(String key,String value){
		url+="&"+key+"="+value;
	}
	
	private String getIP(){
		return HostTool.getHostIP();
	}
	
	private void sendMessage(String msg){
		MessageCenter.send(msg);
	}
}
