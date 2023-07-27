package de.magic_lou.challengespluginv2.commands.challengecomands;

import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SkipCommand implements CommandExecutor {

    private static final String SKIP = "skip";
    private final ChallengeManager challengeManager;

    public SkipCommand(ChallengeManager challengeManager) {
        this.challengeManager = challengeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return CommandUtils.exec(args, challengeManager, SKIP, (Player) sender);
    }


    public void description(Player player) {
        player.sendMessage("---------------------");
        player.sendMessage(ChatColor.DARK_PURPLE + "Use Skip-Command like this:");
        player.sendMessage(ChatColor.WHITE + "/skip [challenge]");
        player.sendMessage("");
        player.sendMessage(ChatColor.WHITE + "Searches for a " + ChatColor.GREEN + "'ForceChallenge' " + ChatColor.WHITE + "to skip the Request");
        player.sendMessage(ChatColor.WHITE + "If a specific " + ChatColor.GREEN + "[challenge] " + ChatColor.WHITE + "is set, this Challenges Request is skipped");
    }

}
