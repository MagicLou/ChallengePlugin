package de.magic_lou.challengespluginv2.timer;

import de.magic_lou.challengespluginv2.Main;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class PauseStopListener implements Listener {


    private final PlayerManager playerManager;
    private final HashMap<Player, Location> loc = new HashMap<>();
    private boolean running = false;

    public PauseStopListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public void setLoc(Player player, Location location) {
        loc.put(player, location);
    }



    public void setPaused(boolean runs) {
        if (playerManager.getPlayers().isEmpty()) return;
        for (Player player : playerManager.getPlayers()) {
            loc.put(player, player.getLocation());

            running = runs;

            player.getWorld().setGameRule(GameRule.DO_FIRE_TICK, !running);
        }
    }

    public void sendMes(Player player) {
        String message = ChatColor.GRAY + "You" + ChatColor.RED + " cant" + ChatColor.GRAY + " do that, the Timer" + ChatColor.RED + " is pause/stopped";
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message)); //Need to

    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (running) return;
        Player player = event.getPlayer();
        if (playerManager.getPlayers().contains(player)) {
            Location playerLoc = player.getLocation();
            if (!loc.containsKey(player)) loc.put(player, player.getLocation());
            Location lastLoc = loc.get(player);
            if (playerLoc.getWorld() == lastLoc.getWorld() && (playerLoc.getBlockX() >= lastLoc.getBlockX() - 5
                    && playerLoc.getBlockX() <= lastLoc.getBlockX() + 5) && (playerLoc.getBlockZ() >= lastLoc.getBlockZ() - 5
                    && playerLoc.getBlockZ() <= lastLoc.getBlockZ() + 5 && (playerLoc.getBlockY() >= lastLoc.getBlockY() - 5
                    && playerLoc.getBlockY() <= lastLoc.getBlockY() + 5))) {
                            return;
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(loc.get(player));
                }
            }.runTaskLater(Main.instance, 1);
            sendMes(player);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (running) return;
        event.setCancelled(true);
        sendMes(event.getPlayer());
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (running) return;
        event.setCancelled(true);
        sendMes(event.getPlayer());
    }

    @EventHandler
    public void onPlayerPicKUpItem(PlayerAttemptPickupItemEvent event) { //here
        if (running) return;
        event.setCancelled(true);
        sendMes(event.getPlayer());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (running) return;
        event.setCancelled(true);
        sendMes(event.getPlayer());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (running) return;
        event.setCancelled(true);
        sendMes(event.getPlayer());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (running) return;
        if (event.getEntity() instanceof Player player) {
            sendMes(player);
        }
        event.setCancelled(true);
    }


    @EventHandler
    public void onEntityTarget(EntityTargetLivingEntityEvent event) {
        if (running) return;
        event.setCancelled(true);
    }
}
