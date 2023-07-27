package de.magic_lou.challengespluginv2.commands.challengecomands;

import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TryCommand implements CommandExecutor {

    private static final String TRY = "try";
    private final ChallengeManager challengeManager;

    public TryCommand(ChallengeManager challengeManager) {
        this.challengeManager = challengeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return CommandUtils.exec(args, challengeManager, TRY, (Player) sender);
    }


    public void description(Player player) {
        player.sendMessage("---------------------");
        player.sendMessage(ChatColor.DARK_PURPLE + "Use Try-Command like this:");
        player.sendMessage(ChatColor.WHITE + "/try [challenge]");
        player.sendMessage("");
        player.sendMessage(ChatColor.WHITE + "Searches for a " + ChatColor.GREEN + "'ForceChallenge' " + ChatColor.WHITE + "to check the Request");
        player.sendMessage(ChatColor.WHITE + "If a specific " + ChatColor.GREEN + "[challenge] " + ChatColor.WHITE + "is set, this Challenges Request is checked");
    }

}
