package me.firedragon5.ultraguilds.commands.guild.menu;

import me.firedragon5.ultraguilds.UltraGuilds;
import me.firedragon5.ultraguilds.Utils;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.ArrayList;
import java.util.List;

public class BannerMenu implements Listener {


	private final UltraGuilds plugin;

	public BannerMenu(UltraGuilds plugin) {
		this.plugin = plugin;
	}

	public Inventory inv;


	public void menuBanner(Player player) {
		inv = Bukkit.createInventory(null, 54, Utils.chat("&8Banner Menu"));

//		Create a menu with banners for the guild leader to chose from

		ItemStack item = new ItemStack(Material.WHITE_BANNER, 1);

		BannerMeta meta = (BannerMeta) item.getItemMeta();
		meta.setBaseColor(DyeColor.WHITE);

		List<Pattern> patterns = new ArrayList<Pattern>();

		patterns.add(new Pattern(DyeColor.RED, PatternType.STRIPE_TOP));
		patterns.add(new Pattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM));
		patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT));
		patterns.add(new Pattern(DyeColor.BLUE, PatternType.STRIPE_RIGHT));
		patterns.add(new Pattern(DyeColor.GREEN, PatternType.STRIPE_CENTER));

		meta.setPatterns(patterns);

		meta.setDisplayName(Utils.chat("&cRed &8| &0Black &8| &fWhite &8| &9Blue &8| &aGreen"));



		item.setItemMeta(meta);


		inv.setItem(0, item);



		player.openInventory(inv);


	}


	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		ItemStack clicked = e.getCurrentItem();
		if (e.getView().getTitle().equals(Utils.chat("&8Banner Menu"))) {
			e.setCancelled(true);


		}
	}


}
