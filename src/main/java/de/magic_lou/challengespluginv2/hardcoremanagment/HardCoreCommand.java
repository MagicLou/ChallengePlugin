package de.magic_lou.challengespluginv2.hardcoremanagment;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HardCoreCommand implements TabExecutor {

    private final HardCoreManager hardCoreManager;

    public HardCoreCommand(HardCoreManager hardCoreManager) {
        this.hardCoreManager = hardCoreManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length != 1) {
            description((Player) sender);
            sender.sendMessage("Das Spiel ist im Moment " + hardCoreManager.getGameDiff());
        }
        hardCoreManager.setGameDiff(HardCoreManager.HardCoreType.valueOf(args[0]));
        sender.sendMessage("Das Spiel ist nun " + ChatColor.GREEN + args[0]);
        return false;
    }


    public void description(Player player) {
        player.sendMessage(ChatColor.GOLD + "NORMAL: " + ChatColor.WHITE + "just normal playing");
        player.sendMessage(ChatColor.GOLD + "HARDCORE: " + ChatColor.WHITE + "resetting world after dying");
        player.sendMessage(ChatColor.GOLD + "ULTRA_HARDCORE: " + ChatColor.WHITE + "only regeneration from potions and golden apples");
        player.sendMessage(ChatColor.GOLD + "ULTRA_ULTRA_HARDCORE: " + ChatColor.WHITE + "once lost a heart, it will never come back");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> ret = new ArrayList<>();

        for (HardCoreManager.HardCoreType value : HardCoreManager.HardCoreType.values()) {
            ret.add(value.name());
        }

        return ret;
    }
}
