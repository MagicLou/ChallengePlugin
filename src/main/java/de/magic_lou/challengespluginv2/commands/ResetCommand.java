package de.magic_lou.challengespluginv2.commands;

import de.magic_lou.challengespluginv2.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ResetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        reset();
        return false;
    }

    public void reset() {
        //Exchange Text: ChatColor.GRAY+"The current"+ChatColor.GREEN+" World"+ChatColor.GRAY+" is going to"+ChatColor.RED+" reset"+ChatColor.GRAY+". Please"+ChatColor.DARK_GRAY+" wait"+ChatColor.GRAY+" before"+ChatColor.GREEN+" reconnecting "+ChatColor.GRAY+"the"+ChatColor.RED+" Reset"+ChatColor.GRAY+" may take a While"
        for (Player player : Bukkit.getOnlinePlayers()) player.kick(Component.text("World Reset"));
        Main.instance.getConfig().set("isReset", true);
        Main.instance.saveConfig();
        Bukkit.spigot().restart();
    }

}
