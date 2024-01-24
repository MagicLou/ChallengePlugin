package de.magic_lou.challengespluginv2.achallenges.random.randomizer;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;

public class Randomizer implements Listener {

    public void setRunning(boolean running) {
        this.running = running;
    }

    private boolean running = false;
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private EnumMap<Material, Material> newLoot = new EnumMap<>(Material.class);
    private boolean drops = false;
    private boolean tools = false;

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
    }

    public String toggle() {
        drops = !drops;
        if (drops) return "Drops";
        else return "BlockType";

    }


    public void shuffle() {
        newLoot = Utils.getMaterialMap(Utils.MaterialType.ALL, Utils.MaterialType.ITEM);
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!running) return;
        destroy(event.getBlock(), event.getPlayer().getActiveItem());
        if (!diff.equals(Challenge.ChallengeDiff.EASY)) event.setDropItems(false);
    }

    private void destroy(Block block, ItemStack itemStack) {
        if (diff.equals(Challenge.ChallengeDiff.HARD))
            block.getLocation().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Utils.getRandomMaterial(Utils.MaterialType.ITEM)));
        else {
            if (!drops)
                block.getLocation().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(newLoot.get(block.getType())));
            else {
                if(tools){
                    for (ItemStack drop : block.getDrops()) {
                        block.getLocation().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(newLoot.get(drop.getType())));
                    }
                }else {
                    for (ItemStack drop : block.getDrops(itemStack)) {
                        block.getLocation().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(newLoot.get(drop.getType())));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockDropItem(BlockDropItemEvent event) {
        if (running) event.setCancelled(!diff.equals(Challenge.ChallengeDiff.EASY));
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!running) return;
        for (Block block : event.blockList()) {
            destroy(block, new ItemStack(Material.AIR));
        }
        if (!diff.equals(Challenge.ChallengeDiff.EASY)) event.blockList().clear();
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        if (!running) return;
        for (Block block : event.blockList()) {
            destroy(block, new ItemStack(Material.AIR));
        }
    }

    public String toggleDrops() {
        tools = !tools;
        return tools ? "die von Tools " : "die von Hand";
    }
}
