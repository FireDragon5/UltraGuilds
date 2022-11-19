package me.firedragon5.ultraguilds.commands.guild;

import me.firedragon5.ultraguilds.UltraGuilds;
import me.firedragon5.ultraguilds.Utils;
import me.firedragon5.ultraguilds.commands.guild.menu.BannerMenu;
import me.firedragon5.ultraguilds.commands.guild.menu.GuildMenus;
import me.firedragon5.ultraguilds.commands.guild.menu.MembersMenu;
import me.firedragon5.ultraguilds.filemanager.GuildManager;
import me.firedragon5.ultraguilds.filemanager.PlayerManager;
import me.firedragon5.ultraguilds.ranks.guildRank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GuildCommand implements CommandExecutor, TabCompleter {

	private final UltraGuilds plugin;

	public GuildCommand(UltraGuilds plugin) {
		this.plugin = plugin;
	}

	HashMap<UUID, String> invite = new HashMap<UUID, String>();



	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

//		/guilds <join|leave|create|info|list>
		Player player = (Player) sender;




//		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
//		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);

		File memberFile = new File("plugins/UltraGuilds/Guilds/Members.yml");
		YamlConfiguration memberData = YamlConfiguration.loadConfiguration(memberFile);




		if (sender instanceof Player){


			if (args.length < 1) {
				player.sendMessage(Utils.chat("&cPlease use /guilds <join|leave|create|info|list>"));
				return true;
			}

			if (args[0].equalsIgnoreCase("join")) {
				if (args.length < 2) {
					player.sendMessage(Utils.chat("&cPlease use /guilds join <guild>"));
					return true;
				}

//				Check if the guild is public or not
				if (!GuildManager.getGuildVisibility(args[1])) {
					player.sendMessage(Utils.chat("&cThis guild is not public"));
					return true;
				}

				String guild = args[1];

				if (!GuildManager.guildExists(guild)) {
					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildDoesntExist")
							.replace("%guild%", guild)
							.replace("%tag%", GuildManager.getGuildTag(guild))
							.replace("%rank%", PlayerManager.getGuildRank(player))
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())));
					return true;
				}

				if (!PlayerManager.getPlayerGuildName(player).equals("None")) {
					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildJoin.Error")
							.replace("%guild%", guild)
							.replace("%tag%", GuildManager.getGuildTag(guild))
							.replace("%rank%", PlayerManager.getGuildRank(player))
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())));

					return true;
				}

				String guildTag = GuildManager.getGuildTag(guild);

				PlayerManager.setFullPlayerGuild(player, guild, guildTag, "Member" ,0,0);



				List<String> members = memberData.getStringList("Members.Guilds." + guild + ".Members");
				members.add(player.getUniqueId().toString());

				memberData.set("Members.Guilds." + guild + ".Members", members);

