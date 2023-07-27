package de.magic_lou.challengespluginv2.gravitys;

import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;

public abstract class Gravity {

    protected final PlayerManager playerManager;
    protected boolean running = false;
    protected boolean effectEntity;

    protected Gravity(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public void setEffectEntity(boolean effectEntity) {
        this.effectEntity = effectEntity;
    }

    public void start() {
        running = true;
        onStart();
    }

    public void pause() {
        running = false;
        onPause();
    }

    public void resume() {
        start();
    }

    public void stop() {
        pause();
    }


    public abstract void onStart();

    public abstract void onPause();


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
