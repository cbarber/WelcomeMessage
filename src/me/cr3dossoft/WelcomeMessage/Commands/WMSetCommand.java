package me.cr3dossoft.WelcomeMessage.Commands;

import me.cr3dossoft.WelcomeMessage.WelcomeMessage;
import me.cr3dossoft.WelcomeMessage.Config.WMConfigHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WMSetCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if (!WelcomeMessage.hatSpielerRecht((Player) sender, "WelcomeMessage.setMessage")) return true;
		if (args.length <= 1) return false;

		String x = "";

		for (int i = 1; i < args.length; i++)
		{
			x += (args[i] + " ");
		}

		WMConfigHandler.writeString("message." + args[0], x);

		sender.sendMessage("Changed welcome message for " + args[0] + " changed");

		return true;
	}

}
