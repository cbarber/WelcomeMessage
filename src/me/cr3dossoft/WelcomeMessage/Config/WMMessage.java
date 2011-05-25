package me.cr3dossoft.WelcomeMessage.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class WMMessage
{
	private static String ordner = "plugins/WelcomeMessage";
	
	public static String[] getMessage(String group)
	{
			String message = WMConfigHandler.readString("message." + group);
			if(null == message)
			{
				message = WMConfigHandler.readString("message.Default");
				if(null == message) return new String[]{""};
			}
			
			String[] s = new String[1000];
			
			if(isFile(message))
			{
				File f = new File(ordner + File.separator + WMConfigHandler.readString("message." + group));
				try
				{
					BufferedReader reader = new BufferedReader(new FileReader(f));
					String input;
					int i = 0;
					while((input = reader.readLine()) != null) {
						s[i] = input;
						i++;
						if(i==1000) break;
					}
				}
				catch(Exception e)
				{
					s[0] = "";
				}
				return s;
			}
			else
			{
				
				s = message.split("\\{nl\\}");
				return s;
			}
		
	}

	private static boolean isFile(String message)
	{
		File f = new File(ordner + File.separator + message);
		
		if(f.exists()) return true;
		return false;
	}
}
