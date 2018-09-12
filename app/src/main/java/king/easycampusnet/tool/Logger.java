package king.easycampusnet.tool;
import android.util.*;
import android.widget.*;
import king.easycampusnet.*;
import android.os.*;

public class Logger
{
	public static void logInfo(String tag,String msg){
		//Log.i(tag,msg);
	}
	
	public static void logError(String tag,String msg){
		Log.e(tag,msg);
	}
	
	public static void send(String info){
		Handler hd=MyApplication.getHandler();
		Message msg=hd.obtainMessage();
		msg.obj=info;
		hd.sendMessage(msg);
	}
	
	public static void show(String msg){
		Toast.makeText(MyApplication.getContext(),msg,1000).show();
	}
}
