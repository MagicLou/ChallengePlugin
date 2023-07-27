package de.magic_lou.challengespluginv2.utils;

import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.timer.Timer;
import net.kyori.adventure.sound.Sound;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class UtilsForce {
    private final String WARTEN = "Warten auf neue Anweisung";
    private final Random random = new Random();
    private final PlayerManager playerManager;
    private final Timer timer;
    public UtilsForce(PlayerManager playerManager, Timer timer) {

        this.playerManager = playerManager;
        this.timer = timer;
    }
    public String buildEndTitle(List<Player> players, String sg,String pl,String reset,String lost,String after){

        int i = 0;
        StringBuilder titel = new StringBuilder();
        for (Player player : players) {
            titel.append(player.getName()).append(", ");
            i++;
            player.setHealth(0);
        }
        titel.append(" ");
        if (i > 1) titel.append(pl);
        else titel.append(sg);
        titel.append(reset).append(" ").append(Utils.formateName(lost,true)).append(" ").append(after);
        return titel.toString();
    }

    private void bossBarWait(BossBar bossBar) {
        bossBar.setTitle(WARTEN);
        bossBar.setProgress(1);
    }

    public void check(int min,int max,boolean control, Runnable generateNew, BossBar bossBar,List<Player> players,String sg,String pl,String reset,String lost,String after) {
        if (control) {
            for (Player player : playerManager.getPlayers()) {
                player.playSound(Sound.sound(org.bukkit.Sound.BLOCK_AMETHYST_BLOCK_PLACE, Sound.Source.MASTER, 1, 1));
            }
            bossBarWait(bossBar);
            timer.addExecuteIn(random.nextInt(max) + min, generateNew);
        } else {
            bossBar.setTitle(buildEndTitle(players,sg,pl,reset,lost,after));
            for (Player player : playerManager.getPlayers()) {
                player.playSound(Sound.sound(org.bukkit.Sound.ENTITY_ZOMBIE_VILLAGER_CURE, Sound.Source.MASTER, 1, 1));
            }
            timer.pauseTimer();
        }
    }

    public void start(List<Player> lost,BossBar bossBar,Runnable generateNew,int min,int max) {
        lost.clear();
        bossBar.setTitle(WARTEN);
        timer.addExecuteIn(random.nextInt(max) + min, generateNew);
        for (Player player : playerManager.getPlayers()) {
            bossBar.addPlayer(player);
        }
    }

    public void stop(Runnable check,Runnable generateNew,BossBar bossBar) {
        timer.removeExecution(check);
        timer.removeExecution(generateNew);
        timer.removeBossBar(bossBar);
        for (Player player : playerManager.getPlayers()) {
            bossBar.removePlayer(player);
        }
    }

    public void skipp(Runnable check,BossBar bossBar,Runnable generateNew) {
        timer.removeExecution(check);
        timer.removeBossBar(bossBar);
        generateNew.run();
    }

    public void forceCheck(Runnable checking,BossBar bossBar) {
        timer.removeExecution(checking);
        timer.removeBossBar(bossBar);
        checking.run();
    }


}
