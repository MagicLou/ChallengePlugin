package de.magic_lou.challengespluginv2.achallenges.force.forcebiom;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForceBiomChallenge implements Challenge {


    private final ForceBiom challenge;

    public ForceBiomChallenge(ForceBiom challenge) {
        this.challenge = challenge;
    }

    @Override
    public void start() {
        challenge.start();
    }

    @Override
    public void pause() {
        //if timer paused the Challenge is paused
    }

    @Override
    public void resume() {
        //if timer resume the Challenge resumes
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
        return Arrays.asList("skip", "generates new Block", "try", "checks the request now");
    }

    @Override
    public void execMeth(String methode, Player player) {
        if (methode.equals("skip")) {
            player.sendMessage(Component.text("Skipped " + Utils.formateName(challenge.getCurrentBiom().name(),true)));
            challenge.skipp();
        }
        if (methode.equals("try")) {
            player.sendMessage(Component.text("Checked " + Utils.formateName(challenge.getCurrentBiom().name(),true)));
            challenge.forceCheck();
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
        d.add(ChatColor.DARK_PURPLE + "----ForceBiom----");
        d.add(ChatColor.WHITE + "Alle paar Minuten wird eine Anweisung in Form eines Bioms gestellt. " +
                "Wenn die Zeit abgelaufen ist wird überprüft ob sich alle Spieler in diesem Biom befinden. " +
                "Wenn nicht ist die Challenge vorbei");
        d.add(ChatColor.GOLD + "Difficulty:");
        d.add(ChatColor.GREEN + "EASY: " + ChatColor.WHITE + "Es werden Biom der gleichen Region gewählt");
        d.add(ChatColor.GREEN + "NORMAL: " + ChatColor.WHITE + "Es werden Biom der gleichen Welt gewählt");
        d.add(ChatColor.GREEN + "HARD: " + ChatColor.WHITE + "Es wird aus allen Biomes gewählt");
        d.add(ChatColor.GOLD + "Commands:");
        d.add(ChatColor.GREEN + "/skip: " + ChatColor.WHITE + "Es wird die aktuelle Anweisung geskippt und eine neue gestellt");
        d.add(ChatColor.GREEN + "/try: " + ChatColor.WHITE + "Es wird jetzt überprüft ob die Anweisung erfüllt ist");

        return d;
    }

    @Override
    public Challenge.ChallengeType getType() {
        return Challenge.ChallengeType.FORCE;
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
        return Material.SAND;
    }

}
