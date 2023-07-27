package de.magic_lou.challengespluginv2.commands.challengecomands;

import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class OnExplodeCommand implements CommandExecutor {


    private static final String EXPLOSION = "explosion";
    private final ChallengeManager challengeManager;


    public OnExplodeCommand(ChallengeManager challengeManager) {
        this.challengeManager = challengeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return CommandUtils.exec(args, challengeManager, EXPLOSION, (Player) sender);
    }

    public void description(Player player) {
        player.sendMessage("---------------------");
        player.sendMessage(ChatColor.DARK_PURPLE + "Use OnExplode-Command like this:");
        player.sendMessage(ChatColor.WHITE + "/explosion [challenge]");
        player.sendMessage("");
        player.sendMessage(ChatColor.WHITE + "Searches for a Challenge triggert by " + ChatColor.GREEN + "Block-Breaking " + ChatColor.WHITE + " to set weather an explosion triggers it or not");
        player.sendMessage(ChatColor.WHITE + "If a specific " + ChatColor.GREEN + "[challenge] " + ChatColor.WHITE + "is set, this Challenge is being used");
    }

}