//				Count the amount of members in the guild
				int memberCount = memberData.getStringList("Members.Guilds." + guild + ".Members").size();



				memberData.set("Members.Guilds." + guild + ".Counter", memberCount);


				try {
					memberData.save(memberFile);
				} catch (Exception e) {
					e.printStackTrace();
				}

				player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildJoin.Success")
						.replace("%guild%", guild)
						.replace("%tag%", GuildManager.getGuildTag(guild))
						.replace("%rank%", PlayerManager.getGuildRank(player))
						.replace("%prefix%", plugin.getConfig().getString("Prefix"))
						.replace("%player%", player.getName())));


				return true;
			}

			if (args[0].equalsIgnoreCase("create")){
				if (args.length < 3) {
					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildCreate.Error")
							.replace("%guild%", PlayerManager.getGuildName(player))
							.replace("%tag%", PlayerManager.getGuildTag(player))
							.replace("%rank%", PlayerManager.getGuildRank(player))
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())));
					return true;
				}


				String guild = args[1];
				String tag = args[2];



				if (GuildManager.guildExists(guild)) {
					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildAlreadyExists")
							.replace("%guild%", PlayerManager.getGuildName(player))
							.replace("%tag%", PlayerManager.getGuildTag(player))
							.replace("%rank%", PlayerManager.getGuildRank(player))
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())));
					return true;
				}

				if (!PlayerManager.getPlayerGuildName(player).equals("None")) {
					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildCreate.Fail")
							.replace("%guild%", PlayerManager.getGuildName(player))
							.replace("%tag%", PlayerManager.getGuildTag(player))
							.replace("%rank%", PlayerManager.getGuildRank(player))
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())));
					return true;
				}


				GuildManager.setFullGuildData(guild, tag, player.getUniqueId().toString(), player.getUniqueId().toString(),
						0, true, "None");

				String guildName = GuildManager.getGuildName(guild);
				String guildTag = GuildManager.getGuildTag(guild);
				String guildRank = PlayerManager.getGuildRank(player);
				int guildClass = PlayerManager.getGuildClass(player);
				int guildScore = PlayerManager.getGuildScore(player);

				PlayerManager.setFullPlayerGuild(player ,args[1], args[2], "Leader", 0, 0);

				memberData.set("Members.Guilds." + guild + ".GuildLeader", player.getUniqueId().toString());
				memberData.set("Members.Guilds." + guild + ".Counter", 0);
				memberData.set("Members.Guilds." + guild + ".Members", "None");


				try {

					memberData.save(memberFile);
				} catch (Exception e) {
					e.printStackTrace();
				}

				for (String msg : plugin.getConfig().getStringList("Messages.GuildCreate.Success")) {
					player.sendMessage(Utils.chat(msg
							.replace("%guild%", guild)
							.replace("%tag%", tag)
							.replace("%player%", player.getName())));
				}


				return true;

			}

			if (args[0].equalsIgnoreCase("leave")) {

				if (PlayerManager.getPlayerGuildName(player).equals("None")) {
					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildLeave.Error")
							.replace("%guild%", PlayerManager.getGuildName(player))
							.replace("%tag%", PlayerManager.getGuildTag(player))
							.replace("%rank%", PlayerManager.getGuildRank(player))
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())));
					return true;
				}

