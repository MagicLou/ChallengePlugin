package de.magic_lou.challengespluginv2.achallenges.other.chunkbreaking;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChunkBreakingChallenge implements Challenge {


    private final ChunkBreaking challenge;

    public ChunkBreakingChallenge(ChunkBreaking challenge) {
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

    @Override
    public List<String> getMethods() {
        return Arrays.asList("place", "Toggles if placing Blocks will remove Blocks in the Chunk");

    }

    @Override
    public void execMeth(String methode, Player player) {
        if (methode.equals("place")) {
            challenge.place(player);
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
        d.add(ChatColor.DARK_PURPLE + "----ChunkBreaking----");
        d.add(ChatColor.WHITE + "Wenn du einen Block abbaust, baust du ihm im ganzen Chunk ab. OPTIONAL: Wenn du einen Block platzierst passiert das gleiche");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "EASY: " + ChatColor.WHITE + "Du baust den ganzen Chunk ab, und kannst normal platzieren");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Du baust nur den einen BlockTyp ab, beim platzieren wird nur ein BlockTyp gelöscht");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Du bekommst nicht alle abgebauten Blöcke und wenn du einen Block platzierst wird der ganze Chunk gelöscht");
        d.add(ChatColor.GOLD + "Commands:");
        d.add(ChatColor.GREEN + "/place: " + ChatColor.WHITE + "Blöcke platzieren löscht nun den Chunk oder nicht");

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
