package de.magic_lou.challengespluginv2.achallenges.other.chunkwalking;

import de.magic_lou.challengespluginv2.Challenge;

public class ChunkWalking {
    private boolean running;
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;

    public void setRunning(boolean b) {
        running = b;
    }

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
    }





}