//				Check if the player is the guild leader if player is the guild leader then let them chose a new leader

				if (memberData.getString("Members.Guilds." + PlayerManager.getPlayerGuildName(player) +
						".GuildLeader").equals(player.getUniqueId().toString())) {
					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildLeave.Leader")
							.replace("%guild%", PlayerManager.getGuildName(player))
							.replace("%tag%", PlayerManager.getGuildTag(player))
							.replace("%rank%", PlayerManager.getGuildRank(player))
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())));

				} else {

					String guild = PlayerManager.getPlayerGuildName(player);

					String guildName = GuildManager.getGuildName(guild);
					String guildTag = GuildManager.getGuildTag(guild);
					String guildRank = PlayerManager.getGuildRank(player);
					int guildClass = PlayerManager.getGuildClass(player);
					int guildScore = PlayerManager.getGuildScore(player);

					PlayerManager.setFullPlayerGuild(player ,"None", "None", "None", 0, 0);

					List<String> members = memberData.getStringList("Members.Guilds." + guildName + ".Members");
					memberData.set("Members.Guilds." + guildName + ".Members", members.remove(player.getUniqueId().toString()));


					memberData.set("Members.Guilds." + guildName + ".Counter", members.size());


					try {

						memberData.save(memberFile);
					} catch (Exception e) {
						e.printStackTrace();
					}

					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildLeave.Success")
							.replace("%guild%", guildName)
							.replace("%tag%", guildTag)
							.replace("%player%", player.getName())));


				}
			}

			if (args[0].equalsIgnoreCase("members")){

				if (PlayerManager.getPlayerGuildName(player).equals("None")) {
					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildLeave.Error")
							.replace("%guild%", PlayerManager.getGuildName(player))
							.replace("%tag%", PlayerManager.getGuildTag(player))
							.replace("%rank%", PlayerManager.getGuildRank(player))
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())));
					return true;
				}

				MembersMenu menu = new MembersMenu();
				menu.createMenu(player);

			}

			if (args[0].equalsIgnoreCase("setleader")){
				if (memberData.getString("Members.Guilds." + PlayerManager.getPlayerGuildName(player) + ".GuildLeader")
					.equals(player.getUniqueId().toString())){
					if (args.length == 1){
						player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildSetLeader.Error")
								.replace("%prefix%", plugin.getConfig().getString("Prefix"))
								.replace("%player%", player.getName())));
						return true;
					}

					if (args.length == 2){
						if (Bukkit.getPlayer(args[1]) == null){
							player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildSetLeader.ErrorPlayer")
									.replace("%prefix%", plugin.getConfig().getString("Prefix"))
									.replace("%player%", player.getName())));
							return true;
						}

						Player target = Bukkit.getPlayer(args[1]);

						File targetPlayer = new File("plugins/UltraGuilds/Players/" + player.getUniqueId() + ".yml");
						YamlConfiguration targetData = YamlConfiguration.loadConfiguration(targetPlayer);

						if (targetData.getString("Guilds.GuildName").equals("None")){
							player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildSetLeader.ErrorPlayerGuild")
									.replace("%prefix%", plugin.getConfig().getString("Prefix"))
									.replace("%player%", player.getName())));
							return true;
						}

						if (!targetData.getString("Guilds.GuildName").equals(PlayerManager.getPlayerGuildName(player))){
							player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildSetLeader.ErrorPlayerGuild")
									.replace("%prefix%", plugin.getConfig().getString("Prefix"))
									.replace("%player%", player.getName())));
							return true;
						}

						memberData.set("Members.Guilds." + PlayerManager.getPlayerGuildName(player) + ".GuildLeader", target.getUniqueId().toString());
						GuildManager.setNewGuildLeader(PlayerManager.getPlayerGuildName(target), target);


						GuildManager.saveGuildConfig();

						try {
							memberData.save(memberFile);
						} catch (Exception e) {
							e.printStackTrace();
						}

						player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildSetLeader.Success")
								.replace("%prefix%", plugin.getConfig().getString("Prefix"))
								.replace("%player%", player.getName())
								.replace("%target%", target.getName())
								.replace("%guild%", PlayerManager.getPlayerGuildName(player))));

						target.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildSetLeader.SuccessTarget")
								.replace("%prefix%", plugin.getConfig().getString("Prefix"))
								.replace("%player%", player.getName())
								.replace("%target%", target.getName())
								.replace("%guild%", PlayerManager.getPlayerGuildName(player))));

					}
				}
			}

			if (args[1].equalsIgnoreCase("setspawn")){
				if (memberData.getString("Members.Guilds." + PlayerManager.getPlayerGuildName(player) + ".GuildLeader")
						.equals(player.getUniqueId().toString())){

//					/guild setspawn



					GuildManager.setGuildSpawnLocation(PlayerManager.getPlayerGuildName(player), player);

					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildSpawn.GuildSpawnSet")
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())
							.replace("%guild%", PlayerManager.getPlayerGuildName(player))));

				}
			}

			if (args[0].equalsIgnoreCase("spawn")){
//				Teleport the player to it guild spawn


				if (GuildManager.spawnSet(PlayerManager.getPlayerGuildName(player))){
					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildSpawn.GuildSpawnNotSet")
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())));
					return true;
				}
				player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildSpawn.GuildSpawnTeleport")
						.replace("%prefix%", plugin.getConfig().getString("Prefix"))
						.replace("%player%", player.getName())
						.replace("%guild%", PlayerManager.getPlayerGuildName(player))));
				GuildManager.teleportToSpawn(player, PlayerManager.getPlayerGuildName(player));

			}

			if (args[0].equalsIgnoreCase("settings")){
				if (!memberData.getString("Members.Guilds." + PlayerManager.getPlayerGuildName(player) + ".GuildLeader")
						.equals(player.getUniqueId().toString())) {
				player.sendMessage(Utils.chat(("&cYou must be the guild leader to use this command!")));
				return true;
				}else

				if (args.length == 1){
					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildSettings.Help")
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())));
					return true;
				}

				if (args[1].equalsIgnoreCase("visibility")){
					if (args[2].equalsIgnoreCase("public")){
						GuildManager.setGuildVisibility(PlayerManager.getPlayerGuildName(player), true);
						player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildSettings.VisibilityTrue")
								.replace("%prefix%", plugin.getConfig().getString("Prefix"))
								.replace("%player%", player.getName())
								.replace("%guild%", PlayerManager.getPlayerGuildName(player))
								.replace("%visibility%", args[2])));
					}
					if (args[2].equalsIgnoreCase("private")){
						GuildManager.setGuildVisibility(PlayerManager.getPlayerGuildName(player), false);
						player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildSettings.VisibilityFalse")
								.replace("%prefix%", plugin.getConfig().getString("Prefix"))
								.replace("%player%", player.getName())
								.replace("%guild%", PlayerManager.getPlayerGuildName(player))
								.replace("%visibility%", args[2])));
					}
				}

				if (args[1].equalsIgnoreCase("guildbanner")) {
					BannerMenu bannerMenu = new BannerMenu(plugin);
					bannerMenu.menuBanner(player);
				}




				if (args[1].equalsIgnoreCase("kick")){

//					/guild setting kick player

					if (args.length < 3){
						player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildSettings.KickUsage")
								.replace("%prefix%", plugin.getConfig().getString("Prefix"))
								.replace("%player%", player.getName())
								.replace("%guild%", PlayerManager.getPlayerGuildName(player))));
						return true;
					}

