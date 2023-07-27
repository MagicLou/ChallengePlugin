package de.magic_lou.challengespluginv2.achallenges.other.chorousfruit;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.Random;

public class ChorusFruit implements Listener {

    private static final Random random = new Random();
    private final PlayerManager playerManager;
    private boolean running = false;
    private int r = 50;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
        switch (diff){
            case EASY -> r = 25;
            case NORMAL -> r = 50;
            case HARD -> r = 100;
        }
    }

    public ChorusFruit(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }



    @EventHandler
    public void onEat(PlayerItemConsumeEvent event){
        if(!running) return;
        Player player = event.getPlayer();
        if(!playerManager.getPlayers().contains(player)) return;

        Location loc = player.getLocation();
        int d = 2*r;
        int x = random.nextInt(d)-r;
        int z = random.nextInt(d)-r;
        int y = random.nextInt(d/5)-(r/5);

        loc.add(x,y,z);
        Utils.setOnGround(loc);
        player.teleport(loc);



    }


}
