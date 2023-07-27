package de.magic_lou.challengespluginv2.achallenges.random.randomentityspawnonkill;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.EnumMap;

public class RandomEntitySpawnOnKill implements Listener {

    private EnumMap<EntityType, EntityType> entityTypes = new EnumMap<>(EntityType.class);
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private boolean running = false;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
    }

    public void shuffle() {
        entityTypes = Utils.setEntityTypeHashMap();
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!running) return;
        if (diff.equals(Challenge.ChallengeDiff.NORMAL))
            event.getEntity().getLocation().getWorld().spawnEntity(event.getEntity().getLocation(), entityTypes.get(event.getEntity().getType()));
        else
            event.getEntity().getLocation().getWorld().spawnEntity(event.getEntity().getLocation(), Utils.getRandomEntityType(Utils.EntityTypeCats.ALL));
    }


}
