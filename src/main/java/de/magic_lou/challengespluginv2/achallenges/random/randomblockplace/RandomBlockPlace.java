package de.magic_lou.challengespluginv2.achallenges.random.randomblockplace;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Map;

public class RandomBlockPlace implements Listener {

    private boolean running = false;

    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;

    private Map<Material, Material> materials;


    public RandomBlockPlace() {
        shuffle();
    }

    public void shuffle() {
        materials = Utils.getMaterialMap(Utils.MaterialType.BLOCK, Utils.MaterialType.BLOCK);
    }

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (running) {
            if (diff.equals(Challenge.ChallengeDiff.NORMAL))
                event.getBlock().setType(materials.get(event.getBlock().getType()));
            else event.getBlock().setType(Utils.getRandomMaterial(Utils.MaterialType.BLOCK));
        }
    }


}
