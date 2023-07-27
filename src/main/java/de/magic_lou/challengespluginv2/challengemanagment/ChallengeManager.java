package de.magic_lou.challengespluginv2.challengemanagment;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.gravitys.GravityManager;
import de.magic_lou.challengespluginv2.hardcoremanagment.HardCoreManager;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.timer.PauseStopListener;
import de.magic_lou.challengespluginv2.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.*;

public class ChallengeManager {

    public final List<Challenge> aktiveChallenges;
    public final Map<String, Challenge> challenges;
    public final Map<String, Challenge> cRandom;
    public final Map<String, Challenge> cForce;
    public final Map<String, Challenge> cOther;
    public final Map<String, Challenge> cGen;
    public final Map<String, Challenge> cBattle;
    public final Map<String, Challenge> cNotWorking;
    private final HardCoreManager hardCoreManager;
    private final PauseStopListener pauseStopListener;
    private final PlayerManager playerManager;
    private final GravityManager gravityManager;
    Random random = new Random();
    private boolean running;
    public ChallengeManager(PauseStopListener pauseStopListener, PlayerManager playerManager, HardCoreManager hardCoreManager, GravityManager gravityManager) {
        this.hardCoreManager = hardCoreManager;
        this.pauseStopListener = pauseStopListener;
        this.playerManager = playerManager;
        this.gravityManager = gravityManager;
        setRunning(false);
        aktiveChallenges = new ArrayList<>();
        challenges = new HashMap<>();
        cRandom = new HashMap<>();
        cForce = new HashMap<>();
        cGen = new HashMap<>();
        cBattle = new HashMap<>();
        cOther = new HashMap<>();
        cNotWorking = new HashMap<>();

    }

    public boolean isRunning() {
        return running;
    }

    private void setRunning(boolean r) {
        running = r;
        pauseStopListener.setPaused(r);
    }

    public void registerChallenge(Challenge challenge) {
        challenges.put(challenge.getClass().getSimpleName(), challenge);
        if (challenge.getType() == null) {
            Bukkit.broadcast(Component.text(challenge.getClass().getSimpleName() + " has Type null"));
            cNotWorking.put(challenge.getClass().getSimpleName(), challenge);
            return;
        }
        switch (challenge.getType()) {
            case RANDOM -> cRandom.put(challenge.getClass().getSimpleName(), challenge);
            case FORCE -> cForce.put(challenge.getClass().getSimpleName(), challenge);
            case BALLTE -> cBattle.put(challenge.getClass().getSimpleName(), challenge);
            case GENERATOR -> cGen.put(challenge.getClass().getSimpleName(), challenge);
            case NOTWORKING -> cNotWorking.put(challenge.getClass().getSimpleName(), challenge);
            case OTHER -> cOther.put(challenge.getClass().getSimpleName(), challenge);
            default -> throw new IllegalArgumentException("Challenge " + challenge + " has no Type");
        }
    }

    public Challenge selectRandom() {
        return (Challenge) challenges.values().toArray()[random.nextInt(challenges.values().size())];
    }


    public void start() {
        setRunning(true);
        for (Challenge challenge : aktiveChallenges) {
            challenge.start();
            String name = challenge.getClass().getSimpleName();
            Bukkit.broadcast(Component.text(ChatColor.GOLD + name + ChatColor.GREEN + " " + challenge.getDiff() + ChatColor.GRAY + " gestartet"));
        }
        Bukkit.broadcast(Component.text(ChatColor.GRAY + "Es wird " + ChatColor.GREEN + hardCoreManager.getGameDiff() + ChatColor.GRAY + " gespielt"));
        gravityManager.start();
    }

    public void pause() {
        setRunning(false);
        for (Challenge challenge : aktiveChallenges) {
            challenge.pause();
            String name = challenge.getClass().getSimpleName();
            Bukkit.broadcast(Component.text(ChatColor.GOLD + name + ChatColor.GRAY + ChatColor.GRAY + " pausiert"));
            gravityManager.pause();
        }
    }

    public void stop() {
        setRunning(false);
        for (Challenge challenge : aktiveChallenges) {
            challenge.stop();
            String name = challenge.getClass().getSimpleName();
            Bukkit.broadcast(Component.text(ChatColor.GOLD + name + ChatColor.GRAY + ChatColor.GRAY + " gestoppt"));
        }
        gravityManager.stop();
    }

    public void resume() {
        setRunning(true);
        for (Challenge challenge : aktiveChallenges) {
            challenge.resume();
            String name = challenge.getClass().getSimpleName();
            Bukkit.broadcast(Component.text(ChatColor.GOLD + name + ChatColor.GRAY + " fortgesetzt"));
        }
        gravityManager.resume();
    }


    public void addDiffChallenge(String challengeName, Challenge.ChallengeDiff diff) {
        Challenge challenge = challenges.get(challengeName);
        if (challenge.getDiffs().contains(diff)) challenge.setDiff(diff);
        else challenge.setDiff(Challenge.ChallengeDiff.NORMAL);

        addChallenge(challengeName);

    }

    public void addChallenge(String challengeName) {
        Challenge challenge = challenges.get(challengeName);

        aktiveChallenges.remove(challenge);
        aktiveChallenges.add(challenge);
    }


    public void removeChallenge(String challengeName) {
        Challenge challenge = challenges.get(challengeName);
        if (aktiveChallenges.contains(challenge)) challenge.stop();
        aktiveChallenges.remove(challenge);
    }

    public List<String> challengesMeths(String challengeName) {
        Challenge challenge = challenges.get(challengeName);
        if (challenge.getMethods() == null)
            return Collections.singletonList(ChatColor.GRAY + "Die Challenge " + ChatColor.GOLD + challengeName + ChatColor.GRAY + " hat" + ChatColor.RED + " keine" + ChatColor.GRAY + " eigenen Commands");
        else return challenge.getMethods();
    }

    public List<String> challengeDescription(String challengeName) {
        return challenges.get(challengeName).getDescription();
    }


    public void honorChallenges(ChatColor color) {
        if (!aktiveChallenges.isEmpty()) {
            if (aktiveChallenges.size() == 1) Bukkit.broadcast(Component.text(color + "Es wurde die Challenge:"));
            else Bukkit.broadcast(Component.text(color + "Es wurden die Challenges:"));
            for (Challenge aktiveChallenge : aktiveChallenges) {
                Bukkit.broadcast(Component.text(color + aktiveChallenge.getClass().getSimpleName() + aktiveChallenge.getDiff()));
            }
        } else Bukkit.broadcast(Component.text(color + "Das Spiel wurde"));
    }

    public void beaten(int time) {
        stop();
        Bukkit.broadcast(Component.text(ChatColor.GRAY + "Das Spiel wurde erfolgreich nach " + Utils.formatTime(time, ChatColor.DARK_GREEN) + ChatColor.GREEN + " abgeschlossen"));
        honorChallenges(ChatColor.GOLD);
        Bukkit.broadcast(Component.text(ChatColor.AQUA + "von:"));
        playerManager.honorPlayers(ChatColor.AQUA);
        Bukkit.broadcast(Component.text(ChatColor.GREEN + "durchgespielt"));
        playerManager.honorSpectators(ChatColor.GRAY);
    }


    public List<String> getChallenge() {
        List<String> ret = new ArrayList<>();
        for (Challenge aktiveChallenge : aktiveChallenges) {
            ret.add(aktiveChallenge.getClass().getSimpleName());
        }
        return ret;
    }


}
