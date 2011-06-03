package me.cr3dossoft.WelcomeMessage.Commands;

import me.cr3dossoft.WelcomeMessage.WelcomeMessage;
import me.cr3dossoft.WelcomeMessage.Config.WMConfigHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WMReload implements CommandExecutor
{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if (!WelcomeMessage.hatSpielerRecht((Player) sender, "WelcomeMessage.reload")) return true;
		if(args.length != 1) return false;
		
		WMConfigHandler.onStartUp();
		
		return true;
	}

}
