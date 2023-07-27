package de.magic_lou.challengespluginv2.commands.challengecomands;

import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PrintCommand implements CommandExecutor {

    private final ChallengeManager challengeManager;

    public PrintCommand(ChallengeManager challengeManager) {
        this.challengeManager = challengeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return CommandUtils.exec(args, challengeManager, "print", (Player) sender);
    }

    public void description(Player player) {
        player.sendMessage("---------------------");
        player.sendMessage(ChatColor.DARK_PURPLE + "Use Print-Command like this:");
        player.sendMessage(ChatColor.WHITE + "/print");
        player.sendMessage("");
        player.sendMessage(ChatColor.WHITE + "Tells the Players of" + ChatColor.DARK_PURPLE + " RandomMobLoot-Challenge " + ChatColor.GREEN + "what Mob" + ChatColor.WHITE + " they killed or not");
    }
}
