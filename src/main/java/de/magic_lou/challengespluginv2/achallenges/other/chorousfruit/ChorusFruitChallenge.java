package de.magic_lou.challengespluginv2.achallenges.other.chorousfruit;

import de.magic_lou.challengespluginv2.Challenge;

import java.util.ArrayList;
import java.util.Collections;

import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class ChorusFruitChallenge implements Challenge {

    private final ChorusFruit challenge;

    public ChorusFruitChallenge(ChorusFruit challenge) {
        this.challenge = challenge;
    }

    @Override
    public void start() {
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
        stop();
        start();
    }

    @Override
    public void stop() {
        challenge.setRunning(false);
    }

    @Override
    public List<String> getMethods() {
        return Collections.emptyList();
    }

    @Override
    public void execMeth(String methode, Player player) {
        player.sendMessage("No Methods");
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
        d.add(ChatColor.DARK_PURPLE + "----ChorusFruit----");
        d.add(ChatColor.WHITE + "Wenn du isst, wirst du random teleportiert");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "EASY: " + ChatColor.WHITE + "Kleiner Radius");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Solider Radius");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Großer Radius");

        return d;
    }

    @Override
    public ChallengeType getType() {
        return ChallengeType.OTHER;
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
        return Material.CHORUS_FRUIT;
    }
}
