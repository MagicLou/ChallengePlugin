package de.magic_lou.challengespluginv2.achallenges.random.randomentityspawnonkill;

import de.magic_lou.challengespluginv2.Challenge;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomEntitySpawnOnKillChallenge implements Challenge {

    private final RandomEntitySpawnOnKill challenge;

    public RandomEntitySpawnOnKillChallenge(RandomEntitySpawnOnKill challenge) {
        this.challenge = challenge;
    }


    @Override
    public void start() {
        challenge.shuffle();
        challenge.setRunning(true);
    }

    @Override
    public void pause() {
        challenge.setRunning(false);
    }

    @Override
    public void resume() {
        challenge.setRunning(true);
    }

    @Override
    public void restart() {
        start();
        stop();
    }

    @Override
    public void stop() {
        challenge.setRunning(false);
    }

    @Override
    public List<String> getMethods() {
        return Arrays.asList("shuffle", "Shuffles the Random-Set");
    }

    @Override
    public void execMeth(String methode, Player player) {
        if (methode.equals("shuffle")) {
            challenge.shuffle();
            player.sendMessage(ChatColor.GREEN + "Shuffled " + ChatColor.WHITE + getClass().getSimpleName());
        }
    }

    @Override
    public void description(Player player) {
        for (String s : getDescription()) {
            player.sendMessage(s);
        }
    }

    @Override
    public List<String> getDescription() {
        List<String> d = new ArrayList<>();
        d.add(ChatColor.DARK_PURPLE + "----RandomEntitySpawnOnKill----");
        d.add(ChatColor.WHITE + "Wenn ein Entity getötet wird spawnt ein neues");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Jeder EntityType hat sein Entity das gespawnt wird");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Es ist jedes mal ein zufälliges Entity das gespawnt wird");
        d.add(ChatColor.GOLD + "Commands:");
        d.add(ChatColor.GREEN + "/shuffle: " + ChatColor.WHITE + "Allen Entitys werden neuen Entitys zugeordnet");
        return d;
    }

    @Override
    public ChallengeType getType() {
        return ChallengeType.RANDOM;
    }

    @Override
    public List<ChallengeDiff> getDiffs() {
        return Arrays.asList(ChallengeDiff.NORMAL, ChallengeDiff.HARD);
    }

    @Override
    public ChallengeDiff getDiff() {
        return challenge.getDiff();
    }

    @Override
    public void setDiff(ChallengeDiff diff) {
        challenge.setDiff(diff);
    }

    @Override
    public Material getItem() {
        return Material.SCULK_SHRIEKER;
    }
}
