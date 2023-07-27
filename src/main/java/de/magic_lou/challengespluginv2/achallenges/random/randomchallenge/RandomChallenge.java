package de.magic_lou.challengespluginv2.achallenges.random.randomchallenge;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomChallenge implements Challenge {
    //Weiß ich

    private static final String MSG = ChatColor.GRAY + "The Plugin chooses a " + ChatColor.DARK_PURPLE + "random Challenge" + ChatColor.GRAY + " for you";
    public final Random random = new Random();
    private final ChallengeManager challengeManager;
    private Challenge theRandomChallenge;

    public RandomChallenge(ChallengeManager challengeManager) {
        theRandomChallenge = challengeManager.selectRandom();
        this.challengeManager = challengeManager;
    }

    @Override
    public void start() {
        theRandomChallenge.start();
        Bukkit.broadcast(Component.text(ChatColor.GOLD + theRandomChallenge.getClass().getSimpleName()));
    }

    @Override
    public void pause() {
        theRandomChallenge.pause();
    }

    @Override
    public void resume() {
        theRandomChallenge.resume();
    }

    @Override
    public void restart() {
        stop();
        start();
    }

    @Override
    public void stop() {
        theRandomChallenge.stop();
        theRandomChallenge = challengeManager.selectRandom();
    }

    @Override
    public List<String> getMethods() {
        List<String> ret = theRandomChallenge.getMethods();
        ret.add("getChallenge");
        return ret;
    }

    @Override
    public void execMeth(String methode, Player player) {
        if (methode.equals("getChallenge"))
            player.sendMessage("Die Challenge ist " + ChatColor.GOLD + theRandomChallenge.getClass().getSimpleName());
        else {
            theRandomChallenge.execMeth(methode, player);
        }
    }

    public String getChallenge() {
        return theRandomChallenge.getClass().getSimpleName();
    }

    @Override
    public void description(Player player) {

        player.sendMessage(MSG);

    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList(MSG);
    }

    @Override
    public ChallengeType getType() {
        return ChallengeType.RANDOM;
    }

    @Override
    public List<ChallengeDiff> getDiffs() {
        return theRandomChallenge.getDiffs();
    }

    @Override
    public ChallengeDiff getDiff() {
        return theRandomChallenge.getDiff();
    }


    @Override
    public void setDiff(ChallengeDiff diff) {
        theRandomChallenge.setDiff(diff);
    }

    @Override
    public Material getItem() {
        return Material.MELON_SEEDS;
    }

}
