package de.magic_lou.challengespluginv2.achallenges.random.randommobloot;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomMobLootChallenge implements Challenge {

    private final RandomMobLoot challenge;

    public RandomMobLootChallenge(RandomMobLoot challenge) {
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
        return Arrays.asList("shuffle", "Shuffles the Random-Set","print","Toggles printing the new entitys");
    }

    @Override
    public void execMeth(String methode, Player player) {
        if (methode.equals("shuffle")) {
            challenge.shuffle();
            player.sendMessage(ChatColor.GREEN + "Shuffled " + ChatColor.WHITE + getClass().getSimpleName());
        }
        if(methode.equals("print")){
            challenge.print(player);
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
        d.add(ChatColor.DARK_PURPLE + "----RandomMobLoot----");
        d.add(ChatColor.WHITE + "Wenn ein Entity getötet wird, werden dir Drops eines anderen gedropt");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "EASY: " + ChatColor.WHITE + "Es wird zusätzlicher Loot gedropt");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Es wird andere Loot gedropt");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Es wird jedes mal random Loot gedropt");
        d.add(ChatColor.GOLD + "Commands:");
        d.add(ChatColor.GREEN + "/shuffle: " + ChatColor.WHITE + "Allen Entitys bekommen neue Drops");
        d.add(ChatColor.GREEN + "/print: " + ChatColor.WHITE + "Es wird nun ausgegeben was für ein Mob sich hinter dem getöteten verbirgt oder nicht");
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
        return Material.CHICKEN_SPAWN_EGG;
    }
}
