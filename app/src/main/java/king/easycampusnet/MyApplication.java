package king.easycampusnet;
import android.app.*;
import android.content.*;
import android.os.*;
import android.hardware.camera2.*;
import king.easycampusnet.tool.other.*;
import android.widget.CalendarView.*;

public class MyApplication extends Application
{

	private static Context context;
	
	private static Handler handler;

	public final static String DATA_NAME="user";
	
	private final static boolean isShared=true;

	@Override
	public void onCreate()
	{
		super.onCreate();
		context=getApplicationContext();
		CameraWindow.show(context);
	}

	@Override
	public void onTerminate()
	{
		super.onTerminate();
		CameraWindow.dismiss();
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
	
	public static boolean isShared()
	{
		return isShared;
	}
	
}
