package de.magic_lou.challengespluginv2.challengemanagment;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.timer.Timer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChallengeCommand implements TabExecutor {


    private static final String DIE_CHALLENGE = ChatColor.GRAY + "Die Challenge";
    private static final String REMOVE = "remove";
    private static final String METHODS = "methods";

    private final Timer timer;
    private final ChallengeManager challengeManager;

    public ChallengeCommand(Timer timer, ChallengeManager challengeManager) {
        this.timer = timer;
        this.challengeManager = challengeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (args.length != 3 && args.length != 4) {
            description((Player) sender);
            return false;
        }

        final String challengeName = args[2];
        final String challengeDisplayName = ChatColor.GOLD + challengeName;

        if (timer.getTimerValue() != -1 && !args[0].equals(METHODS)) {
            sender.sendMessage(ChatColor.GRAY + "Der" + ChatColor.RED + " Timer" + ChatColor.GRAY + " muss" + ChatColor.RED + " getoppt" + ChatColor.GRAY + " sein um diesen Befehl auszuführen");
            return false;
        }

        if (!challengeManager.challenges.containsKey(challengeName)) {
            sender.sendMessage(DIE_CHALLENGE + " " + challengeDisplayName + ChatColor.RED + " gibt es nicht");
            return false;
        }

        switchCheck(args,challengeName,challengeDisplayName,sender);

        return true;
    }

    private void switchCheck(String[] args,String challengeName,String challengeDisplayName,CommandSender sender){
        switch (args[0]) {
            case "add" -> {
                String diff = "NORMAL";
                if (args.length == 4) {
                    diff = args[3];
                }
                challengeManager.addDiffChallenge(challengeName, Challenge.ChallengeDiff.valueOf(diff));
                sender.sendMessage(DIE_CHALLENGE + " " + challengeDisplayName + ChatColor.GREEN + " wurde hinzugefügt");
            }
            case REMOVE -> {
                challengeManager.removeChallenge(args[1]);
                sender.sendMessage(DIE_CHALLENGE + " " + ChatColor.GOLD + args[2] + ChatColor.RED + " wurde entfernt");
            }
            case METHODS -> {
                int i = 0;
                StringBuilder builder = new StringBuilder();
                for (String s : challengeManager.challengesMeths(challengeName)) {
                    if (i % 2 == 0) {
                        builder.append(ChatColor.GREEN).append(s).append(ChatColor.GRAY).append(": ");
                    } else {
                        builder.append(ChatColor.GRAY).append(s);
                        sender.sendMessage(builder.toString());
                        builder.delete(0, builder.length());
                    }
                    i++;

                }
            }
            case "description" -> {
                for (String s : challengeManager.challengeDescription(challengeName)) {
                    sender.sendMessage(s);
                }
            }
            default -> description((Player) sender);
        }
    }

    public void description(Player player) {
        String sc = ChatColor.WHITE + "selected Challenge";
        String osc = ChatColor.WHITE + "of " + sc;
        player.sendMessage("---------------------");
        player.sendMessage(ChatColor.DARK_PURPLE + "Use Challenge-Command like this:");
        player.sendMessage(ChatColor.WHITE + "/challenge " + ChatColor.GOLD + "[action] " + ChatColor.DARK_GREEN + "[challengePackage] " + ChatColor.GREEN + "[Challenge] " + ChatColor.GRAY + "[diffs]");
        player.sendMessage("");
        player.sendMessage(ChatColor.GOLD + "[action]");
        player.sendMessage(ChatColor.GOLD + "add: " + ChatColor.DARK_PURPLE + "Adds " + sc);
        player.sendMessage(ChatColor.GOLD + "remove: " + ChatColor.DARK_PURPLE + "Removes " + sc);
        player.sendMessage(ChatColor.GOLD + "meths: " + ChatColor.DARK_PURPLE + "Method's " + osc);
        player.sendMessage(ChatColor.GOLD + "description: " + ChatColor.DARK_PURPLE + "Description " + osc);
        player.sendMessage("");
        String c = ChatColor.WHITE + "Challenges";
        player.sendMessage(ChatColor.DARK_GREEN + "[challengePackage]");
        player.sendMessage(ChatColor.DARK_GREEN + "Force: " + ChatColor.DARK_PURPLE + "Force " + c);
        player.sendMessage(ChatColor.DARK_GREEN + "Random: " + ChatColor.DARK_PURPLE + "Random " + c);
        player.sendMessage(ChatColor.DARK_GREEN + "Other: " + ChatColor.DARK_PURPLE + "Other " + c);
        player.sendMessage(ChatColor.DARK_GREEN + "NotWorking: " + ChatColor.DARK_PURPLE + "Not working " + c);
        player.sendMessage(ChatColor.DARK_GREEN + "Overview: " + ChatColor.DARK_PURPLE + "All " + c);
        player.sendMessage("");
        player.sendMessage(ChatColor.GREEN + "[Challenge]");
        player.sendMessage(ChatColor.DARK_PURPLE + "Name " + ChatColor.WHITE + " of to select Challenge");
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "[diffs]");
        player.sendMessage(ChatColor.WHITE + "If Challenge gets added, you can choose a " + ChatColor.DARK_PURPLE + "difficulty" + ChatColor.WHITE + ". If left out it gets " + ChatColor.GOLD + "'normal'");

    }


    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        switch (args.length) {
            case 1 -> {
                return Arrays.asList("add", REMOVE, METHODS, "description", "help");
            }
            case 2 -> {
                if (args[0].equals(REMOVE)) {
                    List<String> ret = new ArrayList<>();
                    for (Challenge challenge : challengeManager.aktiveChallenges) {
                        ret.add(challenge.getClass().getSimpleName());
                    }
                    return ret;
                } else return Arrays.asList("Force", "Random", "Other", "Overview", "NotWorking");
            }
            case 3 -> {
                switch (args[1]) {
                    case "Force" -> {
                        return new ArrayList<>(challengeManager.cForce.keySet());
                    }
                    case "Random" -> {
                        return new ArrayList<>(challengeManager.cRandom.keySet());
                    }
                    case "Other" -> {
                        return new ArrayList<>(challengeManager.cOther.keySet());
                    }
                    case "Overview" -> {
                        return new ArrayList<>(challengeManager.challenges.keySet());
                    }
                    case "NotWorking" -> {
                        return new ArrayList<>(challengeManager.cNotWorking.keySet());
                    }
                    default -> {
                        sender.sendMessage("Something went wrong! Please try again");
                        return Collections.emptyList();
                    }
                }
            }
            case 4 -> {
                List<String> ret = new ArrayList<>();
                if (args[0].equals("add")) {
                    for (Challenge.ChallengeDiff diff : challengeManager.challenges.get(args[2]).getDiffs()) {
                        ret.add(diff.toString());
                    }
                }
                return ret;
            }
            default -> {
                sender.sendMessage("Something went wrong! Please try again");
                return Collections.emptyList();
            }
        }
    }

}
