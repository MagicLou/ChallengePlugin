package de.magic_lou.challengespluginv2.timer;

import de.magic_lou.challengespluginv2.commands.ResetCommand;
import de.magic_lou.challengespluginv2.hardcoremanagment.HardCoreManager;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

public class EndListener implements Listener {

    private final Timer timer;
    private final PlayerManager playerManager;
    private final HardCoreManager hardCoreManager;
    private final ResetCommand resetCommand;

    public EndListener(Timer timer, PlayerManager playerManager,HardCoreManager hardCoreManager, ResetCommand resetCommand) {
        this.timer = timer;
        this.playerManager = playerManager;
        this.hardCoreManager = hardCoreManager;
        this.resetCommand = resetCommand;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setCancelled(true);
        event.getDrops().clear();
        event.setKeepInventory(true);

        for (Player players : playerManager.getPlayers()) {
            players.setGameMode(GameMode.SPECTATOR);
        }
        timer.pauseTimer();
        Bukkit.broadcast(Component.text(ChatColor.DARK_RED + "Challenge pausiert"));
        Bukkit.broadcast(Component.text(ChatColor.WHITE + "Verwende " + ChatColor.DARK_PURPLE + "/reset " + ChatColor.WHITE + "um eine neue Welt zu generieren oder" + ChatColor.DARK_PURPLE + " /timer resume bzw. /timer start " + ChatColor.WHITE + "um die Challenge fort zu setzen"));
        Bukkit.broadcast(Component.text(ChatColor.WHITE + "Reason:"));
        Bukkit.broadcast(Objects.requireNonNull(event.deathMessage()));
        if (hardCoreManager.getGameDiff() != HardCoreManager.HardCoreType.NORMAL) {
            resetCommand.reset();
        }


    }


    @EventHandler
    public void onGameBeaten(EntityDeathEvent event) {
        if (event.getEntityType().equals(EntityType.ENDER_DRAGON)) {
            timer.end();
        }
    }


}
