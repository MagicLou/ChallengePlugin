package de.magic_lou.challengespluginv2.playermanagment;

import de.magic_lou.challengespluginv2.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class PlayerManager {

    private final List<Player> players = new ArrayList<>();
    private final List<Player> spectators = new ArrayList<>();

    public List<Player> getPlayers() {
        return players;
    }


    public void start() {
        resume();
        for (Player player : players) {
            player.getInventory().clear();
            player.updateInventory();
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(20);
            player.setHealthScale(20.0);
            player.setHealth(20.0);
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
        }
    }

    public void resume() {
        for (Player player : players) {
            player.teleport(Utils.setOnGround(player.getLocation()));
            player.setGameMode(GameMode.SURVIVAL);
        }
        for (Player spectator : spectators) {
            spectator.setGameMode(GameMode.SPECTATOR);
        }
    }

    public void remove(Player player) {
        players.remove(player);
        spectators.remove(player);
    }

    public void toggleSpectate(Player player) {
        if (players.contains(player)) addSpectators(player);
        else addPlayers(player);
    }

    public void addPlayers(Player player) {
        player.sendMessage(ChatColor.GRAY + "Du nimmst nun an der Challenge teil");
        spectators.remove(player);
        players.add(player);
        player.teleport(Utils.setOnGround(player.getLocation()));
        player.setGameMode(GameMode.SURVIVAL);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }





    public void addSpectators(Player spectator) {
        spectator.sendMessage(ChatColor.GRAY + "Du beobachtest nun die Challenge");
        players.remove(spectator);
        spectators.add(spectator);
        spectator.setGameMode(GameMode.SPECTATOR);
    }


    public boolean containsAny(Collection<Entity> entity) {
        for (Entity entity1 : entity) {
            if (players.contains(entity1)) return true;
        }
        return false;
    }

    public void honorPlayers(ChatColor color) {
        for (Player player : players) {
            Bukkit.broadcast(Component.text(color + player.getName()));
        }
    }

    public void honorSpectators(ChatColor color) {
        if (!spectators.isEmpty()) {
            for (Player player : spectators) {
                Bukkit.broadcast(Component.text(color + player.getName()));
            }
            if (spectators.size() > 1) Bukkit.broadcast(Component.text(color + "waren auch dabei"));
            else Bukkit.broadcast(Component.text(color + "war auch dabei"));
        }
    }


}
