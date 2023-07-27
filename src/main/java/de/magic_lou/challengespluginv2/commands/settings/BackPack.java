package de.magic_lou.challengespluginv2.commands.settings;

import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.HashMap;

public class BackPack implements Listener {

    private final HashMap<Player, Inventory> invs = new HashMap<>();
    private final Inventory inv;
    private final PlayerManager playerManager;

    private boolean running = false;
    private boolean together = false;
    private final ItemStack item = new ItemStack(Material.ENDER_CHEST);

    public BackPack(PlayerManager playerManager) {
        this.playerManager = playerManager;
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.GOLD + "BackPag"));
        meta.lore(Collections.singletonList(Component.text("If you put this Item in your BackPack I can't help you")));
        item.setItemMeta(meta);
        inv = Bukkit.createInventory(null, 54);
    }

    public boolean isTogether() {
        return together;
    }

    public boolean isRunning() {
        return running;
    }

    public void toggleRunning() {
        if (running && together) {
            running = false;
            together = false;
            for (Player player : playerManager.getPlayers()) {
                player.sendMessage("BackPacks sind jetzt" + ChatColor.RED + " aus");
                player.getInventory().remove(Material.ENDER_CHEST);
                player.updateInventory();
            }
        } else if (running) {
            together = true;
            for (Player player : playerManager.getPlayers()) {
                player.sendMessage("Es gibt jetzt " + ChatColor.GOLD + "ein BackPacks für alle");
                player.getInventory().remove(Material.ENDER_CHEST);
                player.getInventory().addItem(item);
                player.updateInventory();
            }
        } else {
            running = true;
            together = false;
            for (Player player : playerManager.getPlayers()) {
                player.sendMessage("BackPacks sind jetzt" + ChatColor.GREEN + " an");
                player.getInventory().remove(Material.ENDER_CHEST);
                player.getInventory().addItem(item);
                player.updateInventory();
            }
        }
    }

    public void openBackPack(Player player) {
        if (!invs.containsKey(player)) invs.put(player, Bukkit.createInventory(null, 54));
        player.openInventory(invs.get(player));
    }


    @EventHandler
    public void onItemClick(PlayerInteractEvent event) {
        if (!running) return;
        if (event.getPlayer().getActiveItem().equals(item)) {
            if (together) event.getPlayer().openInventory(inv);
            else openBackPack(event.getPlayer());
            event.setCancelled(true);
        }
    }


}
