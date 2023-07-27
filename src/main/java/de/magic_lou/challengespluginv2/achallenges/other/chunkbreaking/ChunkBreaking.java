package de.magic_lou.challengespluginv2.achallenges.other.chunkbreaking;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Collections;

public class ChunkBreaking {


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
        Collection<ItemStack> dropping = block.getDrops(tool, player);

        int max = diff.equals(Challenge.ChallengeDiff.NORMAL) ? 576 : 1;

        int drops = 0;
        if (blocktype != Material.BEDROCK) {
            for (int y = event.getBlock().getWorld().getMinHeight(); y < event.getBlock().getWorld().getMaxHeight(); y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {

                        if (diff.equals(Challenge.ChallengeDiff.EASY)) {
                            Block blockE = chunk.getBlock(x,y,z);
                            block.getWorld().dropItemNaturally(block.getLocation(), (ItemStack) blockE.getDrops(tool,player));
                            blockE.setType(Material.AIR);
                        } else if (blocktype == chunk.getBlock(x, y, z).getType()) {
                            chunk.getBlock(x, y, z).setType(Material.AIR);
                            if (drops < max) {
                                for (ItemStack itemStack : dropping) {
                                    block.getWorld().dropItemNaturally(block.getLocation(), itemStack);
                                }
                                drops++;
                            }
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
        if(!place) return;
        Chunk chunk = event.getBlockAgainst().getChunk();
        Block block = event.getBlock();
        Material blocktype = event.getBlockAgainst().getType();

        if (blocktype != Material.BEDROCK) {
            for (int y = event.getBlock().getWorld().getMinHeight(); y < event.getBlock().getWorld().getMaxHeight(); y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        if (blocktype == chunk.getBlock(x, y, z).getType()) {
                            chunk.getBlock(x, y, z).setType(Material.AIR);
                        }
                    }
                }
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
