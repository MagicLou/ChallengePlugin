package de.magic_lou.challengespluginv2.timer;

import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TimerCommand implements TabExecutor {


    private static final String DER_TIMER_WURDE = ChatColor.GRAY + "Der Timer wurde";
    private static final String PAUSIERT = " pausiert";
    private final ChallengeManager challengeManager;
    private final Timer timer;
    public TimerCommand(ChallengeManager challengeManager, Timer timer) {
        this.challengeManager = challengeManager;
        this.timer = timer;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (args.length < 1) {
            description((Player) sender);
            return false;
        }


        switch (args[0]) {
            case "start", "resume" -> {
                if (challengeManager.isRunning()) {
                    sender.sendMessage(ChatColor.GRAY + "Der Timer " + ChatColor.DARK_RED + "läuft" + ChatColor.GRAY + " schon");
                    return false;
                } else if (timer.getTimerValue() >= 0) {
                    timer.resumeTimer();
                    sender.sendMessage(ChatColor.GRAY + DER_TIMER_WURDE + ChatColor.GREEN + " fortgesetzt");
                    return false;
                }
                timer.startTimer();
                sender.sendMessage(ChatColor.GRAY + DER_TIMER_WURDE + ChatColor.GREEN + " gestartet");
            }
            case "stop" -> {
                if (timer.getTimerValue() < 0) {
                    sender.sendMessage(ChatColor.GRAY + "Der Timer ist schon " + ChatColor.DARK_RED + "gestoppt");
                    return false;
                }
                timer.stopTimer();
                sender.sendMessage(ChatColor.GRAY + DER_TIMER_WURDE + ChatColor.GREEN + " gestoppt");
            }
            case "pause" -> {
                if (timer.getTimerValue() < 0) {
                    sender.sendMessage(ChatColor.GRAY + "Der Timer läuft " + ChatColor.DARK_RED + "nicht");
                    return false;
                } else if (!challengeManager.isRunning()) {
                    sender.sendMessage(ChatColor.GRAY + "Der Timer ist schon" + ChatColor.GOLD + PAUSIERT);
                    return false;
                }
                timer.pauseTimer();
                sender.sendMessage(ChatColor.GRAY + DER_TIMER_WURDE + ChatColor.GREEN + PAUSIERT);
            }
            case "reset" -> timer.setTimerValue(0);
            case "set" -> {
                timer.setTimerValueString(args);
                sender.sendMessage(ChatColor.GRAY + "Timer wurde auf " + Utils.formatTime(timer.getTimerValue(), ChatColor.DARK_GREEN) + ChatColor.GRAY + " gesetzt");
            }
            case "reverse" -> {
                timer.reverseTimer();
                if (timer.isReverse())
                    sender.sendMessage(ChatColor.GRAY + DER_TIMER_WURDE + " läuft jetzt" + ChatColor.GREEN + " rückwärts");
                else
                    sender.sendMessage(ChatColor.GRAY + DER_TIMER_WURDE + " läuft jetzt" + ChatColor.GREEN + " vorwärts");
            }
            default -> description((Player) sender);
        }

        return true;
    }

    public void description(Player player) {
        player.sendMessage("---------------------");
        String theTimer = ChatColor.WHITE + "the Timer";
        player.sendMessage(ChatColor.DARK_PURPLE + "Use Timer-Command like this:");
        player.sendMessage(ChatColor.WHITE + "/timer " + ChatColor.GOLD + "[action]");
        player.sendMessage("");
        player.sendMessage(ChatColor.GOLD + "[action]");
        player.sendMessage(ChatColor.GOLD + "start or resume: " + ChatColor.DARK_PURPLE + "Starts or resumes " + theTimer);
        player.sendMessage(ChatColor.GOLD + "pause: " + ChatColor.DARK_PURPLE + "Pauses " + theTimer);
        player.sendMessage(ChatColor.GOLD + "stop: " + ChatColor.DARK_PURPLE + "Stops " + theTimer);
        player.sendMessage(ChatColor.GOLD + "reset: " + ChatColor.DARK_PURPLE + "Resets " + theTimer + " to 0");
        player.sendMessage(ChatColor.GOLD + "reverse: " + ChatColor.DARK_PURPLE + "Reverses " + theTimer + ". When down to 0 the timer stops");
        player.sendMessage(ChatColor.GOLD + "set: " + ChatColor.DARK_PURPLE + "Sets " + theTimer + ": /timer set <days> <hours> <min> <sec>");
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> timerComplete = new ArrayList<>();

        if (args.length == 1) {
            timerComplete.add("resume");
            timerComplete.add("pause");
            timerComplete.add("reset");
            timerComplete.add("set");
            timerComplete.add("start");
            timerComplete.add("stop");
            timerComplete.add("reverse");
            timerComplete.add("help");

        }

        return timerComplete;
    }

}
