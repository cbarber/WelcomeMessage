package me.cr3dossoft.WelcomeMessage.API;

import org.bukkit.entity.Player;

public interface  WelcomeHandler
{
	
	/**
	 * WelcomeMessage use this function for get a message
	 * You can use the keywords (Look on the forum-post of the WelcomeMessage for a list of keywords)
	 * If there is no permission-plugin groupname = "Default"
	 * @param Name of the group
	 * @return the message for the group (null if you don't have a message)
	 */
	public String[] getMessageForGroup(String groupname);
	
	/**
	 * 
	 * @param player
	 * @return the message for the player (null if you don't have a message)
	 */
	public String[] getMessageForPlayer(Player player);
}