//					Target player even thought he is offfline
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[2]);

					File guildTarget = new File("plugins/UltraGuilds/Players/" + target.getUniqueId() + ".yml");
					YamlConfiguration targetData = YamlConfiguration.loadConfiguration(guildTarget);

//					Check if the target is in the same guild
					if (!targetData.getString("Guilds.GuildName").equals(PlayerManager.getPlayerGuildName(player))){
						player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildSettings.KickFail")
								.replace("%prefix%", plugin.getConfig().getString("Prefix"))
								.replace("%player%", player.getName())
								.replace("%guild%", PlayerManager.getPlayerGuildName(player))
								.replace("%target%", target.getName())));
						return true;
					}

//					Check if the target is the guild leader
					if (targetData.getString("Guilds.GuildName").equals(PlayerManager.getPlayerGuildName(player))){
						if (targetData.getString("Guilds.GuildLeader").equals(target.getUniqueId().toString())){
							player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildSettings.KickLeaderError")
									.replace("%prefix%", plugin.getConfig().getString("Prefix"))
									.replace("%player%", player.getName())
									.replace("%guild%", PlayerManager.getPlayerGuildName(player))
									.replace("%target%", target.getName())));
							return true;
						}
					}

					String guild = PlayerManager.getPlayerGuildName(player);

//					Remove the target from the guild
					targetData.set("Guilds.GuildName", "None");
					targetData.set("Guilds.Tag", "None");
					targetData.set("Guilds.Score", 0);
					targetData.set("Guilds.GuildRank", "None");
					targetData.set("Guilds.GuildClass", 0);

					List<String> members = memberData.getStringList("Members.Guilds." + guild + ".Members");
					memberData.set("Members.Guilds." + guild + ".Members", members.remove(target.getUniqueId().toString()));


					memberData.set("Members.Guilds." + guild + ".Counter", members.size());

					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildSettings.KickSuccess")
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())
							.replace("%guild%", PlayerManager.getPlayerGuildName(player))
							.replace("%target%", target.getName())));



						GuildManager.saveGuildConfig();

					try {
						targetData.save(guildTarget);
						memberData.save(memberFile);
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

			}
			
			if (args[0].equalsIgnoreCase("rankup")){

//

//				Get the rank of the player
				guildRank rank = guildRank.getPlayerRank(player);

//				Check the players next rank
				guildRank nextRank = guildRank.getNextRank(player);

//				Get the players score
				int score = PlayerManager.getPlayerGuildScore(player);

//				Get the score needed for the next rank
//				int scoreNeeded = guildRank.getNextRankScore(player);

//				Check if the player is max rank
				if (nextRank == null){
					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildRankUp.MaxRank")
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())
							.replace("%guild%", PlayerManager.getPlayerGuildName(player))));
					return true;
				}

//				Get Message
				String rankupMessage = 	plugin.getConfig().getString("Messages.GuildRankUp.Rankup")
						.replace("%prefix%", plugin.getConfig().getString("Prefix"))
						.replace("%player%", player.getName())
						.replace("%guild%", PlayerManager.getPlayerGuildName(player))
						.replace("%nextrank%", nextRank.toString());

