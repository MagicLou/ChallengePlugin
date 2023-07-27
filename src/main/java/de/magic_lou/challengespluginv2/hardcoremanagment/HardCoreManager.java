package de.magic_lou.challengespluginv2.hardcoremanagment;

import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.Objects;

public class HardCoreManager implements Listener {

    private final PlayerManager playerManager;
    private HardCoreType gameDiff = HardCoreType.NORMAL;


    public HardCoreManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public HardCoreType getGameDiff() {
        return gameDiff;
    }

    public void setGameDiff(HardCoreType gameDiff) {
        this.gameDiff = gameDiff;
        boolean reg = !gameDiff.equals(HardCoreType.ULTRA_HARDCORE);
        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.NATURAL_REGENERATION, reg);
        }
        for (Player player : playerManager.getPlayers()) {
            player.setHealthScale(20);
        }

    }

    @EventHandler
    public void onRegenerate(EntityRegainHealthEvent event) {
        if (!event.getEntityType().equals(EntityType.PLAYER)) return;
        if (gameDiff.equals(HardCoreType.ULTRA_ULTRA_HARDCORE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!event.getEntityType().equals(EntityType.PLAYER)) return;
        if (gameDiff.equals(HardCoreType.ULTRA_ULTRA_HARDCORE)) {
            Player player = (Player) event.getEntity();

            player.setHealth(Math.max(0, player.getHealth() - event.getFinalDamage()));
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(player.getHealth());
            player.setHealthScale(player.getHealth());
            event.setDamage(0);
        }
    }


    public enum HardCoreType {
        NORMAL, HARDCORE, ULTRA_HARDCORE, ULTRA_ULTRA_HARDCORE
    }


}
