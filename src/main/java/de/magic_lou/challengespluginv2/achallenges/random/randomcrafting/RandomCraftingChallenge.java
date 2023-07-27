package de.magic_lou.challengespluginv2.achallenges.random.randomcrafting;

import de.magic_lou.challengespluginv2.Challenge;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomCraftingChallenge implements Challenge {

    private final RandomCrafting challenge;

    public RandomCraftingChallenge(RandomCrafting challenge) {
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
        challenge.reset();
    }

    @Override
    public void stop() {
        challenge.stop();
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
        d.add(ChatColor.DARK_PURPLE + "----RandomCrafting----");
        d.add(ChatColor.WHITE + "Wenn man Crafted werden neue Items hergestellt");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Jedes CraftingRezepten bekommt ein eigenes Item");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Es kommt jedes mal ein zufälliges Item heraus");
        d.add(ChatColor.GOLD + "Commands:");
        d.add(ChatColor.GREEN + "/shuffle: " + ChatColor.WHITE + "Allen CraftingRezepten werden neuen Items zugeordnet");
        return d;
    }

    @Override
    public ChallengeType getType() {
        return ChallengeType.RANDOM;
    }

    @Override
    public List<ChallengeDiff> getDiffs() {
        return Arrays.asList(ChallengeDiff.NORMAL, ChallengeDiff.HARD);
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
        return Material.CRAFTING_TABLE;
    }
}
