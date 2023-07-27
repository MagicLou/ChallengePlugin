package de.magic_lou.challengespluginv2.achallenges.random.randomeffectondamage;

import de.magic_lou.challengespluginv2.Challenge;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomEffectOnDamageChallenge implements Challenge {

    private final RandomEffectOnDamage challenge;

    public RandomEffectOnDamageChallenge(RandomEffectOnDamage challenge) {
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
        d.add(ChatColor.DARK_PURPLE + "----RandomEffectOnDamage----");
        d.add(ChatColor.WHITE + "Wenn du Damage bekommst, bekommst du zusätzlich einen Random Effekt");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "EASY: " + ChatColor.WHITE + "Es gibt nur positive Effekte");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Tödliche Effekte wie Wither oder Instant-Damage sind ausgeschlossen");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Es kann jeder Effekt werden");
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
        return Material.POTION;
    }
}
