package de.magic_lou.challengespluginv2.playermanagment;

import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import de.magic_lou.challengespluginv2.timer.Timer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpectateCommand implements CommandExecutor {


    private final Timer timer;
    private final ChallengeManager challengeManager;
    private final PlayerManager playerManager;

    public SpectateCommand(Timer timer, ChallengeManager challengeManager, PlayerManager playerManager) {
        this.timer = timer;
        this.challengeManager = challengeManager;
        this.playerManager = playerManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            playerManager.toggleSpectate(player);
            if (challengeManager.isRunning()) {
                timer.pauseTimer();
                timer.resumeTimer();
            }
        }
        return false;
    }

}
