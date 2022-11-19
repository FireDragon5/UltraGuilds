package me.firedragon5.ultraguilds.guildwar.instance;

import me.firedragon5.ultraguilds.guildwar.GameState;
import me.firedragon5.ultraguilds.Utils;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Game {

	private Arena arena;

	private HashMap<UUID, Integer> points;

	public Game(Arena arena) {
		this.arena = arena;
		this.points = new HashMap<>();
	}

	public void start(){
		arena.setState(GameState.LIVE);
		arena.sendMessage(Utils.chat("&eThe game has started!"));

		for (UUID uuid : arena.getPlayers()){
			points.put(uuid, 0);

		}
	}

	public void addPoint(Player player){
		int playerPoints = points.get(player.getUniqueId()) + 1;
		if (playerPoints == 20){
			arena.sendMessage(Utils.chat("&e" + player.getName() + " has won the game!"));
			arena.reset(true);
			return;
		}else {
			player.sendMessage(Utils.chat("&a+1 point!"));
			points.replace(player.getUniqueId(), playerPoints);
		}
	}


}
