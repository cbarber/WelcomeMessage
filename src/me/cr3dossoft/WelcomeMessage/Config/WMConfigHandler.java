package me.cr3dossoft.WelcomeMessage.Config;

import java.io.File;

import org.bukkit.util.config.Configuration;

public class WMConfigHandler
{
	private static String ordner = "plugins/WelcomeMessage";
	private static File configFile = new File(ordner + File.separator + "config.yml");
	
	private static Configuration config;
	
	private static Configuration load()
	{
		try
		{
			Configuration config = new Configuration(configFile);
			config.load();
			return config;

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static void onStartUp()
	{
		new File(ordner).mkdir();
		
		if (!configFile.exists())
		{
			try
			{
				configFile.createNewFile();
				config = load();
				writeBool("welcome", true);
				writeString("message.Default","Hallo {player} in default group");
				writeString("message.Admins","Hallo {player} in Admin group");
				return;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		config = load();
	}

	public static void writeBool(String key, boolean input)
	{
		config.setProperty(key, (input ? "true" : "false"));
		config.save();
	}
	
	public static boolean readBool(String key)
	{
		String s = config.getString(key);
		if(null == s) return false;
		return(s.equalsIgnoreCase("true") ? true : false);
	}
	
	public static void writeString(String key, String input)
	{
		config.setProperty(key, input);
		config.save();
	}
	
	public static String readString(String key)
	{
		String s = config.getString(key);
		if(null == s) return "";
		else return s;
	}
}
