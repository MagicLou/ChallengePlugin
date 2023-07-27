package de.magic_lou.challengespluginv2.commands.challengecomands;

import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceCommand implements CommandExecutor {

    private final ChallengeManager challengeManager;

    public PlaceCommand(ChallengeManager challengeManager) {
        this.challengeManager = challengeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return CommandUtils.exec(args, challengeManager, "place", (Player) sender);
    }

    public void description(Player player) {
        player.sendMessage("---------------------");
        player.sendMessage(ChatColor.DARK_PURPLE + "Use Place-Command like this:");
        player.sendMessage(ChatColor.WHITE + "/place");
        player.sendMessage("");
        player.sendMessage(ChatColor.WHITE + "Toggles weather placing blocks will"+ ChatColor.GREEN +" clear Chunk in" + ChatColor.DARK_PURPLE + " ChunkBreaking-Challenge"+ ChatColor.WHITE + " or not");
    }
}
