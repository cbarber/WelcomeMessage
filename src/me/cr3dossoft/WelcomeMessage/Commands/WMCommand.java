package me.cr3dossoft.WelcomeMessage.Commands;

import me.cr3dossoft.WelcomeMessage.WelcomeMessage;
import me.cr3dossoft.WelcomeMessage.Config.WMConfigHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WMCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(!WelcomeMessage.hatSpielerRecht((Player)sender, "WelcomeMessage.toggleWelcome")) return true;
		if(args.length != 0) return false;
		
		toggleWM();
		sender.sendMessage("WelcomeMessage is " + (WMConfigHandler.readBool("welcome") ?  "on" : "off"));
		return true;
	}

	private void toggleWM()
	{
		WMConfigHandler.writeBool("welcome", (WMConfigHandler.readBool("welcome") ? false : true));
	}
}
