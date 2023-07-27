package de.magic_lou.challengespluginv2.achallenges.other.running;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.Main;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import static org.bukkit.Bukkit.getScheduler;

public class Running {

    private double speed = 0.1;
    private double airSpeed = 0.1;

    private BukkitTask run = null;
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private final PlayerManager playerManager;

    public Running(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
        switch (diff) {
            case EASY -> {
                speed = 0.05;
                airSpeed = 0.025;
            }
            case NORMAL -> {
                speed = 0.1;
                airSpeed = 0.05;
            }
            case HARD -> {
                speed = 0.5;
                airSpeed = 0.25;
            }
        }
    }


    public void run() {
        run = getScheduler().runTaskTimer(Main.instance, this::gas, 1, 1);
    }

    //not working
    public void gas() {
        for (Player player : playerManager.getPlayers()) {
            Vector facing = player.getLocation().getDirection();
            facing.setY(0);
            double multiplier;
            if (((LivingEntity) player).isOnGround()) {
                multiplier = speed;
            } else {
                multiplier = airSpeed;
            }

            //VectorRechnung
            Location loc = player.getLocation().clone();
            loc.setYaw(player.getLocation().getYaw());
            loc.setPitch(0);
            Vector direction = loc.getDirection();
            player.setVelocity(player.getVelocity().add(direction.multiply(multiplier)));

        }
    }

    public void start() {
        this.run();
    }

    public void stop() {
        if (!run.isCancelled())
            run.cancel();
    }


}




