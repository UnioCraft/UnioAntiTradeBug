package me.UnioDex.AntiTradeBug;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.Zrips.TradeMe.TradeMe;
import net.md_5.bungee.api.ChatColor;

public final class Main extends JavaPlugin implements Listener {

	private TradeMe tradeMe;
	private FileConfiguration fc;

	public void onEnable() 
	{
		getServer().getPluginManager().registerEvents(this, this);
		Plugin tradeMePlugin = Bukkit.getPluginManager().getPlugin("TradeMe");
		if (tradeMePlugin == null) {
			Bukkit.getLogger().log(Level.SEVERE, "TradeMe couldn't find. UnioAntiTradeBug unloaded.");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		tradeMe = TradeMe.getInstance();
		saveDefaultConfig();
		fc = this.getConfig();
	}

	@EventHandler
	public void onCommandUpdate(PlayerCommandPreprocessEvent event) 
	{
		Player p = event.getPlayer();

		if (tradeMe.getUtil().isTrading(p)) {
			event.setCancelled(true);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', fc.getString("messages.commandDenied")));
			Bukkit.getLogger().log(Level.INFO, p.getName() + " used a command while trading. UnioAntiTradeBug cancelled that.");
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) 
	{
		Player p = event.getPlayer();

		if (tradeMe.getUtil().isTrading(p)) {
			event.setCancelled(true);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', fc.getString("messages.chatDenied")));
			Bukkit.getLogger().log(Level.INFO, p.getName() + " used a command while trading. UnioAntiTradeBug cancelled that.");
		}
	}
}