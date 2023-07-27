package de.magic_lou.challengespluginv2.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.LinkedList;
import java.util.List;

import static org.bukkit.Bukkit.broadcast;

public class UtilsChunk {

    public static void clear(Chunk chunk, Material blocktype, int y) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Block block = chunk.getBlock(x, y, z);
                if (blocktype != null) {
                    if (blocktype == block.getType()) {
                        block.setType(Material.AIR);
                    }
                } else {
                    if (!block.getType().equals(Material.BEDROCK)) {
                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }


}
