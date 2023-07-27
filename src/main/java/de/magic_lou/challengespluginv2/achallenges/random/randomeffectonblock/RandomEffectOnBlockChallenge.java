package de.magic_lou.challengespluginv2.achallenges.random.randomeffectonblock;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomEffectOnBlockChallenge implements Challenge {

    private final RandomEffectOnBlock challenge;

    public RandomEffectOnBlockChallenge(RandomEffectOnBlock challenge) {
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
        d.add(ChatColor.DARK_PURPLE + "----RandomEffectOnBlock----");
        d.add(ChatColor.WHITE + "Auf jedem Block bekommst du unterschiedliche Effekte");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "EASY: " + ChatColor.WHITE + "Es sind nur positive Effekte");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Es sind alle Effekte und auf jeder Blockart gibt es unterschiedliche");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Auf jedem Block gibt es einen anderen Effekt");
        d.add(ChatColor.GOLD + "Commands:");
        d.add(ChatColor.GREEN + "/shuffle: " + ChatColor.WHITE + "Allen Blöcken werden neue Effekte zugeordnet");
        return d;
    }

    @Override
    public ChallengeType getType() {
        return ChallengeType.RANDOM;
    }

    @Override
    public List<ChallengeDiff> getDiffs() {
        return Utils.getChallengeDiffs();
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
        return Material.GRASS_BLOCK;
    }
}
