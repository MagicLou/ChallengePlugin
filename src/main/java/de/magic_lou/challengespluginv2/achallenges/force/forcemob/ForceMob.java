package de.magic_lou.challengespluginv2.achallenges.force.forcemob;

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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ForceMob implements Listener {


    private static final Random random = new Random();
    private static final String WARTEN = "Warten auf neue Anweisung";
    private final PlayerManager playerManager;
    private final Timer timer;
    private final UtilsForce utilsForce;
    private final List<Player> players = new LinkedList<>();
    private final List<Player> lost = new LinkedList<>();
    private final List<EntityType> entity = Utils.getEntityTypes(Utils.EntityTypeCats.LIVING);
    private final BossBar bossBar = Bukkit.createBossBar(WARTEN, BarColor.GREEN, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private EntityType currentMob = EntityType.UNKNOWN;
    private boolean running = false;
    public ForceMob(PlayerManager playerManager, Timer timer, UtilsForce utilsForce) {
        this.playerManager = playerManager;
        this.timer = timer;
        this.utilsForce = utilsForce;
        entity.remove(EntityType.ENDER_DRAGON);
        entity.remove(EntityType.SHULKER);
        entity.remove(EntityType.GIANT);
        entity.remove(EntityType.WANDERING_TRADER);
        entity.remove(EntityType.TRADER_LLAMA);
        entity.remove(EntityType.WITHER);
        entity.remove(EntityType.SKELETON_HORSE);
        entity.remove(EntityType.ZOMBIE_HORSE);
        entity.remove(EntityType.ARMOR_STAND);
        entity.remove(EntityType.ILLUSIONER);
        entity.remove(EntityType.ZOGLIN);
    }

    public EntityType getCurrentMob() {
        return currentMob;
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


    public void generateNew() {

        if (diff.equals(Challenge.ChallengeDiff.HARD))
            currentMob = Utils.getRandomEntityType(Utils.EntityTypeCats.LIVING);
        else currentMob = entity.get(random.nextInt(entity.size() - 1));
        int sec = random.nextInt(600) + 300;
        bossBar.setTitle("Töte in " + sec + "sec " + Utils.formateName(currentMob.name(),true));
        timer.addBossBar(bossBar, sec);
        for (Player player : playerManager.getPlayers()) {
            player.playSound(Sound.sound(org.bukkit.Sound.BLOCK_BELL_USE, Sound.Source.MASTER, 1, 1));
        }

        timer.addExecuteIn(sec, this::check);
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (running && (event.getEntity().getKiller() != null)) {
            players.add(event.getEntity().getKiller());
        }
    }


    public void check() {
        boolean check = !diff.equals(Challenge.ChallengeDiff.EASY);
        for (Player player : playerManager.getPlayers()) {
            if (diff.equals(Challenge.ChallengeDiff.EASY)) {
                if (players.contains(player)) check = true;
            } else if (!players.contains(player)) {
                check = false;
                lost.add(player);
            }
        }
        utilsForce.check(300,600, check,this::generateNew,bossBar,lost,"tötete","töteten","nicht",currentMob.name(),"");
        if(check) players.clear();
    }



}
