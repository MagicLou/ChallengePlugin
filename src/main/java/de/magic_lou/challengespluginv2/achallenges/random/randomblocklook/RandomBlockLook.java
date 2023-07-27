package de.magic_lou.challengespluginv2.achallenges.random.randomblocklook;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.Main;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;


public class RandomBlockLook {


    private final PlayerManager playerManager;

    private final Map<Player, Material> block = new HashMap<>();
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private BukkitTask taskID = null;

    public RandomBlockLook(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
    }

    public void start() {
        taskID = Bukkit.getScheduler().runTaskTimer(Main.instance, this::blockChange, 1, 1);
    }

    public void stop() {
        taskID.cancel();
    }

    public void blockChange() {
        for (Player player : playerManager.getPlayers()) {
            switch (diff) {
                case EASY:
                    if (block.get(player) != player.getTargetBlock(null, 100).getType()) {
                        player.getTargetBlock(null, 100).setType(Utils.getRandomMaterial(Utils.MaterialType.OCCLUDING));
                        block.put(player, player.getTargetBlock(null, 100).getType());
                    }
                    break;
                case NORMAL:
                    if (player.isSneaking()) break;
                    //else do same as default
                default:
                    player.getTargetBlock(null, 100).setType(Utils.getRandomMaterial(Utils.MaterialType.OCCLUDING));
            }
        }
    }


}
