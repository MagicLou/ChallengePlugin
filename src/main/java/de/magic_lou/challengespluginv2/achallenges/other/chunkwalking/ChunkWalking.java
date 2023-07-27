package de.magic_lou.challengespluginv2.achallenges.other.chunkwalking;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.utils.UtilsChunk;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Collections;

public class ChunkWalking implements Listener {

    private final PlayerManager playerManager;
    private boolean running;
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;

    public ChunkWalking(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public void setRunning(boolean b) {
        running = b;
    }

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (!running) return;
        if (!playerManager.containsAny(Collections.singletonList(event.getPlayer()))) return;
        Location loc = event.getRespawnLocation();
        loc.add(0, -1, 0);
        loc.getBlock().setType(Material.GLASS);

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!running) return;
        if (!playerManager.containsAny(Collections.singletonList(event.getPlayer()))) return;
        Player player = event.getPlayer();

        Location loc = player.getLocation().getBlock().getLocation();
        int xloc = loc.getBlockX();
        int yloc = loc.getBlockY();
        int zloc = loc.getBlockZ();
        loc.setX(xloc);
        loc.setY(yloc - 1);
        loc.setZ(zloc);
        Chunk chunk = loc.getChunk();
        Block block = loc.getBlock();
        Material blocktype = block.getType();
        int setY = diff.equals(Challenge.ChallengeDiff.EASY) ? player.getLocation().getBlockY() : event.getPlayer().getLocation().getWorld().getMinHeight();

        if (blocktype != Material.BEDROCK) {
            if (blocktype != Material.AIR) {
                for (int y = setY; y < event.getPlayer().getLocation().getWorld().getMaxHeight(); y++) {
                    if(diff.equals(Challenge.ChallengeDiff.HARD)){
                        UtilsChunk.clear(chunk,null,y);
                    }else {
                        UtilsChunk.clear(chunk, blocktype, y);
                    }
                }
                block.setType(Material.GLASS);
            }
        }

    }


}





