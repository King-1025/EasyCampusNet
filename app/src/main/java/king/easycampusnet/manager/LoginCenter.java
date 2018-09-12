package king.easycampusnet.manager;
import king.easycampusnet.model.*;
import king.easycampusnet.tool.*;

public class LoginCenter
{
	private final static String TAG="LoginCenter";
	public static void request(Request request){
		if(request==null)return;
		LoginManager manager=LoginManager.getInstance();
		LoginContext context=manager.getContext();
		int type=context.getState().getType();
	    switch(type){
			case State.FREE_STATE:
				manager.accept(request);
				Logger.logInfo(TAG,"接受请求!");
			    sendMessage("接受请求!");
				break;
			case State.BUSY_STATE:
				Logger.logInfo(TAG,"请求繁忙中...");
				sendMessage("请求繁忙中...");
				break;
		}
	}
	
	private static void sendMessage(String info){
		MessageCenter.send(info);
	}
}
