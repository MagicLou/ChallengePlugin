package de.magic_lou.challengespluginv2.achallenges.random.randomgravity;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.gravitys.GravityManager;
import de.magic_lou.challengespluginv2.timer.Timer;

import java.util.Random;

public class RandomGravity {

    private final GravityManager gravityManager;
    private final Timer timer;
    private final Random random = new Random();
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private int maxDiff = 300;

    public RandomGravity(GravityManager gravityManager, Timer timer) {
        this.gravityManager = gravityManager;
        this.timer = timer;
    }

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
        switch (diff) {
            case EASY -> maxDiff = 600;
            case NORMAL -> maxDiff = 300;
            case HARD -> maxDiff = 60;

        }
    }

    public void selectRandom() {
        gravityManager.selectRandom();
    }

    public void start() {
        selectRandom();
        timer.addExecuteIn(random.nextInt(maxDiff), this::start);
    }


}
