package de.magic_lou.challengespluginv2.position;

import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionManager {
    private final HashMap<String, Location> pos = new HashMap<>();

    public Map<String, Location> getPos() {
        return pos;
    }


    public List<String> listPos() {
        List<String> ret = new ArrayList<>();
        for (Map.Entry<String, Location> entry : pos.entrySet()) {
            ret.add(Utils.formatLocationName(entry.getKey(), ChatColor.GREEN, entry.getValue(), ChatColor.GRAY, ChatColor.GOLD));

        }
        return ret;
    }

    public String getPosition(String name) {
        if (pos.containsKey(name))
            return Utils.formatLocationName(name, ChatColor.GREEN, pos.get(name), ChatColor.GRAY, ChatColor.GOLD);
        else return ChatColor.RED + name + ChatColor.GRAY + " ist keine gespeicherte Position";
    }


    public void addPos(String name, Location location) {
        pos.remove(name);
        pos.put(name, location);
        //For DataBase: PositionDataManager.addNewPosition(new PositionDataManager.SavedPosition(args[1],p.getLocation().getBlockX(),p.getLocation().getBlockY(),p.getLocation().getBlockZ(),p.getLocation().getWorld().getName(), Main.timer.id))

    }

    public void removePos(String name) {
        pos.remove(name);
    }


}
