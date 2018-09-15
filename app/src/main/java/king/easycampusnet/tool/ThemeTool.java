package king.easycampusnet.tool;
import king.easycampusnet.*;
import android.content.*;
import android.app.*;
import javax.mail.*;
import king.easycampusnet.manager.*;

public class ThemeTool
{
	public static final String KEY_THEME="theme";

	public static final String FLAG_LIGHT_THEME="light";
	public static final String FLAG_DARK_THEME="dark";
	
	public static final int THEME_NONE=-1;
	
	public static int loadTheme(){
		String theme=SaveTool.query(MyApplication.getContext(),MyApplication.DATA_NAME,KEY_THEME);
		return parseTheme(theme);
	}
	
	public static boolean saveTheme(int theme){
		String value=null;
		switch(theme){
			case R.style.light:
			     value=FLAG_LIGHT_THEME;
				 break;
			case R.style.dark:
				 value=FLAG_DARK_THEME;
				 break;
		}
		MessageCenter.send("theme value:"+value);
		if(value!=null){
			return SaveTool.add(MyApplication.getContext(),MyApplication.DATA_NAME,KEY_THEME,value);
		}else{
			return false;
		}
		
	}
	
	private static int parseTheme(String theme){
	        int value=THEME_NONE;
			if(!isNull(theme)){
				if(theme.equals(FLAG_LIGHT_THEME)){
					value=R.style.light;
				}else if(theme.equals(FLAG_DARK_THEME)){
					value=R.style.dark;
				}
			}
			return value;
	}
	
	private static boolean isNull(String p1){
		return ActivityTool.isNull(p1);
	}
}
