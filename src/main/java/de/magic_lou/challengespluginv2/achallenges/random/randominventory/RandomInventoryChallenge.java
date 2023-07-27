package de.magic_lou.challengespluginv2.achallenges.random.randominventory;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomInventoryChallenge implements Challenge {


    private final RandomInventory challenge;

    public RandomInventoryChallenge(RandomInventory challenge) {
        this.challenge = challenge;
    }

    @Override
    public void start() {
        challenge.start();
    }

    @Override
    public void pause() {
        //Timer handles
    }

    @Override
    public void resume() {
        //Timer handles
    }

    @Override
    public void restart() {
        //Timer handles
    }

    @Override
    public void stop() {
        //Timer handles
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
        d.add(ChatColor.DARK_PURPLE + "----RandomInventory----");
        d.add(ChatColor.WHITE + "Dein Inventar wird immer wieder randomized");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "EASY: " + ChatColor.WHITE + "Frühestens nach 5min max 7min");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Frühestens nach 3min max 5min und jedes Item bekommt ein neues Item zugeordnet");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Jedes Item wird random geändert");
        d.add(ChatColor.GOLD + "Commands:");
        d.add(ChatColor.GREEN + "/shuffle: " + ChatColor.WHITE + "Allen Blöcken werden neuen Items zugeordnet");
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
        return Material.BARREL;
    }
}
