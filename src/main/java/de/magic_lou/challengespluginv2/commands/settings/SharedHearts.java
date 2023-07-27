package de.magic_lou.challengespluginv2.commands.settings;

import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class SharedHearts implements Listener {

    private final PlayerManager playerManager;
    private boolean running = false;


    public SharedHearts(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public boolean isRunning() {
        return running;
    }

    public void toggleRunning() {
        running = !running;
        if (running) Bukkit.broadcast(Component.text("Shared Hears ist jetzt" + ChatColor.GREEN + " an"));
        else Bukkit.broadcast(Component.text("Shared Hears ist jetzt" + ChatColor.RED + " aus"));
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!running) return;
        if (event.getEntity() instanceof Player) {
            for (Player otherPlayer : playerManager.getPlayers()) {
                otherPlayer.setHealth(otherPlayer.getHealth() - event.getFinalDamage());
                otherPlayer.playSound(otherPlayer.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 1);
            }
            event.setCancelled(true);
        }
    }


}