//				Not enough score
				String rankupFail =	plugin.getConfig().getString("Messages.GuildRankUp.NoRankup")
						.replace("%prefix%", plugin.getConfig().getString("Prefix"))
						.replace("%player%", player.getName())
						.replace("%guild%", PlayerManager.getPlayerGuildName(player))
						.replace("%nextrank%", nextRank.toString());

//				Max rank
				String maxRank = plugin.getConfig().getString("Messages.GuildRankUp.MaxRank")
						.replace("%prefix%", plugin.getConfig().getString("Prefix"))
						.replace("%player%", player.getName())
						.replace("%guild%", PlayerManager.getPlayerGuildName(player))
						.replace("%rank%", guildRank.getUpgradeRankName(player));

				guildRank.rankUp(player, rankupMessage, rankupFail);

			}

			if (args[0].equalsIgnoreCase("invite")){
//				Invite the player that was type in as args[1]

				Player target = Bukkit.getPlayer(args[1]);

				File guildTarget = new File("plugins/UltraGuilds/Players/" + target.getUniqueId() + ".yml");
				YamlConfiguration targetData = YamlConfiguration.loadConfiguration(guildTarget);



				if (args[1].equalsIgnoreCase("")){
					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildInvite.NoPlayer")
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", target.getName())));
					return true;
				}



				if (targetData.getString("Guilds.GuildName").equals(PlayerManager.getPlayerGuildName(player))){
					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildInvite.SameGuild")
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", target.getName())));
					return true;
				}

				if (targetData.getString("Guilds.GuildName").equals("None")) {
//					Check if the players preform the command /guild accept

					invite.put(target.getUniqueId(), PlayerManager.getPlayerGuildName(player));



					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildInvite.InviteSent")
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())
							.replace("%target%", target.getName())
							.replace("%guild%", PlayerManager.getPlayerGuildName(player))));
					target.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildInvite.InviteReceived")
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())
							.replace("%target%", target.getName())
							.replace("%guild%", PlayerManager.getPlayerGuildName(player))));

				}

			}

			if (args[0].equalsIgnoreCase("accept")){
				if (invite.containsKey(player.getUniqueId())){
					String guildName = invite.get(player.getUniqueId());
					PlayerManager.setPlayerGuildName(player, guildName);
					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildInvite.Accept")
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())
							.replace("%guild%", guildName)));
					invite.remove(player.getUniqueId());

//					Add the guild to the players file

					String guildTag = GuildManager.getGuildTag(guildName);
					String guildRank = PlayerManager.getGuildRank(player);
					int guildClass = PlayerManager.getGuildClass(player);
					int guildScore = PlayerManager.getGuildScore(player);
					PlayerManager.setFullPlayerGuild(player , guildName, guildTag, guildRank, guildClass, guildScore);



