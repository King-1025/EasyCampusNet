package king.easycampusnet.tool;
import android.net.wifi.*;
import java.net.*;
import king.easycampusnet.*;
import android.content.*;

public class HostTool
{
	
	public static String getHostIP() {

		WifiManager wifiManager = (WifiManager) MyApplication.getContext().getSystemService(android.content.Context.WIFI_SERVICE);
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
	
	public static String getHostMAC() {
		WifiManager wifiManager = (WifiManager)MyApplication.getContext().getSystemService(Context.WIFI_SERVICE);
		if (!wifiManager.isWifiEnabled()) {  
            wifiManager.setWifiEnabled(true);    
		}  
		WifiInfo info = wifiManager.getConnectionInfo();
		return info.getMacAddress();
	}
}
