package me.cr3dossoft.WelcomeMessage.Listener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.cr3dossoft.WelcomeMessage.WelcomeMessage;
import me.cr3dossoft.WelcomeMessage.API.WelcomeHandler;
import me.cr3dossoft.WelcomeMessage.Config.WMConfigHandler;
import me.cr3dossoft.WelcomeMessage.Config.WMMessage;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

import com.nijiko.permissions.PermissionHandler;

public class WMPlayerListener extends PlayerListener
{

	private ArrayList<WelcomeHandler> plugins;

	public WMPlayerListener()
	{
		plugins = new ArrayList<WelcomeHandler>();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		if (!WMConfigHandler.readBool("welcome")) return;
		Player p = event.getPlayer();

		PermissionHandler permissionHandler = WelcomeMessage.getPermissionHandler();

		String group = "Default";

		if (permissionHandler != null)
		{
			World world = p.getWorld();
			if(null != world) group = permissionHandler.getGroup(world.getName(), p.getName());
		}

		String[] s = WMMessage.getMessage(group);

		sendAMessage(p, s);

		for (WelcomeHandler wm : plugins)
		{
			sendAMessage(p, wm.getMessageForGroup(group));
			sendAMessage(p, wm.getMessageForPlayer(p));
		}
	}

	private void sendAMessage(Player p, String[] s)
	{
		if (null == s) return;
		for (String txt : s)
		{
			String out = replacePlayer(txt, p);
			out = replacePlayers(out, p);
			out = replacePlayerCount(out, p);
			out = replaceMaxPlayerCount(out, p);

			Pattern pat = Pattern.compile("\\{[\\w]+\\}");
			Matcher m = pat.matcher(out);

			while (m.find())
			{
				String match = m.group();
				match = match.substring(1, match.length() - 1);
				ChatColor c = WelcomeMessage.getColorByString(match);
				out = m.replaceFirst(c + "");
				m = pat.matcher(out);
			}
			p.sendMessage(out);
		}
	}

	private String replacePlayer(String txt, Player p)
	{
		if(null == txt) return "";
		if(null == p) return txt;
		
		return txt.replaceAll("\\{player\\}", p.getName());
	}

	private String replacePlayers(String txt, Player p)
	{
		if(null == txt) return "";
		if(null == p) return txt;
		
		StringBuilder players = new StringBuilder();

		Player[] ps = p.getServer().getOnlinePlayers();

		for (Player player : ps)
		{
			if (players.length() > 0)
			{
				players.append(", ");
			}
			players.append(player.getName());
		}

		return txt.replaceAll("\\{players\\}", players.toString());
	}

	private String replacePlayerCount(String txt, Player p)
	{
		if(null == txt) return "";
		if(null == p) return txt;
		
		Integer playerCount = p.getServer().getOnlinePlayers().length;

		return txt.replaceAll("\\{playerCount\\}", playerCount.toString());
	}

	private String replaceMaxPlayerCount(String txt, Player p)
	{
		if(null == txt) return "";
		if(null == p) return txt;
		
		Integer maxPlayerCount = p.getServer().getMaxPlayers();

		return txt.replaceAll("\\{maxPlayerCount\\}", maxPlayerCount.toString());
	}

	public void addHandler(WelcomeHandler handler)
	{
		System.out.println("add Handler");
		if (null == handler) return;
		plugins.add(handler);
	}

	public void removeHandler(WelcomeHandler handler)
	{
		if (null == handler) return;
		plugins.remove(handler);
	}
}