//					Add the player to the guild members list
					List<String> members = memberData.getStringList("Members.Guilds." + guildName + ".Members");
					members.add(player.getUniqueId().toString());

					memberData.set("Members.Guilds." + guildName + ".Members", members);

					//				Count the amount of members in the guild

					int memberCount = memberData.getStringList("Members.Guilds." + guildName + ".Members").size();
					memberData.set("Members.Guilds." + guildName + ".Counter", memberCount);


					try{
						memberData.save(memberFile);
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}

			if (args[0].equalsIgnoreCase("deny")){
				if (invite.containsKey(player.getUniqueId())){
					String guildName = invite.get(player.getUniqueId());
					player.sendMessage(Utils.chat(plugin.getConfig().getString("Messages.GuildInvite.Deny")
							.replace("%prefix%", plugin.getConfig().getString("Prefix"))
							.replace("%player%", player.getName())
							.replace("%guild%", guildName)));
					invite.remove(player.getUniqueId());
				}
			}

			if (args[0].equalsIgnoreCase("guildlist")){
				GuildMenus guildMenus = new GuildMenus(plugin);
				guildMenus.GuildMenu(player);
			}



		}else {
			sender.sendMessage("You must be a player to use this command");
		}





		return true;
	}


	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		Player player = (Player) sender;


		File memberFile = new File("plugins/UltraGuilds/Guilds/Members.yml");
		YamlConfiguration memberData = YamlConfiguration.loadConfiguration(memberFile);




		List<String> arguments = new ArrayList<>();



		switch (args.length) {
			case 1:

				if (PlayerManager.getPlayerGuildName(player).equals("None")){
					arguments.add("join");
					arguments.add("guildlist");
				}else {
					arguments.add("leave");
					arguments.add("members");
					arguments.add("spawn");
					arguments.add("guildlist");
					arguments.add("rankup");
				}


				if (!PlayerManager.getPlayerGuildName(player).equals("None")) {
					if (memberData.getString("Members.Guilds." + PlayerManager.getPlayerGuildName(player) + ".GuildLeader")
							.equals(player.getUniqueId().toString())) {
						arguments.add("invite");
						arguments.add("settings");
					}
				}

//				Check if the player is in the hashmap
				if (invite.containsKey(player.getUniqueId())){
					arguments.add("accept");
					arguments.add("deny");
				}



				break;
			case 2:
				if (args[0].equalsIgnoreCase("join")) {

					String guild =	GuildManager.getGuildList();
					arguments.add(guild);


				}
				if (args[0].equalsIgnoreCase("invite")) {
					for (Player online : Bukkit.getOnlinePlayers()) {
						if (!online.getUniqueId().equals(player.getUniqueId())) {
							arguments.add(online.getName());
						}
					}
				}
				if (args[0].equalsIgnoreCase("kick")) {
					for (String member : memberData.getStringList("Members." + PlayerManager.getPlayerGuildName(player) + ".Members")) {
						arguments.add(Bukkit.getOfflinePlayer(UUID.fromString(member)).getName());
					}
				}
				if (args[0].equalsIgnoreCase("promote")) {
					for (String member : memberData.getStringList("Members." + PlayerManager.getPlayerGuildName(player) + ".Members")) {
						arguments.add(Bukkit.getOfflinePlayer(UUID.fromString(member)).getName());
					}
				}
				if (args[0].equalsIgnoreCase("demote")) {
					for (String member : memberData.getStringList("Members." + PlayerManager.getPlayerGuildName(player) + ".Members")) {
						arguments.add(Bukkit.getOfflinePlayer(UUID.fromString(member)).getName());
					}
				}

				if (!PlayerManager.getPlayerGuildName(player).equals("None")) {
					if (args[0].equalsIgnoreCase("setleader")) {
						if (memberData.getString("Members.Guilds." + PlayerManager.getPlayerGuildName(player) + ".GuildLeader").equals(player.getUniqueId().toString())) {
							for (String member : memberData.getStringList("Members." + PlayerManager.getPlayerGuildName(player) + ".Members")) {
								arguments.add(Bukkit.getOfflinePlayer(UUID.fromString(member)).getName());
							}
						}
					}
				}

				if (args[0].equalsIgnoreCase("settings")) {
					if (memberData.getString("Members.Guilds." + PlayerManager.getPlayerGuildName(player) + ".GuildLeader")
							.equals(player.getUniqueId().toString())) {
						arguments.add("setleader");
						arguments.add("promote");
						arguments.add("setspawn");
						arguments.add("disband");
						arguments.add("kick");
						arguments.add("demote");
						arguments.add("visibility");
						arguments.add("guildbanner");
					}


				}

				break;


			case 3:
				if (args[0].equalsIgnoreCase("create")) {

					if (args.length == 2) {
						arguments.add("<Name>");
					}
					if (args.length == 3) {
						arguments.add("<Tag>");
					}
				}


				if (args[0].equalsIgnoreCase("settings")) {
					if (args[1].equalsIgnoreCase("visibility")) {
//						Check the guild file and see if that guild is public or not
						if (GuildManager.getGuildVisibility(PlayerManager.getPlayerGuildName(player))) {
							arguments.add("private");
						} else {
							arguments.add("public");
						}
					}
				}



				break;

			case 4:
				if (args[0].equalsIgnoreCase("leave")) {
					String guild = PlayerManager.getPlayerGuildName(player);
					arguments.add(guild);
				}
				break;


		}

		List<String> result = new ArrayList<>();
		if (arguments.isEmpty()) {
			return null;
		}
		for (String a : arguments) {
			if (a.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
				result.add(a);
			}
		}
		return result;


	}

}
