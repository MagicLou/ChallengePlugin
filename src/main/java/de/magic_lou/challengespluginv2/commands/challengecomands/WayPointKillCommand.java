package de.magic_lou.challengespluginv2.commands.challengecomands;

import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WayPointKillCommand implements CommandExecutor {

    private static final String WK = "wayPointKill";
    private final ChallengeManager challengeManager;

    public WayPointKillCommand(ChallengeManager challengeManager) {
        this.challengeManager = challengeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return CommandUtils.exec(args, challengeManager, WK, (Player) sender);
    }


    public void description(Player player) {
        player.sendMessage("---------------------");
        player.sendMessage(ChatColor.DARK_PURPLE + "Use WayPointKill-Command like this:");
        player.sendMessage(ChatColor.WHITE + "/waypointkill");
        player.sendMessage("");
        player.sendMessage(ChatColor.WHITE + "Kills the " + ChatColor.GREEN + "WayPoint " + ChatColor.WHITE + "of " + ChatColor.GREEN + "BackPortChallenge " + ChatColor.WHITE + "if existing");
    }

}
