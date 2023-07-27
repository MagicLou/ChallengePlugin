package de.magic_lou.challengespluginv2.commands.challengecomands;

import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PickUpItemCommand implements CommandExecutor {

    private final ChallengeManager challengeManager;


    public PickUpItemCommand(ChallengeManager challengeManager) {
        this.challengeManager = challengeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return CommandUtils.exec(args, challengeManager, "pickup", (Player) sender);
    }

    public void description(Player player) {
        player.sendMessage("---------------------");
        player.sendMessage(ChatColor.DARK_PURPLE + "Use Pickup-Command like this:");
        player.sendMessage(ChatColor.WHITE + "/pickup");
        player.sendMessage("");
        player.sendMessage(ChatColor.WHITE + "Allows the Players of " + ChatColor.DARK_PURPLE + "RandomChestLoot-Challenge" + ChatColor.WHITE + " to  " + ChatColor.GREEN + "pick up other Items ");
    }

}
