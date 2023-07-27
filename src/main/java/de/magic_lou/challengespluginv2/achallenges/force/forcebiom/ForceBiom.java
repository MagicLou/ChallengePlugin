package de.magic_lou.challengespluginv2.achallenges.force.forcebiom;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.timer.Timer;
import de.magic_lou.challengespluginv2.utils.Utils;
import de.magic_lou.challengespluginv2.utils.UtilsForce;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ForceBiom {

    private static final Random random = new Random();
    private static final String WARTEN = "Warten auf neue Anweisung";
    private final List<Player> lost = new LinkedList<>();
    private final PlayerManager playerManager;
    private final Timer timer;
    private final UtilsForce utilsForce;
    private final BossBar bossBar = Bukkit.createBossBar(WARTEN, BarColor.GREEN, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private Biome currentBiom = Biome.CUSTOM;

    public ForceBiom(PlayerManager playerManager, Timer timer, UtilsForce utilsForce) {
        this.playerManager = playerManager;
        this.timer = timer;
        this.utilsForce = utilsForce;
    }

    public Biome getCurrentBiom() {
        return currentBiom;
    }

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
    }

    public void start() {
        utilsForce.start(lost,bossBar,this::generateNew,600,300);
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

    private Utils.BiomType getBiomType() {
        Player player = playerManager.getPlayers().get(0);
        Utils.BiomType type = Utils.BiomType.ALL;

        if (!diff.equals(Challenge.ChallengeDiff.HARD)) {
            if (player.getWorld().isPiglinSafe()) type = Utils.BiomType.NETHER;
            else if (player.getWorld().isBedWorks()) type = Utils.BiomType.OVERWORLD;
            else type = Utils.BiomType.END;
        }


        return type;

    }


    public void generateNew() {

        currentBiom = Utils.getRandomBiome(getBiomType());


        if (diff.equals(Challenge.ChallengeDiff.EASY)) {
            List<Biome> biomes = new LinkedList<>();
            Player player = playerManager.getPlayers().get(0);
            Location loc = player.getLocation();
            loc.add(-1000, 0, -1000);
            for (int x = 0; x < 2000 / 50; x++) {
                for (int z = 0; z < 2000 / 50; z++) {
                    loc.add(0, 0, 50);
                    biomes.add(loc.getWorld().getBiome(loc));
                }
                loc.add(50, 0, -2000);

            }
            currentBiom = biomes.get(random.nextInt(biomes.size()));
        }

        int sec = random.nextInt(300) + 300;
        bossBar.setTitle("Befinde dich in " + sec + "sec in " + Utils.formateName(currentBiom.name(),true));
        timer.addBossBar(bossBar, sec);
        for (Player player : playerManager.getPlayers()) {
            player.playSound(Sound.sound(org.bukkit.Sound.BLOCK_BELL_USE, Sound.Source.MASTER, 1, 1));
        }

        timer.addExecuteIn(sec, this::check);
    }


    public void check() {
        boolean check = !diff.equals(Challenge.ChallengeDiff.EASY);
        for (Player player : playerManager.getPlayers()) {
            if (!player.getLocation().getWorld().getBiome(player.getLocation()).equals(currentBiom)) {
                check = false;
                lost.add(player);
            }
        }
        utilsForce.check(300,300,check,this::generateNew,bossBar,lost,"befand","befanden","sich nicht in",currentBiom.name(),"");
    }



}
