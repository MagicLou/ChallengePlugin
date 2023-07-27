package de.magic_lou.challengespluginv2.achallenges.other.backport;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class BackPort implements Listener {


    private static final String TP = " teleportiert";
    private final PlayerManager playerManager;
    private final HashMap<Player, Boolean> sneaking = new HashMap<>();
    private final HashMap<Player, Entity> spot = new HashMap<>();
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private boolean running = false;
    public BackPort(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        if (!diff.equals(Challenge.ChallengeDiff.NORMAL)) diff = Challenge.ChallengeDiff.NORMAL;
        this.diff = diff;
    }

    public void start() {
        for (Player player : playerManager.getPlayers()) {
            player.sendMessage(ChatColor.GRAY + "Wenn du " + ChatColor.GREEN + "sneakst " + ChatColor.GRAY + "erstellst du einen " + ChatColor.GOLD + "Wegpunkt " + ChatColor.GRAY + "oder wirst zu einem vorhandenen" + ChatColor.GOLD + TP);
            sneaking.put(player, false);
        }
        resume();
    }

    public void pause() {
        running = false;
    }

    public void resume() {
        running = true;
    }

    public void stop() {
        for (Entity value : spot.values()) {
            value.remove();
        }
        spot.clear();
        sneaking.clear();
        pause();
    }


    public void wayKill(Player player) {
        if (!spot.containsKey(player)) {
            player.sendMessage(ChatColor.GRAY + "Du hast noch keine " + ChatColor.RED + "Waypoints" + ChatColor.GRAY + " erstellt");

        } else {
            player.sendMessage(ChatColor.GRAY + "Dein Waypoint wurde bei" + Utils.formateLocation(spot.get(player).getLocation(), ChatColor.GRAY, ChatColor.RED) + ChatColor.RED + " entfernt");
            spot.get(player).remove();
            spot.remove(player);
            sneaking.put(player, true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        spot.remove(event.getPlayer());
        sneaking.remove(event.getPlayer());
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (!running) return;
        if (player.isSneaking()) return;
        if (!sneaking.containsKey(player)) {
            sneaking.put(player, false);
            player.sendMessage(ChatColor.GRAY + "Wenn du " + ChatColor.GREEN + "sneakst " + ChatColor.GRAY + "erstellst du einen " + ChatColor.GOLD + "Wegpunkt " + ChatColor.GRAY + "oder wirst zu einem vorhandenen" + ChatColor.GOLD + TP);
        }
        if (Boolean.TRUE.equals(sneaking.get(player))) {
            sneaking.put(player, false);
            set(player);
        } else {
            sneaking.put(player, true);
            tp(player);
        }
    }

    private void set(Player player) {
        Bat tpEntity = (Bat) player.getWorld().spawnEntity(player.getLocation(), EntityType.BAT);
        tpEntity.addPotionEffect(Utils.potionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 255, false, false, false));
        tpEntity.addPotionEffect(Utils.potionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 255, false, false, false));
        tpEntity.setAI(false);
        spot.put(player, tpEntity);
        player.sendMessage(ChatColor.GRAY + "WayPoint bei " + Utils.formateLocation(tpEntity.getLocation(), ChatColor.DARK_GRAY, ChatColor.GOLD) + ChatColor.GRAY + " erstellt");
    }

    public void tp(Player player) {
        if (spot.containsKey(player)) {
            player.teleport(spot.get(player).getLocation());
            player.sendMessage(ChatColor.GRAY + "Zu WayPoint bei " + Utils.formateLocation(spot.get(player).getLocation(), ChatColor.DARK_GRAY, ChatColor.DARK_PURPLE) + ChatColor.GRAY + TP);
            spot.get(player).remove();
            spot.remove(player);
        }
    }


}
