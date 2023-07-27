package de.magic_lou.challengespluginv2.challengemanagment.GUI.guis;

import de.magic_lou.challengespluginv2.challengemanagment.GUI.managment.ChallengeManagerGUI;
import de.magic_lou.challengespluginv2.commands.settings.BackPack;
import de.magic_lou.challengespluginv2.commands.settings.InvSeeCommand;
import de.magic_lou.challengespluginv2.commands.settings.SharedHearts;
import de.magic_lou.challengespluginv2.gravitys.*;
import de.magic_lou.challengespluginv2.utils.UtilsGUI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SettingsGUI {

    private final Inventory inv;
    private static final String BACKPAGE = "BackPack";
    private final MainGui mainGUI;
    private final GravityManager gravityManager;
    private final InvSeeCommand invSeeCommand;
    private final SharedHearts sharedHearts;
    private final BackPack backPack;
    private final List<List<ItemStack>> pages = new ArrayList<>();
    private final ChallengeManagerGUI challengeManagerGUI;
    private int page = 0;

    public SettingsGUI(MainGui mainGUI, InvSeeCommand invSeeCommand, GravityManager gravityManager, SharedHearts sharedHearts, BackPack backPack, ChallengeManagerGUI challengeMangerGUI) {
        this.mainGUI = mainGUI;
        this.invSeeCommand = invSeeCommand;
        this.gravityManager = gravityManager;
        this.sharedHearts = sharedHearts;
        this.backPack = backPack;
        this.challengeManagerGUI = challengeMangerGUI;
        inv = Bukkit.createInventory(null, 54, Component.text("Settings-Inventory"));
        UtilsGUI.createOutLine(inv, 0, mainGUI.getMode(), mainGUI.isSpec(), challengeMangerGUI.timer, true);
        createInvItems();
    }

    public Inventory getInv() {
        return inv;
    }



    public void nextPage() {
        if (page < pages.size() - 1) page++;
    }

    public void backPage() {
        if (page > 0) page--;
    }

    public void createInvPages(List<ItemStack> stacks) {
        UtilsGUI.createInvItems(stacks, pages);
    }

    public void setPage() {
        int i = 10;
        for (ItemStack itemStack : pages.get(page)) {
            inv.setItem(i, itemStack);
            i++;
            if (i % 9 == 8) i = i + 2;
            if (i > 43) break;
        }
    }


    public void createInvItems() {

        SelectedEnchantment selectedEnchantment = new SelectedEnchantment();
        List<ItemStack> stacks = new LinkedList<>();


        ItemStack antiGravity = UtilsGUI.createGuiItem(Material.FIREWORK_ROCKET, ChatColor.GOLD + AntiGravity.class.getSimpleName(), "The Sky is the Gravity's center");
        ItemStack hardGravity = UtilsGUI.createGuiItem(Material.ANVIL, ChatColor.GOLD + HardGravity.class.getSimpleName(), "You cant go up");
        ItemStack lowGravity = UtilsGUI.createGuiItem(Material.FEATHER, ChatColor.GOLD + LowGravity.class.getSimpleName(), "The Gravity is not having much impact on you");
        ItemStack noGravity = UtilsGUI.createGuiItem(Material.SNOWBALL, ChatColor.GOLD + NoGravity.class.getSimpleName(), "You are ab e to fly in a certain way");
        ItemStack normalGravity = UtilsGUI.createGuiItem(Material.GRASS_BLOCK, ChatColor.GOLD + NormalGravity.class.getSimpleName(), "Just playing as usual");
        switch (gravityManager.getSelectedGravity().getClass().getSimpleName()) {
            case "AntiGravity" -> antiGravity.addEnchantment(selectedEnchantment, 1);
            case "HardGravity" -> hardGravity.addEnchantment(selectedEnchantment, 1);
            case "LowGravity" -> lowGravity.addEnchantment(selectedEnchantment, 1);
            case "NoGravity" -> noGravity.addEnchantment(selectedEnchantment, 1);
            default -> normalGravity.addEnchantment(selectedEnchantment,1);
        }

        ItemStack gravEffEntity = UtilsGUI.createGuiItem(Material.SALMON_SPAWN_EGG, ChatColor.GOLD + "Effect Entity's", "All Entity's also have this Gravity");
        if (gravityManager.isEffectEntity()) {
            gravEffEntity.setType(Material.ZOMBIE_SPAWN_EGG);
            gravEffEntity.addEnchantment(selectedEnchantment, 1);
        }

        ItemStack invsee = UtilsGUI.createGuiItem(Material.BARREL, ChatColor.GOLD + "InvSee", "Open an other players Inventory");
        if (invSeeCommand.isAllowed()) {
            invsee.addEnchantment(selectedEnchantment, 1);
        }


        ItemStack sHeart = UtilsGUI.createGuiItem(Material.CHAIN, ChatColor.GOLD + "Shared Hearts", "If a Player gets Damage, everybody gets that Damage");
        if (sharedHearts.isRunning()) {
            sHeart.addEnchantment(selectedEnchantment, 1);
        }

        ItemStack backPackItem = UtilsGUI.createGuiItem(Material.RED_SHULKER_BOX, ChatColor.RED + BACKPAGE, "BackPack is disabled");
        if (this.backPack.isRunning()) {
            backPackItem = UtilsGUI.createGuiItem(Material.LIME_SHULKER_BOX, ChatColor.GREEN + BACKPAGE, "BackPack for every Player");
            if (this.backPack.isTogether()) {
                backPackItem = UtilsGUI.createGuiItem(Material.YELLOW_SHULKER_BOX, ChatColor.GOLD + BACKPAGE, "1 Main-BackPack for all");
            }
        }


        stacks.add(antiGravity);
        stacks.add(hardGravity);
        stacks.add(lowGravity);
        stacks.add(noGravity);
        stacks.add(normalGravity);
        stacks.add(gravEffEntity);
        stacks.add(UtilsGUI.empty);
        stacks.add(invsee);
        stacks.add(sHeart);
        stacks.add(backPackItem);


        //Gravity
        //2 empty
        createInvPages(stacks);


    }

    public void createInv(Player player) {
        inv.clear();
        UtilsGUI.createOutLine(inv, 0, mainGUI.getMode(), mainGUI.isSpec(), challengeManagerGUI.timer, true);
        createInvItems();

        setPage();

        openInventory(player);
    }


    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }


    //Settings
    public void setGravity(String gravity) {
        gravityManager.setGravity(gravity);
    }

    public void toggleEffectEntity() {
        gravityManager.toggleEffectEntity();
    }

    public void toggleInvSee() {
        invSeeCommand.toggleOn();
    }

    public void toggleSH() {
        sharedHearts.toggleRunning();
    }

    public void toggleBP() {
        backPack.toggleRunning();
    }

}
