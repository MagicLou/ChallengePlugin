package de.magic_lou.challengespluginv2.achallenges.other.chunkbreaking;

import com.google.gson.JsonArray;
import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.utils.UtilsChunk;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static net.kyori.adventure.text.Component.text;
import static org.bukkit.Bukkit.*;
import static org.bukkit.Bukkit.broadcast;

public class ChunkBreaking implements Listener {


    private final PlayerManager playerManager;
    private boolean running = false;
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private boolean place;

    public ChunkBreaking(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
        place = !diff.equals(Challenge.ChallengeDiff.EASY);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!running) return;
        if (!playerManager.containsAny(Collections.singletonList(event.getPlayer()))) return;
        Chunk chunk = event.getBlock().getChunk();
        Block block = event.getBlock();
        Material blocktype = block.getType();
        Player player = event.getPlayer();
        ItemStack tool = player.getItemInHand();

        int max = diff.equals(Challenge.ChallengeDiff.HARD) ? 1 : 576;
        List<Block> dropping = new LinkedList<>();


        int drops = 0;
        if (blocktype != Material.BEDROCK) {
            for (int y = event.getBlock().getWorld().getMinHeight(); y < event.getBlock().getWorld().getMaxHeight(); y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        if (diff.equals(Challenge.ChallengeDiff.EASY)) {
                            Block blockE = chunk.getBlock(x, y, z);
                            if (drops < max) {
                                Collection<ItemStack> eDrops = blockE.getDrops(tool, player);
                                if (!eDrops.isEmpty()) {
                                    dropping.add(blockE);
                                }
                            }
                            if (!blockE.getType().equals(Material.BEDROCK))
                                blockE.setType(Material.AIR);
                        } else if (blocktype == chunk.getBlock(x, y, z).getType()) {
                            chunk.getBlock(x, y, z).setType(Material.AIR);
                            if (drops < max) {
                                for (ItemStack itemStack : chunk.getBlock(x, y, z).getDrops(tool, player)) {
                                    block.getWorld().dropItemNaturally(block.getLocation(), itemStack);
                                }
                                drops++;
                            }
                        }
                    }
                }
            }
            if(!dropping.isEmpty()){
                List<Block> eDrops = new LinkedList<>();
                for (Block b : dropping) {
                    if(b.getType().equals(block.getType())){
                        eDrops.add(b);
                    }
                }
                for (Block b : eDrops) {
                    if (drops < max) {
                        for (ItemStack drop : b.getDrops(tool, player)) {
                            block.getWorld().dropItemNaturally(block.getLocation(), drop);
                        }
                        drops++;
                    }
                }

                for (Block b : dropping) {
                    if (drops < max) {
                        if (!b.getType().equals(block.getType())) {
                            for (ItemStack drop : b.getDrops(tool, player)) {
                                block.getWorld().dropItemNaturally(block.getLocation(), drop);
                            }
                            drops++;
                        }
                    }
                }

            }
        }
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!running) return;
        if (!playerManager.containsAny(Collections.singletonList(event.getPlayer()))) return;
        if (!place) return;
        Chunk chunk = event.getBlockAgainst().getChunk();
        Block block = event.getBlock();
        Material blocktype = event.getBlockAgainst().getType();

        if (blocktype != Material.BEDROCK) {
            for (int y = event.getBlock().getWorld().getMinHeight(); y < event.getBlock().getWorld().getMaxHeight(); y++) {
                UtilsChunk.clear(chunk, diff.equals(Challenge.ChallengeDiff.HARD) ? null : blocktype, y);
            }
        }
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (!running) return;
        if (!playerManager.containsAny(Collections.singletonList(event.getPlayer()))) return;
        Location loc = event.getRespawnLocation();
        loc.add(0, -1, 0);
        loc.getBlock().setType(Material.GLASS);
    }

    public void place(Player player) {
        place = !place;
        if (place) {
            player.sendMessage("Blöcke platzieren entfernt Blöcke im Chunk");
        } else {
            player.sendMessage("Du kannst ganz normal Blöcke platzieren");
        }
    }

}
