package de.magic_lou.challengespluginv2.achallenges.other.backport;

import de.magic_lou.challengespluginv2.Challenge;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BackPortChallenge implements Challenge {

    private final BackPort challenge;

    public BackPortChallenge(BackPort challenge) {
        this.challenge = challenge;
    }


    @Override
    public void start() {
        challenge.start();
    }

    @Override
    public void pause() {
        challenge.pause();
    }

    @Override
    public void resume() {
        challenge.resume();
    }

    @Override
    public void restart() {
        stop();
        start();
    }

    @Override
    public void stop() {
        challenge.stop();
    }

    @Override
    public List<String> getMethods() {
        return Arrays.asList("wayPointKill", "Kills your current WayPoint");

    }

    @Override
    public void execMeth(String methode, Player player) {
        if (methode.equals("wayPointKill")) {
            challenge.wayKill(player);
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
        d.add(ChatColor.DARK_PURPLE + "----BackPort----");
        d.add(ChatColor.WHITE + "Du kannst durch sneaken einen WayPoint erstellen. Wenn du nochmals sneaks wirst du dorthin zurück teleportiert");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Only Normal Difficulty");
        d.add(ChatColor.GOLD + "Commands:");
        d.add(ChatColor.GREEN + "/waypointkill: " + ChatColor.WHITE + "Löscht deinen WayPoint");

        return d;
    }

    @Override
    public Challenge.ChallengeType getType() {
        return Challenge.ChallengeType.OTHER;
    }

    @Override
    public List<ChallengeDiff> getDiffs() {
        return Collections.singletonList(ChallengeDiff.NORMAL);
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
        return Material.BEACON;
    }
}
