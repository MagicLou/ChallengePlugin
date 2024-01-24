package de.magic_lou.challengespluginv2.achallenges.random.randomizer;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomizerChallenge implements Challenge {

    private final Randomizer challenge;

    public RandomizerChallenge(Randomizer challenge) {
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
        return Arrays.asList("shuffle", "Shuffles the Random-Set", "toggleItem", "toggles weather the randomItem is from the BlockType or its Drops","toggleDrop", "toggles weather the Item Drops as destroyed with used or with Tool ");
    }

    @Override
    public void execMeth(String methode, Player player) {
        if (methode.equals("shuffle")) {
            challenge.shuffle();
            player.sendMessage(ChatColor.GREEN + "Shuffled " + ChatColor.WHITE + getClass().getSimpleName());
        }
        if (methode.equals("toggleItem")) {
            player.sendMessage(ChatColor.WHITE + "Es werden nun die " + ChatColor.GOLD + challenge.toggle() + ChatColor.WHITE + " randomized");
        }
        if (methode.equals("toggleDrop")) {
            player.sendMessage(ChatColor.WHITE + "Es werden nun die " + ChatColor.GOLD + challenge.toggleDrops() + ChatColor.WHITE + " randomized");
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
        d.add(ChatColor.DARK_PURPLE + "----Randomizer----");
        d.add(ChatColor.WHITE + "Wenn ein Block zerstört wird, dropt er ein anderes Item");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "EASY: " + ChatColor.WHITE + "Jeder Block dropt zusätzlich ein random Item");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Jeder Block hat seine Item das gedropt wird");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Es ist jedes mal ein zufälliges Item das gedropt wird");
        d.add(ChatColor.GOLD + "Commands:");
        d.add(ChatColor.GREEN + "/shuffle: " + ChatColor.WHITE + "Allen Blöcken werden neuen Items zugeordnet");
        d.add(ChatColor.GREEN + "/toggleItem: " + ChatColor.WHITE + "Toggle ob der Block randomized wird oder seine Drops");
        d.add(ChatColor.GREEN + "/toggleDrop: " + ChatColor.WHITE + "Toggle ob der Block mit dem auswählten Item oder einem Tool zerstört wurde");
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
        return Material.DIAMOND_PICKAXE;
    }
}
