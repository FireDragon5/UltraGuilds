package me.firedragon5.ultraguilds.commands.guild.menu;

import me.firedragon5.ultraguilds.Utils;
import me.firedragon5.ultraguilds.filemanager.GuildManager;
import me.firedragon5.ultraguilds.filemanager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MembersMenu implements Listener {



	public Inventory inv;

	public void createMenu(Player player){

		inv = Bukkit.createInventory(null, 54, Utils.chat("&8&lMembers"));

		File memberFile = new File("plugins/UltraGuilds/Guilds/Members.yml");
		YamlConfiguration memberData = YamlConfiguration.loadConfiguration(memberFile);

//		File guildPlayer = new File("plugins/UltraGuilds/Players/" + player.getUniqueId() + ".yml");
//		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(guildPlayer);
//
//		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
//		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);

		String guildName = PlayerManager.getPlayerGuildName(player);
		String guildTag = GuildManager.getGuildTag(guildName);
		List<String> members = memberData.getStringList("Members.Guilds." + guildName + ".Members");
		String guildLeaders = memberData.getString("Members.Guilds." + guildName + ".GuildLeader");
		String guildCreator = GuildManager.getGuildCreator(guildName);
		List<String> membersList = new ArrayList<>();

		for (String member : members) {
			membersList.add(member);
		}



//		Show on the first row only the guild name
		ItemStack guildNameSetting = new ItemStack(Material.NAME_TAG);
		ItemMeta guildNameMeta = guildNameSetting.getItemMeta();
		guildNameMeta.setDisplayName(Utils.chat(guildTag));
		List<String> guildNameLore = new ArrayList<>();
		guildNameLore.add(Utils.chat("&6&lGuild Creator: &b" + Bukkit.getOfflinePlayer(UUID.fromString(guildCreator)).getName()));
		guildNameMeta.setLore(guildNameLore);
		guildNameSetting.setItemMeta(guildNameMeta);
		inv.setItem(4, guildNameSetting);

//		Display the guild leader first and then the members
		ItemStack guildLeader = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta guildLeaderMeta = (SkullMeta) guildLeader.getItemMeta();
		List <String> guildLeaderLore = new ArrayList<>();
		guildLeaderMeta.setOwner(Bukkit.getOfflinePlayer(UUID.fromString(guildLeaders)).getName());
		guildLeaderLore.add(Utils.chat("&l&bGuild Leader"));
		guildLeaderMeta.setLore(guildLeaderLore);
		guildLeaderMeta.setDisplayName(Utils.chat("&l&b" + Bukkit.getOfflinePlayer(UUID.fromString(guildLeaders)).getName()));
		guildLeader.setItemMeta(guildLeaderMeta);
		inv.setItem(9, guildLeader);


		int j = 0;
		for (String member : membersList) {
			ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
			skullMeta.setOwner(Bukkit.getOfflinePlayer(UUID.fromString(member)).getName());
			List<String> skullLore = new ArrayList<>();
			skullMeta.setDisplayName(Utils.chat("&l&b" + Bukkit.getOfflinePlayer(UUID.fromString(member)).getName()));
			skullLore.add(Utils.chat("&l&bMember"));
			skullMeta.setLore(skullLore);
			skull.setItemMeta(skullMeta);
			inv.setItem(11 + j, skull);
			j++;
		}

//		Close the gui
		ItemStack close = new ItemStack(Material.BARRIER);
		ItemMeta closeMeta = close.getItemMeta();
		closeMeta.setDisplayName(Utils.chat("&l&cClose"));
		close.setItemMeta(closeMeta);
		inv.setItem(45, close);
//		If slot is empty add a glass pane

		for (int i = 0; i < inv.getSize(); i++) {
			if (inv.getItem(i) == null) {
				ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
				ItemMeta glassMeta = glass.getItemMeta();
				glassMeta.setDisplayName(Utils.chat("&l&c"));
				glass.setItemMeta(glassMeta);
				inv.setItem(i, glass);
			}
		}


		player.openInventory(inv);


	}


	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();

		if (event.getView().getTitle().equals(Utils.chat("&8&lMembers"))) {
			event.setCancelled(true);
			if (clicked == null || clicked.getType() == Material.AIR) return;

			if (clicked.getType() == Material.BARRIER) {
				player.closeInventory();
			}
		}
	}





}
