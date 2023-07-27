package de.magic_lou.challengespluginv2.challengemanagment.GUI;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.challengemanagment.GUI.guis.ChallengeGui;
import de.magic_lou.challengespluginv2.challengemanagment.GUI.guis.MainGui;
import de.magic_lou.challengespluginv2.challengemanagment.GUI.guis.SettingsGUI;
import de.magic_lou.challengespluginv2.challengemanagment.GUI.managment.ChallengeManagerGUI;
import de.magic_lou.challengespluginv2.challengemanagment.GUI.managment.GUIManager;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class InventoryClickListener implements Listener {

    private static final String HARDCORE = "hardcore ";
    private static final String CHALLENGE = "Challenge";
    private final MainGui mainGUI;
    private final ChallengeGui challengeGUI;
    private final ChallengeManagerGUI challengeManagerGUI;
    private final GUIManager guiManager;
    private final SettingsGUI settingsGUI;

    public InventoryClickListener(MainGui mainGUI, ChallengeGui challengeGUI, ChallengeManagerGUI challengeManagerGUI, GUIManager guiManager, SettingsGUI settingsGUI) {
        this.mainGUI = mainGUI;
        this.challengeGUI = challengeGUI;
        this.challengeManagerGUI = challengeManagerGUI;
        this.guiManager = guiManager;
        this.settingsGUI = settingsGUI;
    }


    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(mainGUI.getInv()) && !e.getInventory().equals(challengeGUI.getInv()) && !e.getInventory().equals(settingsGUI.getInv()))
            return;
        e.setCancelled(true);
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().isAir()) return;
        Player player = (Player) e.getWhoClicked();
        TextComponent component = (TextComponent) clickedItem.getItemMeta().displayName();
        String name = "";
        if (component != null)
            name = ChatColor.stripColor(component.content());

        switch (name) {
            case "Next Page" -> {
                if (e.getInventory().equals(settingsGUI.getInv())) {
                    settingsGUI.nextPage();
                    settingsGUI.createInv(player);
                }
                guiManager.nextPage(player);
            }
            case "Page Back" -> {
                if (e.getInventory().equals(settingsGUI.getInv())) {
                    settingsGUI.backPage();
                    settingsGUI.createInv(player);
                }
                guiManager.backPage(player);
            }
            case "Start Timer" -> {
                challengeManagerGUI.start();
                player.closeInventory();
            }
            case "Pause Timer" -> challengeManagerGUI.pause();

            case "Stop Timer" -> {
                challengeManagerGUI.stop();
                guiManager.openInv(player, true);
            }

            case "Revers Timer" -> {
                challengeManagerGUI.timer.reverseTimer();
                guiManager.openInv(player, true);
            }

            case "Set Timer" -> {
                //Settings inv
                player.sendMessage("Coming soon");
                player.closeInventory();
            }
            case "Back" -> {
                mainGUI.setPage(0);
                guiManager.openInv(player, true);
            }
            case "Reset" -> player.performCommand("reset");
            case "Spectate" -> {
                player.performCommand("spectate");
                player.closeInventory();
                mainGUI.setSpec(player.getGameMode().equals(GameMode.SPECTATOR));
            }
            case "Random" -> mainGUI.createRandom(player, challengeManagerGUI.challengeManager);

            case "Force" -> mainGUI.createForce(player, challengeManagerGUI.challengeManager);

            case "Force Battles" -> mainGUI.createForceBattles(player, challengeManagerGUI.challengeManager);

            case "World Generators" -> mainGUI.createGen(player, challengeManagerGUI.challengeManager);

            case "Other" -> mainGUI.createOther(player, challengeManagerGUI.challengeManager);

            case "Overview" -> mainGUI.creatOverview(player, challengeManagerGUI.challengeManager);

            case "Active" -> mainGUI.createActive(player, challengeManagerGUI.challengeManager);


            case "Normal" -> {
                mainGUI.setMode(9);
                player.performCommand(HARDCORE + name.replace(" ", "_").toUpperCase());
                guiManager.openInv(player, false);
            }
            case "Hardcore" -> {
                mainGUI.setMode(18);
                player.performCommand(HARDCORE + name.replace(" ", "_").toUpperCase());
                guiManager.openInv(player, false);
            }
            case "Ultra Hardcore" -> {
                mainGUI.setMode(27);
                player.performCommand(HARDCORE + name.replace(" ", "_").toUpperCase());
                guiManager.openInv(player, false);
            }
            case "Ultra Ultra Hardcore" -> {
                mainGUI.setMode(36);
                player.performCommand(HARDCORE + name.replace(" ", "_").toUpperCase());
                guiManager.openInv(player, false);
            }
            case "Settings" -> {
                try {
                    if (Objects.requireNonNull(e.getInventory().getItem(10)).getType().equals(Material.FIREWORK_ROCKET))
                        guiManager.openInv(player, true);
                    else {
                        settingsGUI.createInv(player);
                    }
                } catch (NullPointerException n) {
                    settingsGUI.createInv(player);
                }
            }


            default -> furtherCheck(name, player, e, clickedItem);
        }
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1f, 1f);

    }

    private void furtherCheck(String name, Player player, InventoryClickEvent e, ItemStack clickedItem) {

        if (name.contains(CHALLENGE)) {
            challengeGUI.createInv(player, challengeManagerGUI.challengeManager.challenges.get(ChatColor.stripColor(name)));
        } else if (e.getInventory().equals(challengeGUI.getInv())) {

            TextComponent challengeComponent = (TextComponent) Objects.requireNonNull(e.getInventory().getItem(13)).getItemMeta().displayName();
            assert challengeComponent != null;
            String challengeName = ChatColor.stripColor(challengeComponent.content());
            challengeName = challengeName.replace("-", "");
            String battleName = challengeName + "Ballte";
            if (!challengeName.endsWith(CHALLENGE)) challengeName = challengeName + CHALLENGE;
            Challenge challenge = challengeManagerGUI.challengeManager.challenges.get(challengeName);

            if (challenge == null) {
                challenge = challengeManagerGUI.challengeManager.challenges.get(battleName);
            }
            if (challenge != null) {
                checkChallenge(name, challenge, challengeName, player, clickedItem);
            }
        }

        openSettings(name, player, e);


    }

    private void checkChallenge(String name, Challenge challenge, String challengeName, Player player, ItemStack clickedItem) {
        boolean open = true;
        switch (name.toLowerCase()) {
            case "add" -> challengeManagerGUI.challengeManager.addChallenge(challengeName);
            case "remove" -> challengeManagerGUI.challengeManager.removeChallenge(challengeName);
            case "easy" -> challenge.setDiff(Challenge.ChallengeDiff.EASY);
            case "default" -> challenge.setDiff(Challenge.ChallengeDiff.NORMAL);
            case "hard" -> challenge.setDiff(Challenge.ChallengeDiff.HARD);
            default -> {
                if (name.startsWith("-")) challenge.description(player);
                else challenge.execMeth(name, player);
                if (!clickedItem.getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
                    open = false;
                    player.closeInventory();
                }
            }
        }
        if (name.equalsIgnoreCase("add") || name.equalsIgnoreCase("remove"))
            mainGUI.reBuild(player, challengeManagerGUI.challengeManager);
        else if (open) challengeGUI.createInv(player, challenge);

    }


    private void openSettings(String name, Player player, InventoryClickEvent e) {
        if (e.getInventory().equals(settingsGUI.getInv())) {
            switch (name) {
                case "AntiGravity", "HardGravity", "LowGravity", "NoGravity", "NormalGravity" ->
                        settingsGUI.setGravity(name.split("Gravity")[0]);
                case "Effect Entity's" -> settingsGUI.toggleEffectEntity();
                case "InvSee" -> settingsGUI.toggleInvSee();
                case "Shared Hearts" -> settingsGUI.toggleSH();
                case "BackPack" -> settingsGUI.toggleBP();
                default -> {
                    mainGUI.setPage(0);
                    guiManager.openInv(player, true);
                }
            }
            settingsGUI.createInv(player);
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (!e.getInventory().equals(mainGUI.getInv()) && !e.getInventory().equals(challengeGUI.getInv()) && !e.getInventory().equals(settingsGUI.getInv()))
            return;
        e.setCancelled(true);

    }


}
