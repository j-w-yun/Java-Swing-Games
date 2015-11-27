package network;

import java.net.*;
import java.util.*;

public class Get
{
	public static String ip()
	{
		String save = null, ip;

	    try
	    {
	        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
	        while (interfaces.hasMoreElements())
	        {
	            NetworkInterface iface = interfaces.nextElement();

	            // filters out 127.0.0.1 and inactive interfaces
	            if (iface.isLoopback() || !iface.isUp())
	            {
	                continue;
	            }
	            
	            Enumeration<InetAddress> addresses = iface.getInetAddresses();
	            while(addresses.hasMoreElements())
	            {
	                InetAddress addr = addresses.nextElement();
	                ip = addr.getHostAddress();
	                if(ip.contains("192.168."))
	                {
	                	save = ip;
	                }
	            }
	        }
	    }

	    catch (SocketException e)
	    {
	        throw new RuntimeException(e);
	    }

	    return save;
	}
}