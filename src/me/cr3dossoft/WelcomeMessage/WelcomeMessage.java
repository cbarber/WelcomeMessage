package me.cr3dossoft.WelcomeMessage;

import java.util.logging.Logger;

import me.cr3dossoft.WelcomeMessage.API.WelcomeHandler;
import me.cr3dossoft.WelcomeMessage.Commands.WMCommand;
import me.cr3dossoft.WelcomeMessage.Commands.WMReload;
import me.cr3dossoft.WelcomeMessage.Commands.WMSetCommand;
import me.cr3dossoft.WelcomeMessage.Config.WMConfigHandler;
import me.cr3dossoft.WelcomeMessage.Listener.WMPlayerListener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;

public class WelcomeMessage extends JavaPlugin
{

	private PluginDescriptionFile pdfFile;
	private static final Logger log = Logger.getLogger("Minecraft");
	private WMPlayerListener wmpl;
	
	private static PermissionHandler permissionHandler;

	@Override
	public void onDisable()
	{
		log.info(pdfFile.getName() + " disabled");
	}

	public static PermissionHandler getPermissionHandler()
	{
		return permissionHandler;
	}

	@Override
	public void onEnable()
	{
		pdfFile = this.getDescription();

		this.wmpl = new WMPlayerListener();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Type.PLAYER_JOIN, wmpl, Priority.High, this);

		WMConfigHandler.onStartUp();
		getCommand("welcome").setExecutor(new WMCommand());
		getCommand("wmset").setExecutor(new WMSetCommand());
		getCommand("wmreload").setExecutor(new WMReload());

		log.info(pdfFile.getName() + " " + pdfFile.getVersion() + " enabled");

		permissionsAufsetzen();
	}

	public static ChatColor getColorByString(String colorName)
	{
		ChatColor c;
		if (colorName.equalsIgnoreCase("aqua")) c = ChatColor.AQUA;
		else if (colorName.equalsIgnoreCase("black")) c = ChatColor.BLACK;
		else if (colorName.equalsIgnoreCase("blue")) c = ChatColor.BLUE;
		else if (colorName.equalsIgnoreCase("DARK_AQUA")) c = ChatColor.DARK_AQUA;
		else if (colorName.equalsIgnoreCase("DARK_BLUE")) c = ChatColor.DARK_BLUE;
		else if (colorName.equalsIgnoreCase("DARK_GRAY")) c = ChatColor.DARK_GRAY;
		else if (colorName.equalsIgnoreCase("DARK_GREEN")) c = ChatColor.DARK_GREEN;
		else if (colorName.equalsIgnoreCase("DARK_PURPLE")) c = ChatColor.DARK_PURPLE;
		else if (colorName.equalsIgnoreCase("DARK_RED")) c = ChatColor.DARK_RED;
		else if (colorName.equalsIgnoreCase("GOLD")) c = ChatColor.GOLD;
		else if (colorName.equalsIgnoreCase("GRAY")) c = ChatColor.GRAY;
		else if (colorName.equalsIgnoreCase("GREEN")) c = ChatColor.GREEN;
		else if (colorName.equalsIgnoreCase("LIGHT_PURPLE")) c = ChatColor.LIGHT_PURPLE;
		else if (colorName.equalsIgnoreCase("RED")) c = ChatColor.RED;
		else if (colorName.equalsIgnoreCase("YELLOW")) c = ChatColor.YELLOW;
		else c = ChatColor.WHITE;
		return c;
	}

	private void permissionsAufsetzen()
	{
		Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

		if (WelcomeMessage.permissionHandler == null)
		{
			if (permissionsPlugin != null)
			{
				WelcomeMessage.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
			}
			else
			{
				log.info("Permission system not detected, defaulting to OP");
			}
		}
	}

	public static boolean hatSpielerRecht(Player p, String label)
	{
		if (null == permissionHandler)
		{
			return p.isOp();
		}
		else
		{
			return permissionHandler.has(p, label);
		}
	}

	public void addWelcomeHandler(WelcomeHandler handler)
	{
		this.wmpl.addHandler(handler);
	}
	
	public void removeWelcomeHandler(WelcomeHandler handler)
	{
		this.wmpl.removeHandler(handler);
	}
}
