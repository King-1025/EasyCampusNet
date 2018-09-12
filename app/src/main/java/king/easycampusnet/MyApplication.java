package king.easycampusnet;
import android.app.*;
import android.content.*;
import android.os.*;

public class MyApplication extends Application
{

	private static Context context;
	
	private static Handler handler;

	@Override
	public void onCreate()
	{
		super.onCreate();
		context=getApplicationContext();
	}
	
	public static Context getContext(){
		return context;
	}
	
	public static void setHandler(Handler handler)
	{
		MyApplication.handler = handler;
	}

	public static Handler getHandler()
	{
		return handler;
	}
}
