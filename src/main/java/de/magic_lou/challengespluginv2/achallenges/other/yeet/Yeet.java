package de.magic_lou.challengespluginv2.achallenges.other.yeet;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.timer.Timer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public class Yeet {

    private static final Random random = new Random();
    private final Timer timer;
    private final PlayerManager playerManager;
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private double x = 1;

    public Yeet(Timer timer, PlayerManager playerManager) {
        this.timer = timer;
        this.playerManager = playerManager;
    }

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
        switch (diff) {
            case HARD -> x = 2;
            case NORMAL -> x = 1;
            case EASY -> x = 0.5;
        }
    }

    public void start() {
        timer.addExecuteIn(random.nextInt(15) + 5, this::yeet);
    }

    public void stop() {
        timer.removeExecution(this::yeet);
    }

    public void yeet() {
        for (Player player : playerManager.getPlayers()) {
            player.setVelocity(Vector.getRandom().multiply(random.nextFloat() * x));
        }
        timer.addExecuteIn(random.nextInt(10) + 2, this::yeet);
    }


}
