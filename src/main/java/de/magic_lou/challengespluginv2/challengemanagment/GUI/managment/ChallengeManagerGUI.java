package de.magic_lou.challengespluginv2.challengemanagment.GUI.managment;

import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import de.magic_lou.challengespluginv2.timer.Timer;

public class ChallengeManagerGUI {

    public final ChallengeManager challengeManager;
    public final Timer timer;

    public ChallengeManagerGUI(ChallengeManager challengeManager, Timer timer) {
        this.challengeManager = challengeManager;
        this.timer = timer;
    }


    public void start() {
        if (timer.getTimerValue() >= 0) timer.resumeTimer();
        else timer.startTimer();
    }

    public void pause() {
        timer.pauseTimer();
    }

    public void stop() {
        timer.stopTimer();
    }


}
