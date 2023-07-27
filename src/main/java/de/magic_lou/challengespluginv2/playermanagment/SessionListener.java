package de.magic_lou.challengespluginv2.playermanagment;

import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import de.magic_lou.challengespluginv2.timer.PauseStopListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SessionListener implements Listener {


    private final PlayerManager playerManager;
    private final ChallengeManager challengeManager;

    private final PauseStopListener pauseStopListener;

    public SessionListener(PlayerManager playerManager, ChallengeManager challengeManager, PauseStopListener pauseStopListener) {
        this.playerManager = playerManager;
        this.challengeManager = challengeManager;
        this.pauseStopListener = pauseStopListener;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!challengeManager.isRunning()) {
            playerManager.addPlayers(player);
            player.sendMessage(ChatColor.GRAY + "If you just want to spectate the Challenge, use " + ChatColor.GOLD + "/spectate");
            pauseStopListener.setLoc(player, player.getLocation());
        } else {
            playerManager.addSpectators(player);
            player.sendMessage(ChatColor.GRAY + "The Challenge is running. You are spectating the Challenge.");
        }
    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playerManager.remove(player);
    }

}
