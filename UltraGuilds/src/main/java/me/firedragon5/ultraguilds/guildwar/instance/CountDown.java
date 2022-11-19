package me.firedragon5.ultraguilds.guildwar.instance;

import me.firedragon5.ultraguilds.filemanager.ConfigManager;
import me.firedragon5.ultraguilds.guildwar.GameState;
import me.firedragon5.ultraguilds.UltraGuilds;
import me.firedragon5.ultraguilds.Utils;
import org.bukkit.scheduler.BukkitRunnable;

public class CountDown extends BukkitRunnable {

	private UltraGuilds plugin;
	private Arena arena;

	private int countDownSeconds;


	public CountDown(UltraGuilds plugin, Arena arena) {
		this.plugin = plugin;
		this.arena = arena;
		this.countDownSeconds = ConfigManager.getCountDownSeconds();

	}



	public void start(){
		arena.setState(GameState.COUNTDOWN);

		runTaskTimer(plugin, 0, 20);

	}

	@Override
	public void run() {
		if(countDownSeconds == 0){
			cancel();
			arena.start();
			return;
		}

		if (countDownSeconds <= 10 || countDownSeconds % 15 == 0) {
			arena.sendMessage(Utils.chat("&eThe game will start in &6" + countDownSeconds + " &eseconds!"
					+ (countDownSeconds == 1 ? "" : "s") + "."));

		}

		countDownSeconds--;

	}

}
