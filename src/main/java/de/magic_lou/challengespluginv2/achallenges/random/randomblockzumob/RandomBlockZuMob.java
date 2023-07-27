package de.magic_lou.challengespluginv2.achallenges.random.randomblockzumob;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class RandomBlockZuMob implements Listener {


    private static final Random random = new Random();
    private final EnumMap<Material, EntityType> matEntity = new EnumMap<>(Material.class);
    private final Map<Entity, Collection<ItemStack>> entityLoot = new HashMap<>();
    private boolean running = false;
    private boolean explosion = false;
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;

    public RandomBlockZuMob() {
        shuffle();
    }

    public boolean isExplosion() {
        return explosion;
    }

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
        List<Material> mats = Utils.getMaterialList(Utils.MaterialType.ALL);
        List<EntityType> entityTypes = Utils.getEntityTypes(Utils.EntityTypeCats.LIVING);
        Collections.shuffle(mats);
        for (Material mat : mats) {
            matEntity.put(mat, entityTypes.get(random.nextInt(entityTypes.size())));
        }
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!running) return;
        Collection<ItemStack> drops = event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand());
        if (!diff.equals(Challenge.ChallengeDiff.EASY)) event.setDropItems(false);
        destroy(event.getBlock(), drops);
    }

    private void destroy(Block block, Collection<ItemStack> drops) {
        Location loc = block.getLocation();
        EntityType type;
        if (diff.equals(Challenge.ChallengeDiff.HARD))
            type = Utils.getRandomEntityType(Utils.EntityTypeCats.LIVING);
        else type = matEntity.get(block.getType());
        Entity entity = loc.getWorld().spawnEntity(loc, type);
        this.entityLoot.put(entity, drops);
    }


    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!running) return;
        if (!explosion) return;
        for (Block block : event.blockList()) {
            destroy(block, block.getDrops());
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        if (!running) return;
        if (!explosion) return;
        for (Block block : event.blockList()) {
            destroy(block, block.getDrops());
        }
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!running) return;
        Entity entity = event.getEntity();
        if (diff.equals(Challenge.ChallengeDiff.EASY)) {
            this.entityLoot.remove(entity);
            return;
        }
        if (this.entityLoot.containsKey(entity)) {
            Collection<ItemStack> drops = this.entityLoot.get(entity);
            for (ItemStack drop : drops) {
                entity.getLocation().getWorld().dropItem(entity.getLocation(), drop);
            }
        }
    }


    public void toggleExplosion() {
        explosion = !explosion;
    }


}
