package de.magic_lou.challengespluginv2.achallenges.random.randomblockplace;

import de.magic_lou.challengespluginv2.Challenge;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomBlockPlaceChallenge implements Challenge {


    private final RandomBlockPlace challenge;

    public RandomBlockPlaceChallenge(RandomBlockPlace randomBlockPlace) {
        this.challenge = randomBlockPlace;
    }

    @Override
    public void start() {
        challenge.setRunning(true);
    }

    @Override
    public void pause() {
        stop();

    }

    @Override
    public void resume() {
        start();

    }

    @Override
    public void restart() {
        stop();
        start();
    }

    @Override
    public void stop() {
        challenge.setRunning(false);
    }


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
        d.add(ChatColor.DARK_PURPLE + "----RandomBlockPlace----");
        d.add(ChatColor.WHITE + "Statt dem Block der normalerweise platziert wird, wird ein zufälliger platziert");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Jeder Block hat seinen Block der platziert wird");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Es ist jedes mal ein neuer Block der platziert wird");
        d.add(ChatColor.GOLD + "Commands:");
        d.add(ChatColor.GREEN + "/shuffle: " + ChatColor.WHITE + "Allen Blöcken werden neue Blöcke zugeordnet");

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
        if (!getDiffs().contains(diff)) diff = ChallengeDiff.NORMAL;
        challenge.setDiff(diff);
    }

    @Override
    public Material getItem() {
        return Material.WOODEN_PICKAXE;
    }
}
