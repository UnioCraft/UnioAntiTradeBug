package me.UnioDex.AntiTradeBug;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.Zrips.TradeMe.TradeMe;
import net.ess3.api.events.UserBalanceUpdateEvent;

public final class Main extends JavaPlugin implements Listener {

	private TradeMe tradeMe;

	public void onEnable() 
	{
		getServer().getPluginManager().registerEvents(this, this);
		Plugin tradeMePlugin = Bukkit.getPluginManager().getPlugin("TradeMe");
		if (tradeMePlugin == null) {
			Bukkit.getLogger().log(Level.SEVERE, "TradeMe couldn't find. UnioAntiTradeBug unloaded.");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		Plugin essentials  = Bukkit.getPluginManager().getPlugin("Essentials");
		if (essentials == null) {
			Bukkit.getLogger().log(Level.SEVERE, "Essentials couldn't find. UnioAntiTradeBug unloaded.");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		tradeMe = TradeMe.getInstance();
		saveDefaultConfig();
	}

	@EventHandler
	public void onMoneyUpdate(UserBalanceUpdateEvent event) {
		Player player = event.getPlayer();
		if (tradeMe.getUtil().isTrading(player)) {
			player.closeInventory();
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.denied")));
			Bukkit.getLogger().log(Level.WARNING, player.getName() + "'s money has changed while trading.");
		}
	}
}