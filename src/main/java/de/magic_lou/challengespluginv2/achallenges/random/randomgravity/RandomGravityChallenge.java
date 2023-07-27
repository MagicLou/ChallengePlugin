package de.magic_lou.challengespluginv2.achallenges.random.randomgravity;

import de.magic_lou.challengespluginv2.Challenge;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomGravityChallenge implements Challenge {


    private final RandomGravity challenge;

    public RandomGravityChallenge(RandomGravity randomGravity) {
        this.challenge = randomGravity;
    }


    @Override
    public void start() {
        challenge.start();
    }

    @Override
    public void pause() {
        //If timer paused the Challenge is paused
    }

    @Override
    public void resume() {
        //if timer resumes the Challenge resumes
    }

    @Override
    public void restart() {
        stop();
        start();
    }

    @Override
    public void stop() {
        //if timer stops the Challenge stops
    }

    @Override
    public List<String> getMethods() {
        return Arrays.asList("randomize", "Select new Gravity now");
    }

    @Override
    public void execMeth(String methode, Player player) {
        if (methode.equals("randomize")) {
            challenge.selectRandom();
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
        d.add(ChatColor.DARK_PURPLE + "----RandomGravity----");
        d.add(ChatColor.WHITE + "Es herrschen zufällige Gravitationsfelde, welche sich nach zufälliger Zeit ändern");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "EASY: " + ChatColor.WHITE + "Maximal alle 10 Minuten eine neue Gravity");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Maximal alle 5 Minuten eine neue Gravity");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Maximal jede Minute eine neue Gravity");
        return d;
    }

    @Override
    public ChallengeType getType() {
        return ChallengeType.RANDOM;
    }

    @Override
    public List<ChallengeDiff> getDiffs() {
        return Arrays.stream(ChallengeDiff.values()).toList();
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
        return Material.ELYTRA;
    }
}
