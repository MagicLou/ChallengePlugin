package de.magic_lou.challengespluginv2.achallenges.random.randomchestloot;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomChestLootChallenge implements Challenge {

    private final RandomChestLoot challenge;

    public RandomChestLootChallenge(RandomChestLoot challenge) {
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
        stop();
        start();
    }

    @Override
    public void stop() {
        challenge.setRunning(false);
    }


    @Override
    public List<String> getMethods() {
        return Arrays.asList("shuffle", "Shuffles the Random-Set","pickup","Toggles picking up other items");
    }

    @Override
    public void execMeth(String methode, Player player) {
        if (methode.equals("shuffle")) {
            challenge.shuffle();
            player.sendMessage(ChatColor.GREEN + "Shuffled " + ChatColor.WHITE + getClass().getSimpleName());
        }
        if(methode.equals("pickup")){
            challenge.pickUp(player);
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
        d.add(ChatColor.DARK_PURPLE + "----RandomChestLoot----");
        d.add(ChatColor.WHITE + "Der Inhalt von Truhe wurden geändert");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "EASY: " + ChatColor.WHITE + "Es gibt mehr und zufällige Items");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Es gibt in jeder Truhe neue Items die sich von Truhe zu Truhe unterscheiden");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Es wurde in alle Truhen die selben Items mit den selben neuen ausgetauscht");
        d.add(ChatColor.GOLD + "Commands:");
        d.add(ChatColor.GREEN + "/shuffle: " + ChatColor.WHITE + "Allen Blöcken werden neuen Entity's zugeordnet");
        d.add(ChatColor.GREEN + "/pickup: " + ChatColor.WHITE + "Es können nun zusätzliche Items aufgenommen werden oder nicht");
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
        return Material.CHEST;
    }
}
