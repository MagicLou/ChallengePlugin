package de.magic_lou.challengespluginv2.commands.challengecomands;

import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShuffleCommand implements CommandExecutor {

    private static final String SHUFFLE = "shuffle";
    private final ChallengeManager challengeManager;

    public ShuffleCommand(ChallengeManager challengeManager) {
        this.challengeManager = challengeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return CommandUtils.exec(args, challengeManager, SHUFFLE, (Player) sender);
    }


    public void description(Player player) {
        player.sendMessage("---------------------");
        player.sendMessage(ChatColor.DARK_PURPLE + "Use Shuffle-Command like this:");
        player.sendMessage(ChatColor.WHITE + "/shuffle [challenge]");
        player.sendMessage("");
        player.sendMessage(ChatColor.WHITE + "Searches for a " + ChatColor.GREEN + "'RandomChallenge' " + ChatColor.WHITE + "to shuffle the Random-Set");
        player.sendMessage(ChatColor.WHITE + "If a specific " + ChatColor.GREEN + "[challenge] " + ChatColor.WHITE + "is set, this Challenge is tried to shuffle");
    }


}
