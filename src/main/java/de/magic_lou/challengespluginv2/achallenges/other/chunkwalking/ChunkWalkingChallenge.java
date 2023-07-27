package de.magic_lou.challengespluginv2.achallenges.other.chunkwalking;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChunkWalkingChallenge  implements Challenge, Listener {


    private final ChunkWalking challenge;

    public ChunkWalkingChallenge(ChunkWalking challenge) {
        this.challenge = challenge;
    }


    @Override
    public void start() {
        challenge.setRunning(true);
    }

    @Override
    public void pause() {
        stop();
    }

    @Override
    public void resume() {
        start();
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
        d.add(ChatColor.DARK_PURPLE + "----ChunkWalking----");
        d.add(ChatColor.WHITE + "Wenn du dich bewegst, wird der Blocktyp auf dem du stehst im ganzen Chunk entfernt");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "EASY: " + ChatColor.WHITE + "Es wird nur alles über dir entfernt");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Der Blocktyp wird im ganzen Chunk entfernt");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Es wird der ganze Chunk gelöscht");

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
        return Material.STONE_PICKAXE;
    }


}

