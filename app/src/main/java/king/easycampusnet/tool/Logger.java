package king.easycampusnet.tool;
import android.util.*;
import android.widget.*;
import king.easycampusnet.*;
import android.os.*;
import android.content.*;

public class Logger
{
	public static void logInfo(String tag,String msg){
		//Log.i(tag,msg);
	}
	
	public static void logError(String tag,String msg){
		Log.e(tag,msg);
	}
	
	public static void send(Handler handler,String info){
		Handler hd=handler;
		Message msg=hd.obtainMessage();
		msg.obj=info;
		hd.sendMessage(msg);
	}
	
	public static void show(Context context,String msg){
		Toast.makeText(context,msg,1000).show();
	}
}
