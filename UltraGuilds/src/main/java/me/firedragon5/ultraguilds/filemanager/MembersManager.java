//package me.firedragon5.ultraguilds.filemanager;
//
//import me.firedragon5.ultraguilds.UltraGuilds;
//import org.bukkit.configuration.file.FileConfiguration;
//import org.bukkit.configuration.file.YamlConfiguration;
//import org.bukkit.entity.Player;
//
//import java.io.File;
//import java.util.Arrays;
//import java.util.List;
//
//public class MembersManager {
//
//
//	private static FileConfiguration membersConfig;
//
//	public static void setConfig(FileConfiguration config) {
//		MembersManager.membersConfig = config;
//	}
//
//	public static FileConfiguration getConfig() {
//		return membersConfig;
//	}
//
//	public static void setMembersData(Player player) {
//		File membersFile = new File(UltraGuilds.getInstance().getDataFolder() + "/Guilds/Members.yml");
//		if (!membersFile.exists()) {
//			membersFile.getParentFile().mkdirs();
//			UltraGuilds.getInstance().saveResource("/Guilds/Members.yml", false);
//		}
//		membersConfig = YamlConfiguration.loadConfiguration(membersFile);
//	}
//
// /*
//  * Getters
//  */
//
//	public static String getMemberGuild(Player player) {
//		return membersConfig.getString("Members.Guilds" + PlayerManager.getPlayerGuildName(player));
//	}
//
//	public static String getMemberGuildLeader(Player player) {
//		return membersConfig.getString("Members.Guilds" + PlayerManager.getPlayerGuildName(player) + ".GuildLeader");
//	}
//
//	public static int getMemberGuildCounter(Player player) {
//		return membersConfig.getInt("Members.Guilds" + PlayerManager.getPlayerGuildName(player) + ".Counter");
//	}
//
////	List of all the members in the guild
//	public static String getMemberGuildMembers(Player player) {
//		return membersConfig.getString("Members.Guilds" + PlayerManager.getPlayerGuildName(player) + ".Members");
//	}
//
//
//
// /*
//  * Setters
//  */
//
//
//	public static void setMemberGuild(String[] args) {
//		membersConfig.set("Members.Guilds", Arrays.toString(args));
//	}
//
//	public static void setMemberGuildLeader(String[] args, Player player) {
//		membersConfig.set("Members.Guilds" + Arrays.toString(args) + ".GuildLeader", player.getUniqueId().toString());
//
//	}
//
////	Get the size of the list of members in the guild
//	public static void setMemberGuildCounter(String[] args) {
//
//		List<String> members = memberData.getStringList("Members.Guilds." + guild + ".Members");
//		memberData.set("Members.Guilds." + guild + ".Members", members.remove(target.getUniqueId().toString()));
//
//
//		memberData.set("Members.Guilds." + guild + ".Counter", members.size());
//
//		membersConfig.set("Members.Guilds" + Arrays.toString(args) + ".Counter", );
//	}
//
//
//
//
//
//
//
//
//
//}
