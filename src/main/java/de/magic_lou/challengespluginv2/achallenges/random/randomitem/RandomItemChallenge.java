package de.magic_lou.challengespluginv2.achallenges.random.randomitem;

import de.magic_lou.challengespluginv2.Challenge;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomItemChallenge implements Challenge {


    private final RandomItem challenge;

    public RandomItemChallenge(RandomItem randomItem) {
        this.challenge = randomItem;
    }


    @Override
    public void start() {
        Bukkit.broadcast(Component.text("If it is taking a while the World for this Challenge is getting generated"));
        challenge.setRunning(true);
        challenge.start();
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
        return Arrays.asList("setWorldNull", "Same as Reset");
    }

    @Override
    public void execMeth(String methode, Player player) {
        if (methode.equals("setWorldNull")) {
            player.performCommand("reset");
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
        d.add(ChatColor.DARK_PURPLE + "----RandomItem----");
        d.add(ChatColor.WHITE + "Bekomme in bestimmten Zeitabständen neue Items. Zu Beginn besteht die Welt aus einem Bedrock-Block");
        d.add(ChatColor.WHITE + "Um deine Welt zu resetten musst du /reset verwenden oder ds GUI-Inventar");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "EASY: " + ChatColor.WHITE + "Alle 10 Sekunden ein neues Item");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Jede Minute ein neues Item");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Alle 5 Minuten ein neues Item");
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
        return Material.DISPENSER;
    }
}
