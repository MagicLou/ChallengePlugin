package de.magic_lou.challengespluginv2.achallenges.other.yeet;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class YeetChallenge implements Challenge {


    private final Yeet challenge;

    public YeetChallenge(Yeet challenge) {
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
        return List.of();
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
        d.add(ChatColor.DARK_PURPLE + "----Yeet----");
        d.add(ChatColor.WHITE + "Alle paar Sekunden wirst du random durch dir Gegend ge-YEET-ed");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "EASY: " + ChatColor.WHITE + "Kleines Yeet");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Solides Yeet");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Großes Yeet");

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
        return Material.SLIME_BLOCK;
    }
}
