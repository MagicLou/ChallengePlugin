package de.magic_lou.challengespluginv2.commands.settings;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvSeeCommand implements TabExecutor {

    private boolean allowed = false;

    public boolean isAllowed() {
        return allowed;
    }

    public void toggleOn() {
        allowed = !allowed;
        if (allowed) Bukkit.broadcast(Component.text("InvSee ist jetzt" + ChatColor.GREEN + " an"));
        else Bukkit.broadcast(Component.text("InvSee ist jetzt" + ChatColor.RED + " aus"));
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!allowed) {
            sender.sendMessage("Der Command ist disabled");
            return false;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Dieser Command kann nur von einem Spieler ausgeführt werden.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("Verwendung: /invsee <Spieler>");
            return true;
        }

        Player target = player.getServer().getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("Der angegebene Spieler ist nicht online.");
            return true;
        }

        player.openInventory(target.getInventory());
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }

        if (args.length == 1) {
            List<String> playerNames = new ArrayList<>();
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                playerNames.add(p.getName());
            }
            return playerNames;
        }
        return Collections.emptyList();
    }

}
