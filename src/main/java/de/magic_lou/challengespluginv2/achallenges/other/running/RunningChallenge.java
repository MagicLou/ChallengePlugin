package de.magic_lou.challengespluginv2.achallenges.other.running;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RunningChallenge implements Challenge{



    private final Running challenge;

    public RunningChallenge(Running challenge) {
        this.challenge = challenge;
    }


    @Override
    public void start() {
        challenge.start();
    }

    @Override
    public void pause() {
        challenge.stop();
    }

    @Override
    public void resume() {
        challenge.start();
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
        d.add(ChatColor.DARK_PURPLE + "----Running----");
        d.add(ChatColor.WHITE + "Du kannst nicht stehen bleiben");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "EASY: " + ChatColor.WHITE + "Du gehst die ganze Zeit");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Du läufst die ganze Zeit");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Du rennst die ganze Zeit");

        return d;
    }

    @Override
    public Challenge.ChallengeType getType() {
        return Challenge.ChallengeType.OTHER;
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
        return Material.SUGAR;
    }




}
