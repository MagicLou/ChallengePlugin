package de.magic_lou.challengespluginv2.achallenges.random.randomblockzumob;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomBlockZuMobChallenge implements Challenge {


    private final RandomBlockZuMob challenge;

    public RandomBlockZuMobChallenge(RandomBlockZuMob randomBlockZuMob) {
        this.challenge = randomBlockZuMob;
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

    @Override
    public List<String> getMethods() {
        return Arrays.asList("shuffle", "Shuffles the Random-Set", "explosion", "Toggles weather Explosions trigger the Challenge");
    }

    @Override
    public void execMeth(String methode, Player player) {
        if (methode.equals("shuffle")) {
            challenge.shuffle();
            player.sendMessage(ChatColor.GREEN + "Shuffled " + ChatColor.WHITE + getClass().getSimpleName());
        }
        if (methode.equals("explosion")) {
            challenge.toggleExplosion();
            player.sendMessage(ChatColor.GOLD + "OnExplosion" + ChatColor.WHITE + " in " + getClass().getSimpleName() + " ist " + ChatColor.DARK_PURPLE + challenge.isExplosion());
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
        d.add(ChatColor.DARK_PURPLE + "----RandomBlockZuMob----");
        d.add(ChatColor.WHITE + "Wenn ein Block zerstört wird, spawnt an dieser Stelle statt dem Drop-Item ein Mob, der das Item zusätzlich zu seinem LootTable trägt");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "EASY: " + ChatColor.WHITE + "Du musst das Entity nicht töten um an den Block zu kommen");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Jeder Block hat seine Entity das gespawnt wird");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Es ist jedes mal ein zufälliges Entity das gespawnt wird");
        d.add(ChatColor.GOLD + "Commands:");
        d.add(ChatColor.GREEN + "/shuffle: " + ChatColor.WHITE + "Allen Blöcken werden neuen Entity's zugeordnet");
        d.add(ChatColor.GREEN + "/explosion: " + ChatColor.WHITE + "Allen Blöcken von Explosionen triggern nun die Challenge");
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
        if (!getDiffs().contains(diff)) diff = ChallengeDiff.NORMAL;
        challenge.setDiff(diff);
    }

    @Override
    public Material getItem() {
        return Material.ALLAY_SPAWN_EGG;
    }


}
