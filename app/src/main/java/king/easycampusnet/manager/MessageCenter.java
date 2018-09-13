package king.easycampusnet.manager;
import android.os.*;
import king.easycampusnet.tool.*;
import king.easycampusnet.*;

public class MessageCenter
{
	
	public static void handle(int flag,Bundle data){
	
	}
	
	public static void send(int flag,String info){
		send(info);
	}
	
	public static void send(String info){
		Logger.send(MyApplication.getHandler(),info);
	}
	
}
