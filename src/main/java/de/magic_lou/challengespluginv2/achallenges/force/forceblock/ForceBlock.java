package de.magic_lou.challengespluginv2.achallenges.force.forceblock;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.timer.Timer;
import de.magic_lou.challengespluginv2.utils.Utils;
import de.magic_lou.challengespluginv2.utils.UtilsForce;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ForceBlock {

    private static final Random random = new Random();
    private static final String WARTEN = "Warten auf neue Anweisung";
    private final List<Player> lost = new LinkedList<>();
    private final PlayerManager playerManager;
    private final Timer timer;
    private final UtilsForce utilsForce;
    private final List<Material> blocks = Utils.getMaterialList(Utils.MaterialType.OCCLUDING);
    private final BossBar bossBar = Bukkit.createBossBar(WARTEN, BarColor.GREEN, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private Material currentBlock = Material.AIR;
    public ForceBlock(PlayerManager playerManager, Timer timer, UtilsForce utilsForce) {
        this.playerManager = playerManager;
        this.timer = timer;
        this.utilsForce = utilsForce;
        blocks.remove(Material.COMMAND_BLOCK);
        blocks.remove(Material.REPEATING_COMMAND_BLOCK);
        blocks.remove(Material.CHAIN_COMMAND_BLOCK);
        blocks.remove(Material.JIGSAW);
        blocks.remove(Material.STRUCTURE_BLOCK);
        //Add more here
    }
    public Material getCurrentBlock() {
        return currentBlock;
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

        if (diff.equals(Challenge.ChallengeDiff.HARD))
            currentBlock = Utils.getRandomMaterial(Utils.MaterialType.OCCLUDING);
        else currentBlock = blocks.get(random.nextInt(blocks.size() - 1));
        int sec = random.nextInt(180) + 120;
        bossBar.setTitle("Stelle dich in " + sec + "sec auf " + Utils.formateName(currentBlock.name(),true));
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
                if (player.getLocation().add(0, -1, 0).getBlock().getType().equals(currentBlock)) check = true;
            } else if (!player.getLocation().add(0, -1, 0).getBlock().getType().equals(currentBlock)) {
                check = false;
                lost.add(player);
            }
        }
        utilsForce.check(300,300,check,this::generateNew,bossBar,lost,"stand","standen","nicht auf",currentBlock.name(),"");
    }




}
