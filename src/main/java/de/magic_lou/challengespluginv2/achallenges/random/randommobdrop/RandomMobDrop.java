package de.magic_lou.challengespluginv2.achallenges.random.randommobdrop;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

public class RandomMobDrop implements Listener {

    private static final Random random = new Random();
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private boolean running = false;
    private final EnumMap<EntityType, Material> entitys = new EnumMap<>(EntityType.class);

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void shuffle() {
        List<EntityType> typs = Utils.getEntityTypes(Utils.EntityTypeCats.LIVING);
        List<Material> mats = Utils.getMaterialList(Utils.MaterialType.ITEM);
        Collections.shuffle(mats);
        for (EntityType typ : typs) {
            entitys.put(typ, mats.get(0));
            mats.remove(0);
            if (mats.isEmpty()) {
                mats = Utils.getMaterialList(Utils.MaterialType.ITEM);
                Collections.shuffle(mats);
            }
        }
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!running) return;
        if (event.getEntity().getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.COMMAND)) {
            event.setDroppedExp(0);
            event.setShouldPlayDeathSound(false);
            return;
        }
        if (!diff.equals(Challenge.ChallengeDiff.EASY)) event.getDrops().clear();

        Material mat;
        if (diff.equals(Challenge.ChallengeDiff.NORMAL)) mat = entitys.get(event.getEntityType());
        else {
            mat = Utils.getRandomMaterial(Utils.MaterialType.ITEM);
        }
        event.getDrops().add(new ItemStack(mat, random.nextInt(16)));


    }


}
