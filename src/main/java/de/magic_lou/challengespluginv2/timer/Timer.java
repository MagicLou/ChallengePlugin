package de.magic_lou.challengespluginv2.timer;

import de.magic_lou.challengespluginv2.Main;
import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import de.magic_lou.challengespluginv2.datenbank.RunManager;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Timer {
    private final ChallengeManager challengeManager;
    private final PlayerManager playerManager;
    private final List<Integer> executeTime = new LinkedList<>();
    private final List<Runnable> execute = new LinkedList<>();
    private int timerValue = -1;
    private BukkitTask task = null;
    private BukkitTask actionTask = null;
    private int id = -1;
    private boolean reverse = false;
    private final HashMap<BossBar, Integer> bossBarRunTime = new HashMap<>();
    private final HashMap<BossBar, Integer> bossBarStartTime = new HashMap<>();
    public Timer(PlayerManager playerManager, ChallengeManager challengeManager) {
        this.challengeManager = challengeManager;
        this.playerManager = playerManager;
        setUp();

    }

    public int getTimerValue() {
        return timerValue;
    }

    public void setTimerValue(int timerValue) {
        this.timerValue = timerValue;
    }

    public boolean isReverse() {
        return reverse;
    }

    private void setUp() {
        actionTask = Bukkit.getScheduler().runTaskTimer(Main.instance, () -> {
            String actionValue = ChatColor.RED + "ENDE";
            if (timerValue == -1) actionValue = ChatColor.GRAY + "Timer " + ChatColor.DARK_RED + "gestoppt";
            else if (timerValue >= 0) {
                StringBuilder builder = Utils.formatTime(timerValue, task == null ? ChatColor.GRAY : ChatColor.GOLD);
                if (task == null) builder.append(ChatColor.GOLD + " (pausiert)");
                actionValue = builder.toString();
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendActionBar(Component.text(actionValue));
            }
        }, 20, 20);
    }

    public void startTimer() {
        setUp();
        playerManager.start();
        challengeManager.start();

//        startCon();
        timerValue = 0;
        timerMeth();
    }

    private void startCon() {
        //For DataBase
        id = RunManager.NextID();
        RunManager.newRun(new RunManager.Run(id, System.currentTimeMillis(), null, null));
    }

    private void stopCon() {
        //For DataBase
//        RunManager.endRun(new RunManager.Run( id,-1,System.currentTimeMillis(),timerValue))
    }

    public void end() {
        challengeManager.beaten(timerValue);
        pauseTimer();
    }

    public void stopTimer() {
        challengeManager.stop();
        timerValue = -1;
        if (task != null) {
            task.cancel();
            task = null;
        }
        for (Player player : playerManager.getPlayers()) {
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
        }
        execute.clear();
        executeTime.clear();

    }

    public void pauseTimer() {
        challengeManager.pause();
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    public void resumeTimer() {
        playerManager.resume();
        challengeManager.resume();
        timerMeth();
    }

    public void reverseTimer() {
        reverse = !reverse;
    }

    public void setTimerValueString(String[] args) {
        if (args[0].equals("set")) {
            List<String> argsList = new java.util.ArrayList<>(Arrays.stream(args).toList());
            argsList.remove("set");
            args = argsList.toArray(new String[0]);
        }
        timerValue = 0;
        switch (args.length) {
            case 1 -> timerValue += Integer.parseInt(args[0]);
            case 2 -> {
                timerValue += Integer.parseInt(args[1]);
                timerValue += (Integer.parseInt(args[0]) * 60);
            }
            case 3 -> {
                timerValue += Integer.parseInt(args[2]);
                timerValue += (Integer.parseInt(args[1]) * 60);
                timerValue += (Integer.parseInt(args[0]) * 3600);
            }
            default -> {
                timerValue += Integer.parseInt(args[3]);
                timerValue += (Integer.parseInt(args[2]) * 60);
                timerValue += (Integer.parseInt(args[1]) * 3600);
                timerValue += (Integer.parseInt(args[0]) * 24 * 3600);
            }
        }
    }


    public void removeExecution(Runnable action) {
        int i = -1;
        for (Runnable runnable : execute) {
            i++;
            if (runnable.equals(action)) break;
        }
        try {
            execute.remove(i);
            executeTime.remove(i);
        } catch (IndexOutOfBoundsException ignored) {
            //Ignored
        }
    }

    public void addExecuteOn(int time, Runnable action) {
        if (time < 2) time = 2;
        if (reverse) time = time * -1;

        if (timerValue > time) {
            addExecuteIn(time, action);
        }

        executeTime.add(time);
        execute.add(executeTime.indexOf(time), action);
    }

    public void addExecuteIn(int time, Runnable action) {
        if (time < 2) time = 2;
        time = timerValue + time;
        addExecuteOn(time, action);
    }


    public void addBossBar(BossBar bar, int runTime) {
        bossBarRunTime.put(bar, runTime);
        bossBarStartTime.put(bar, timerValue);
    }

    public void processesBossBar(BossBar bar, int runTime, int startTime) {
        try {
            bar.setProgress(Math.abs((((double) timerValue - (double) startTime) / (double) runTime) - 1d));
            String name = bar.getTitle();
            String[] split = name.split("sec");
            String first = split[0].split("in ")[0];
            String last = split[1];
            bar.setTitle(first + "in " + (runTime - (timerValue - startTime)) + " sec" + last);
            if (runTime == timerValue - startTime) {
                bar.setProgress(1);
                removeBossBar(bar);
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
            removeBossBar(bar);
        }
    }

    public void removeBossBar(BossBar bar) {
        bossBarRunTime.remove(bar);
        bossBarStartTime.remove(bar);
    }

    private void timerMeth() {
        if (task != null) {
            task.cancel();
        }

        task = Bukkit.getScheduler().runTaskTimer(Main.instance, () -> {


            if (executeTime.contains(timerValue)) {
                execute.get(executeTime.indexOf(timerValue)).run();
            }


            for (BossBar bossBar : bossBarRunTime.keySet()) {
                processesBossBar(bossBar, bossBarRunTime.get(bossBar), bossBarStartTime.get(bossBar));

            }


            if (reverse) {

                if (timerValue < 0) {
                    pauseTimer();
                    challengeManager.pause();
                    playerManager.getPlayers().get(0).setHealth(0);
                    Bukkit.broadcast(Component.text("Time's up"));
                    reverse = false;
                }

                timerValue--;
            } else timerValue++;
        }, 20, 20);

    }

}


