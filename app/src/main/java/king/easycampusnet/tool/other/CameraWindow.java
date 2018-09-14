package king.easycampusnet.tool.other;

import android.view.*;
import android.content.Context; 
import android.view.SurfaceView; 
import android.view.WindowManager; 
import android.view.WindowManager.LayoutParams;
import king.easycampusnet.tool.*; 

public class CameraWindow
{
	private static final String TAG = "CameraWindow";

	private static WindowManager windowManager; 

	private static Context applicationContext; 

	private static SurfaceView dummyCameraView; 

	/** 
	 * 显示全局窗口 
	 * 
	 * @param context 
	 */ 
	public static void show(Context context) { 
		if (applicationContext == null) { 
			applicationContext = context.getApplicationContext(); 
			windowManager = (WindowManager) applicationContext 
				.getSystemService(Context.WINDOW_SERVICE); 
			dummyCameraView = new SurfaceView(applicationContext); 
			LayoutParams params = new LayoutParams(); 
			params.width = 1; 
			params.height = 1; 
			params.alpha = 0; 
			params.type = LayoutParams.TYPE_SYSTEM_ALERT; 
			// 屏蔽点击事件 
			params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL 
				| LayoutParams.FLAG_NOT_FOCUSABLE 
				| LayoutParams.FLAG_NOT_TOUCHABLE; 
            dummyCameraView.setVisibility(View.INVISIBLE);
			windowManager.addView(dummyCameraView, params); 
			Logger.logInfo(TAG, TAG + "showing"); 
		} 
	} 

	/** 
	 * @return 获取窗口视图 
	 */ 
	public static SurfaceView getDummyCameraView() { 
		return dummyCameraView; 
	} 

	/** 
	 * 隐藏窗口 
	 */ 
	public static void dismiss() { 
		try { 
			if (windowManager != null && dummyCameraView != null) { 
				windowManager.removeView(dummyCameraView); 
				Logger.logInfo(TAG, TAG + " dismissed"); 
			} 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
	} 
}
