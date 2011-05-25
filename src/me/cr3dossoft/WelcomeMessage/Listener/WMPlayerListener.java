package me.cr3dossoft.WelcomeMessage.Listener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.cr3dossoft.WelcomeMessage.WelcomeMessage;
import me.cr3dossoft.WelcomeMessage.Config.WMConfigHandler;
import me.cr3dossoft.WelcomeMessage.Config.WMMessage;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

import com.nijiko.permissions.PermissionHandler;

public class WMPlayerListener extends PlayerListener
{
	@SuppressWarnings("deprecation")
	@Override
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		if (!WMConfigHandler.readBool("welcome")) return;
		Player p = event.getPlayer();
		
		PermissionHandler permissionHandler = WelcomeMessage.getPermissionHandler();
		
		String group = "Default";
		
		if(permissionHandler != null)
		{
			group = permissionHandler.getGroup(p.getWorld().getName(), p.getName());
		}

		String[] s = WMMessage.getMessage(group);

		for (String txt : s)
		{
			Player player = event.getPlayer();
			
			String out = replacePlayer(txt, player);
			out = replacePlayers(out, player);
			out = replacePlayerCount(out, player);
			out = replaceMaxPlayerCount(out, player);
			
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
		return txt.replaceAll("\\{player\\}", p.getDisplayName());
	}

	private String replacePlayers(String txt, Player p)
	{
		StringBuilder players = new StringBuilder();

		Player[] ps = p.getServer().getOnlinePlayers();

		for (Player player : ps)
		{
			if(players.length() > 0)
			{
				players.append(",");
			}
			players.append(player.getDisplayName());
		}

		return txt.replaceAll("\\{players\\}", players.toString());
	}
	
	private String replacePlayerCount(String txt, Player p)
	{
		Integer playerCount = p.getServer().getOnlinePlayers().length;
		
		return txt.replaceAll("\\{playerCount\\}", playerCount.toString());
	}
	
	private String replaceMaxPlayerCount(String txt, Player p)
	{
		Integer maxPlayerCount = p.getServer().getMaxPlayers();
		
		return txt.replaceAll("\\{maxPlayerCount\\}", maxPlayerCount.toString());
	}
}