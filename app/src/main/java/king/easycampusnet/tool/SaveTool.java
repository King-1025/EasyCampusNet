package king.easycampusnet.tool;
import android.content.*;
import king.easycampusnet.*;
import java.util.*;

public class SaveTool
{
	public static boolean add(Context context,String where,String key,String value){
		return add(context,where,new String[]{key},new String[]{value});
	}
	
	public static String query(Context context,String where,String key){
		String value=null;
		HashMap map=query(context,where,new String[]{key});
		if(map!=null){
			value=String.valueOf(map.get(key));
		}
		return value;
	}
	
	public static boolean remove(Context context,String where,String key){
		return remove(context,where,new String[]{key});
	}
	
	public static boolean add(Context context,String where,String []key,String[]value){
		boolean is=false;
		if(key!=null){
			if(value!=null){
				SharedPreferences sp=context.
				        getSharedPreferences(where,context.MODE_PRIVATE);
				if(sp!=null){
					SharedPreferences.Editor edit = sp.edit();
					for(int i=0;i<value.length;i++){
						edit.putString(key[i],String.valueOf(value[i]));
					}
					edit.commit();
					is=true;
				}
			}
		}
		return is;
	}
	
	public static HashMap<String,String> query(Context context,String where,String[]key){
		HashMap<String,String>result=null;
		if(key!=null){
			SharedPreferences sp=context.
				getSharedPreferences(where,context.MODE_PRIVATE);
			result=new HashMap<>();
			for(int i=0;i<key.length;i++){
				result.put(key[i],sp.getString(key[i],null));
			}
		}
		return result;
	}
	
	public static boolean remove(Context context,String where,String[] key){
		boolean is=false;
		if(key!=null){
			SharedPreferences sp=context.
				getSharedPreferences(where,context.MODE_PRIVATE);
			SharedPreferences.Editor edit = sp.edit();
			for(int i=0;i<key.length;i++){
				edit.remove(String.valueOf(key[i]));
			}
			edit.commit();
			is=true;
		}
		return is;
	}
	
	public static void clear(Context context,String where){
		SharedPreferences sp=context.
			getSharedPreferences(where,context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		edit.clear();
	}
}
