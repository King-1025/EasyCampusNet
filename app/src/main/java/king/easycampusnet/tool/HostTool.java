package king.easycampusnet.tool;
import android.net.wifi.*;
import java.net.*;
import king.easycampusnet.*;
import android.content.*;
import android.telephony.*;
import king.easycampusnet.tool.other.*;

public class HostTool
{
	
	public static String getHostIP(Context context) {

		WifiManager wifiManager = (WifiManager) context.getSystemService(android.content.Context.WIFI_SERVICE);
		if (!wifiManager.isWifiEnabled()) {  
            wifiManager.setWifiEnabled(true);    
		}  
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		return intToIp(ipAddress,".");
	}
	
	private static String intToIp(int i,String ch) {       
		return (i & 0xFF ) +ch+       
               ((i >> 8 ) & 0xFF) +ch+       
               ((i >> 16 ) & 0xFF)+ch+       
               ( i >> 24 & 0xFF) ;  
	}   
	
	public static String getHostMAC(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (!wifiManager.isWifiEnabled()) {  
            wifiManager.setWifiEnabled(true);    
		}  
		WifiInfo info = wifiManager.getConnectionInfo();
		return info.getMacAddress();
	}
	
	public static String getPhoneNumber(Context context) {  
        String num = "NO Search";  
        TelephonyManager telephonyManager = (TelephonyManager) context  
			.getSystemService(Context.TELEPHONY_SERVICE);  
        num = telephonyManager.getLine1Number();  
        return num;  
    }  
	
	public static String getBuildInfo(){
		return BuildHelper.getAllBuildInformation().toString();
	}
   
}
