package de.magic_lou.challengespluginv2.achallenges.random.randomblocklook;

import de.magic_lou.challengespluginv2.Challenge;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomBlockLookChallenge implements Challenge {

    private final RandomBlockLook challenge;

    public RandomBlockLookChallenge(RandomBlockLook challenge) {
        this.challenge = challenge;
    }


    @Override
    public void start() {
        challenge.start();
    }

    @Override
    public void pause() {
        this.stop();
    }

    @Override
    public void resume() {
        this.start();
    }

    @Override
    public void restart() {
        this.stop();
        this.start();
    }

    @Override
    public void stop() {
        challenge.stop();
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
        d.add(ChatColor.DARK_PURPLE + "----RandomBlockLook----");
        d.add(ChatColor.WHITE + "Der Block, den du anschaust, wird zufällig geändert");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "EASY: " + ChatColor.WHITE + "Der Block ändert sich nur, wenn du ihn neu anschaust");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Der Block ändert sich nicht wenn du sneakst");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Der Block ändert sich alle 1/20sec");
        return d;
    }

    @Override
    public ChallengeType getType() {
        return ChallengeType.RANDOM;
    }

    @Override
    public List<ChallengeDiff> getDiffs() {
        return Arrays.asList(ChallengeDiff.values());
        // EASY if stay looking on block no change
        // NORMAL when sneaking no change
        // HARD everytime change
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
        return Material.SPYGLASS;
    }
}
