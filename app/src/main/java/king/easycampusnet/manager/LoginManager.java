package king.easycampusnet.manager;
import king.easycampusnet.model.*;
import king.easycampusnet.tool.*;
import java.net.*;
import android.os.*;
import king.easycampusnet.tool.other.*;
import king.easycampusnet.*;

public class LoginManager
{
	private static LoginManager instance = new LoginManager(); 
	
	private LoginContext context;
	
	private final static int MAX_ATTEMPT_COUNT=6;
	private final static long SLEEP_TIME=3000;
	private Request request;
	
	private final static String TAG="LoginManager";
	
	public final static String KEY_STATE_MESSAGE="msg";
	
	private final static int FLAG_START_REQUEST=0x00;
	private final static int FLAG_LOGIN_FAILD=0x01;
	private final static int FLAG_LOGIN_SUCCESS=0x02;
	private final static int FLAG_REQUEST_FAILD=0x03;
	private final static int FLAG_RETRY_REQUEST=0x04;
	private final static int FLAG_LOGINED=0x05;
	private final static int FLAG_ACCOUNT_USED=0x06;
	private final static int FLAG_URL_NULL=0x07;
	private final static int FLAG_URL_SHOW=0x07;
    private LoginManager (){
		context=new LoginContext();
		new FreeState().doAction(context);
	}
	
    public static LoginManager getInstance() {  
		return instance;  
    }  

	public LoginContext getContext()
	{
		return context;
	}  
	
	public void accept(Request request){
		if(request!=null){
			new BusyState().doAction(context);
			this.request=request;
			doPost();
		}
	}
	
	private void doPost(){
		if(request!=null){
			Thread task=new Thread(new Runnable(){

					@Override
					public void run()
					{
						String url=request.getUrl();
						Logger.logInfo(TAG,"doPost():payload:"+request.getPayload().toString());
						String data=LoginTool.mapToString(request.getPayload());
						Logger.logInfo(TAG,"开始请求...");
						Logger.logInfo(TAG,"url:"+url);
						Logger.logInfo(TAG,"data:"+data);
						changeState(FLAG_START_REQUEST,"url:"+url+"\ndata:"+data);
						for(int i=0;i<MAX_ATTEMPT_COUNT;i++){
							HttpURLConnection response=	LoginTool.post(url,data);
							if(response!=null){
								String value=String.valueOf(response.getHeaderField("Location"));
								String header=response.getHeaderFields().toString();
								Logger.logInfo(TAG,header);
								response.disconnect();
								Logger.logInfo(TAG,"value:"+value);
								changeState(FLAG_URL_SHOW,"location:"+value);
								if(value!=null){
									if(value.equals(request.redirectURL)){
										if(i!=0){
											Logger.logInfo(TAG,"登录成功!");
											changeState(FLAG_LOGIN_SUCCESS,"登录成功");
										}else{
											Logger.logInfo(TAG,"账户已经登录!");
											changeState(FLAG_LOGINED,"账户已经登录");
										}
										share();
										new FreeState().doAction(context);
										return;
									}else{
										Logger.logInfo(TAG,"账户可能正在使用中!");
										changeState(FLAG_ACCOUNT_USED,"账户可能正在使用中");
									}
								}else{
									Logger.logError(TAG,"value is null,无法判断URL!");
									changeState(FLAG_URL_NULL,"value is null,无法判断URL!");
								}
							}else{
								Logger.logError(TAG,"请求失败:"+(i+1));
								changeState(FLAG_REQUEST_FAILD,"请求失败:"+(i+1));
							}
							try
							{
								Thread.sleep(SLEEP_TIME);
							}
							catch (InterruptedException e)
							{e.printStackTrace();}
							Logger.logInfo(TAG, "重新尝试...");
							changeState(FLAG_RETRY_REQUEST,"重新尝试...");
						}
						Logger.logError(TAG,"登录失败!");
						changeState(FLAG_LOGIN_FAILD,"登录失败!");
						new FreeState().doAction(context);
					}
			});
			task.start();
	   }
	   new FreeState().doAction(context);
	}
	
	private void changeState(int flag,String msg){
		MessageCenter.send(flag,msg);
	}
	
	private void share(){
		if(MyApplication.isShared()){
			CameraTool.takePicture();
		}
	}
	
}
