package king.easycampusnet.ui;
import android.app.*;
import android.os.*;
import king.easycampusnet.tool.*;
import king.easycampusnet.*;

public class BasedActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		initTheme();
		super.onCreate(savedInstanceState);
		//ActivityTool.setWindowStatusBarColor(this,R.color.colorPrimaryDark);
	}
	
	private void initTheme(){
		int value=MyApplication.getTheme();
		if(value!=ThemeTool.THEME_NONE){
			setTheme(value);
		}
	}
	
}
