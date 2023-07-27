package de.magic_lou.challengespluginv2.challengemanagment.GUI;

import de.magic_lou.challengespluginv2.challengemanagment.GUI.managment.GUIManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GUICommand implements CommandExecutor {

    private final GUIManager guiManager;

    public GUICommand(GUIManager guiManager) {
        this.guiManager = guiManager;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        guiManager.openInv((Player) commandSender, true);
        return false;
    }
}
