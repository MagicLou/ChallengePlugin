package de.magic_lou.challengespluginv2.achallenges.force.forceheight;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.timer.Timer;
import de.magic_lou.challengespluginv2.utils.Utils;
import de.magic_lou.challengespluginv2.utils.UtilsForce;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ForceHeight {


    private static final Random random = new Random();
    private static final String WARTEN = "Warten auf neue Anweisung";
    private final PlayerManager playerManager;
    private final Timer timer;
    private final UtilsForce utilsForce;
    private final List<Player> lost = new LinkedList<>();
    private final BossBar bossBar = Bukkit.createBossBar(WARTEN, BarColor.GREEN, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private int currentHeight = 0;

    public ForceHeight(PlayerManager playerManager, Timer timer, UtilsForce utilsForce) {
        this.playerManager = playerManager;
        this.timer = timer;
        this.utilsForce = utilsForce;
    }

    public int getCurrentHeight() {
        return currentHeight;
    }

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
    }

    public void start() {
        utilsForce.start(lost,bossBar,this::generateNew,300,300);
    }

    public void stop() {
        utilsForce.stop(this::check,this::generateNew,bossBar);
    }

    public void skipp() {
        utilsForce.skipp(this::check,bossBar,this::generateNew);
    }

    public void forceCheck() {
        utilsForce.forceCheck(this::check,bossBar);
    }


    public void generateNew() {

        int maxHeight = playerManager.getPlayers().get(0).getWorld().getMaxHeight();
        int minHeight = Math.abs(playerManager.getPlayers().get(0).getWorld().getMinHeight());
        if (playerManager.getPlayers().get(0).getWorld().isPiglinSafe()) maxHeight = 126;

        if (diff.equals(Challenge.ChallengeDiff.HARD)) currentHeight = random.nextInt(320 + 64) - 64;
        else currentHeight = random.nextInt(maxHeight + minHeight) - minHeight;

        int sec = random.nextInt(180) + 120;
        bossBar.setTitle("Befinde dich in " + sec + "sec auf Y=" + Utils.formateName(String.valueOf(currentHeight),true));
        timer.addBossBar(bossBar, sec);
        for (Player player : playerManager.getPlayers()) {
            player.playSound(Sound.sound(org.bukkit.Sound.BLOCK_BELL_USE, Sound.Source.MASTER, 1, 1));
        }

        timer.addExecuteIn(sec, this::check);
    }





    public void check() {
        boolean check = !diff.equals(Challenge.ChallengeDiff.EASY);
        for (Player player : playerManager.getPlayers()) {
            if (diff.equals(Challenge.ChallengeDiff.EASY)) {
                if (player.getLocation().getBlock().getY() == currentHeight) check = true;
            } else if (player.getLocation().getBlock().getY() != currentHeight) {
                check = false;
                lost.add(player);
            }
        }
        utilsForce.check(120,180,check,this::generateNew,bossBar,lost,"befand","befanden","sich nicht auf Y:",String.valueOf(currentHeight),"");
    }



}
