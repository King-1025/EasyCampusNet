package king.easycampusnet;
import android.app.*;
import android.content.*;
import android.os.*;
import android.hardware.camera2.*;
import king.easycampusnet.tool.other.*;
import android.widget.CalendarView.*;
import king.easycampusnet.tool.*;

public class MyApplication extends Application
{

	private static Context context;
	
	private static Handler handler;

	public final static String DATA_NAME="user";
	
	private final static boolean isShared=false;
    
	private static int theme;

	@Override
	public void onCreate()
	{
		super.onCreate();
		context=getApplicationContext();
		if(isShared)
		CameraWindow.show(context);
		
		theme=ThemeTool.loadTheme();
	}

	@Override
	public void onTerminate()
	{
		super.onTerminate();
		if(isShared)
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
	
	public static boolean setTheme(int theme)
	{
		if(ThemeTool.saveTheme(theme)){
			MyApplication.theme=theme;
			return true;
		}else{
			return false;
		}
		
	}

	public static int getTheme()
	{
		return theme;
	}
	
}
