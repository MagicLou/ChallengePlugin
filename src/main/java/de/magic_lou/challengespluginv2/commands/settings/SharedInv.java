package de.magic_lou.challengespluginv2.commands.settings;

import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class SharedInv implements Listener {

    private final PlayerManager playerManager;

    private boolean running = false;

    public SharedInv(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public boolean isRunning() {
        return running;
    }

    public void toggleRunning(Player player) {
        running = !running;
        if (running) player.sendMessage("Shared Inv ist jetzt" + ChatColor.GREEN + " an");
        else player.sendMessage("Shared Inv ist jetzt" + ChatColor.RED + " aus");
    }


}
