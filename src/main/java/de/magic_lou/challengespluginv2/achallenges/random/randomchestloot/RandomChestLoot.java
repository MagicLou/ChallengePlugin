package de.magic_lou.challengespluginv2.achallenges.random.randomchestloot;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;
import java.util.Random;

public class RandomChestLoot implements Listener {

    private static final Random random = new Random();
    private boolean running = false;
    private boolean pickup = false;
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private EnumMap<Material, Material> mats = Utils.getMaterialMap(Utils.MaterialType.ITEM, Utils.MaterialType.ITEM);

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
        mats = Utils.getMaterialMap(Utils.MaterialType.ITEM, Utils.MaterialType.ITEM);
    }

    @EventHandler
    public void onPlayerOpenChest(LootGenerateEvent event) {
        if (!running) return;
        for (ItemStack itemStack : event.getLoot()) {
            if (diff.equals(Challenge.ChallengeDiff.HARD)) itemStack.setType(mats.get(itemStack.getType()));
            else itemStack.setType(Utils.getRandomMaterial(Utils.MaterialType.ITEM));

            if (diff.equals(Challenge.ChallengeDiff.EASY))
                itemStack.setAmount(random.nextInt(itemStack.getAmount() + 25));
        }
    }

    @EventHandler
    public void onPlayerPickUp(PlayerAttemptPickupItemEvent event) {
        if (running && pickup) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerVillager(PlayerInteractEntityEvent event) {
        if (!running) return;
        if (event.getRightClicked().getType().equals(EntityType.VILLAGER) || event.getRightClicked().getType().equals(EntityType.WANDERING_TRADER))
            event.setCancelled(true);
    }

    public void pickUp(Player player) {
        pickup = !pickup;
        if(pickup){
            player.sendMessage("Es können nun keine anderen Items außer aus Truhen verwendet werden");
        }else{
            player.sendMessage("Es können nun auch anderen Items außer nur aus Truhen verwendet werden");
        }
    }
}
