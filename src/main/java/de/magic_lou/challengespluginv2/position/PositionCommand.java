package de.magic_lou.challengespluginv2.position;

import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PositionCommand implements TabExecutor {


    private static final String SHARE = "share";
    private static final String REMOVE = "remove";
    private static final String GET_PLAYER = "getPlayer";
    private final PlayerManager playerManager;
    private final PositionManager positionManager;

    public PositionCommand(PlayerManager playerManager, PositionManager positionManager) {
        this.playerManager = playerManager;
        this.positionManager = positionManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (args.length < 1) {
            description((Player) sender);
            return false;
        }


        switch (args[0]) {
            case "list" -> {
                sender.sendMessage("----List----");
                for (String listPo : positionManager.listPos()) {
                    sender.sendMessage(listPo);
                }
            }
            case "get" -> tryGet(args, sender, command, label);
            case SHARE -> {
                if (args.length != 3)
                    sender.sendMessage(ChatColor.GRAY + "/position share " + ChatColor.GREEN + "<playerName>/<all>" + ChatColor.GOLD + " <PositionName>");
                else tryShare(args, sender);
            }

            case "add" -> {
                Player p = (Player) sender;
                positionManager.addPos(args[1], p.getLocation());
                sender.sendMessage(positionManager.getPosition(args[1]) + ChatColor.GRAY + " wurde" + ChatColor.GREEN + " gespeichert");
            }
            case REMOVE -> {
                sender.sendMessage(positionManager.getPosition(args[1]) + " wurde" + ChatColor.RED + " entfernt");
                positionManager.removePos(args[1]);
            }
            case GET_PLAYER -> {
                if (args.length != 2) description((Player) sender);
                if (playerManager.getPlayers().contains(Bukkit.getPlayer(args[1])))
                    sender.sendMessage(Utils.formatLocationName(args[1], ChatColor.GREEN, Objects.requireNonNull(Bukkit.getPlayer(args[1])).getLocation(), ChatColor.GRAY, ChatColor.GOLD));
                else sender.sendMessage("Dieser Spieler ist nicht online");
            }
            default -> description((Player) sender);
        }


        return true;
    }

    private void tryGet(String[] args, CommandSender sender, Command command, String label) {
        if (args.length != 2)
            sender.sendMessage(ChatColor.GRAY + "/position get " + ChatColor.RED + " <positionName>/<all>");
        else {
            if (args[1].equals("all")) {
                args[0] = "list";
                this.onCommand(sender, command, label, args);
            } else sender.sendMessage(positionManager.getPosition(args[1]));
        }
    }

    private void tryShare(String[] args, CommandSender sender) {
        String ret = positionManager.getPosition(args[2]);
        if (ret.contains("ist keine")) {
            sender.sendMessage(ret);
        } else {
            if (args[1].equals("all")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(ret);
                }
            } else {
                Player p = Bukkit.getPlayer(args[1]);
                if (p == null) sender.sendMessage("This Player is not online");
                else {
                    p.sendMessage(ret);
                    sender.sendMessage(ChatColor.GREEN + "Die Position " + ChatColor.DARK_GREEN + args[2] + ChatColor.GREEN + " wurde mit " + ChatColor.DARK_GREEN + p.getName() + ChatColor.GREEN + " geteilt");
                }
            }
        }

    }


    private void description(Player player) {
        player.sendMessage("---------------------");
        player.sendMessage(ChatColor.DARK_PURPLE + "Use Position-Command like this:");
        player.sendMessage(ChatColor.WHITE + "/position " + ChatColor.GOLD + "[action]");
        player.sendMessage("");
        player.sendMessage(ChatColor.GOLD + "[action]");
        player.sendMessage(ChatColor.GOLD + "add: " + ChatColor.DARK_PURPLE + "Adds " + ChatColor.WHITE + "current position as name: " + ChatColor.GREEN + "/position add <name>");
        player.sendMessage(ChatColor.GOLD + REMOVE + ": " + ChatColor.DARK_PURPLE + "Removes " + ChatColor.WHITE + "position: " + ChatColor.GREEN + "/position remove <name>");
        player.sendMessage(ChatColor.GOLD + "get: " + ChatColor.WHITE + "Shows " + ChatColor.DARK_PURPLE + "saved position" + ChatColor.WHITE + ": " + ChatColor.GREEN + "/position get <name>");
        player.sendMessage(ChatColor.GOLD + "list: " + ChatColor.WHITE + "Shows " + ChatColor.DARK_PURPLE + "all saved position" + ChatColor.WHITE + ": " + ChatColor.GREEN + "/position list");
        player.sendMessage(ChatColor.GOLD + SHARE + ": " + ChatColor.WHITE + "Sends " + ChatColor.DARK_PURPLE + "message of position" + ChatColor.WHITE + " to player: " + ChatColor.GREEN + "/position share <player> <name>");
        player.sendMessage(ChatColor.GOLD + GET_PLAYER + ": " + ChatColor.WHITE + "Returns " + ChatColor.DARK_PURPLE + "location of Player" + ChatColor.WHITE + ": " + ChatColor.GREEN + "/position getPlayer <player>");

    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> position = new ArrayList<>();

        if (args.length == 1) {
            position.add(SHARE);
            position.add("list");
            position.add(REMOVE);
            position.add("add");
            position.add("get");
            position.add(GET_PLAYER);
            position.add("help");
        }

        if (args[0].equals(REMOVE) || args[0].equals(SHARE) && args.length == 3 || args[0].equals("get")) {
            for (String name : positionManager.getPos().keySet()) {
                position.add(String.valueOf(name));
            }
        }

        if (args[0].equals(GET_PLAYER)) {
            for (Player player : playerManager.getPlayers()) {
                position.add(player.getName());
            }
        }

        if (args[0].equals(SHARE) && args.length == 2) {
            position.add("all");
            for (Player player : playerManager.getPlayers()) {
                position.add(player.getName());
            }
        }

        return position;
    }

}
