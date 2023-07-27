package de.magic_lou.challengespluginv2.utils;

import org.bukkit.Chunk;
import org.bukkit.Material;

public class UtilsChunk {

    public static void clear(Chunk chunk, Material blocktype, int y) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if(blocktype != null) {
                    if (blocktype == chunk.getBlock(x, y, z).getType()) {
                        chunk.getBlock(x, y, z).setType(Material.AIR);
                    }
                }else{
                    chunk.getBlock(x, y, z).setType(Material.AIR);
                }
            }
        }
    }


}
