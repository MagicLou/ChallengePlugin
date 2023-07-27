package de.magic_lou.challengespluginv2.achallenges.random.randominventory;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.timer.Timer;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;
import java.util.Random;

public class RandomInventory {
    private static final Random random = new Random();
    private final Timer timer;
    private final PlayerManager playerManager;
    private EnumMap<Material, Material> newItems = new EnumMap<>(Material.class);
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;

    public RandomInventory(Timer timer, PlayerManager playerManager) {
        this.timer = timer;
        this.playerManager = playerManager;
    }

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
    }

    public void shuffle() {
        newItems = Utils.getMaterialMap(Utils.MaterialType.ITEM, Utils.MaterialType.ITEM);
    }


    public void randomInv() {
        for (Player player : playerManager.getPlayers()) {
            for (ItemStack itemStack : player.getInventory()) {
                if (itemStack == null) break;
                itemStack.setAmount(Math.abs(random.nextInt(80) - 16));
                if (diff.equals(Challenge.ChallengeDiff.HARD))
                    itemStack.setType(Utils.getRandomMaterial(Utils.MaterialType.ITEM));
                else itemStack.setType(newItems.get(itemStack.getType()));
            }
        }
        if (diff.equals(Challenge.ChallengeDiff.EASY)) timer.addExecuteIn(random.nextInt(120) + 300, this::randomInv);
        else timer.addExecuteIn(random.nextInt(120) + 180, this::randomInv);
    }


    public void start() {
        randomInv();
    }

}
