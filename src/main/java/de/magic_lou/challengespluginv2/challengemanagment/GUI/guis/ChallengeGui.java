package de.magic_lou.challengespluginv2.challengemanagment.GUI.guis;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.challengemanagment.GUI.managment.ChallengeManagerGUI;
import de.magic_lou.challengespluginv2.utils.UtilsGUI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ChallengeGui {

    private final MainGui mainGUI;
    private final ChallengeManagerGUI challengeManagerGUI;
    private final Inventory inv;


    public ChallengeGui(MainGui mainGUI, ChallengeManagerGUI challengeMangerGUI) {
        this.mainGUI = mainGUI;
        this.challengeManagerGUI = challengeMangerGUI;
        inv = Bukkit.createInventory(null, 54, Component.text("Challenge-Inventory"));
    }

    public Inventory getInv() {
        return inv;
    }

    public void createInvItems(Challenge challenge) {

        SelectedEnchantment selectedEnchantment = new SelectedEnchantment();

        List<String> desc = challenge.getDescription();


        ItemStack description = UtilsGUI.createGuiItem(Material.BOOK, desc.get(0), desc.get(1));
        //13 description

        ItemStack easy = UtilsGUI.empty;
        ItemStack normal = UtilsGUI.empty;
        ItemStack hard = UtilsGUI.empty;

        for (String s : desc) {
            s = ChatColor.stripColor(s);
            if (s.split(":")[0].equals("EASY")) {
                easy = UtilsGUI.createGuiItem(Material.WOODEN_SWORD, ChatColor.GREEN + "Easy", s.split(":")[1].replaceFirst(" ", ""));
            }
            if (s.split(":")[0].equals("NORMAL")) {
                normal = UtilsGUI.createGuiItem(Material.IRON_SWORD, ChatColor.GOLD + "Default", s.split(":")[1].replaceFirst(" ", ""));
            }
            if (s.split(":")[0].equals("HARD")) {
                hard = UtilsGUI.createGuiItem(Material.NETHERITE_SWORD, ChatColor.DARK_RED + "Hard", s.split(":")[1].replaceFirst(" ", ""));
            }
        }
        //21-23 diffs


        ItemStack add = UtilsGUI.createGuiItem(Material.LIME_WOOL, ChatColor.GREEN + "Add", "Add Challenge");
        //39 add
        ItemStack remove = UtilsGUI.createGuiItem(Material.RED_WOOL, ChatColor.DARK_RED + "Remove", "Remove Challenge");
        //41 remove
        switch (challenge.getDiff()) {
            case EASY -> easy.addEnchantment(selectedEnchantment, 1);
            case NORMAL -> normal.addEnchantment(selectedEnchantment, 1);
            case HARD -> hard.addEnchantment(selectedEnchantment, 1);
        }


        inv.setItem(39, add);
        inv.setItem(41, remove);
        inv.setItem(13, description);
        inv.setItem(21, easy);
        inv.setItem(22, normal);
        inv.setItem(23, hard);

        int p = 28;

        for (int i = 0; i < challenge.getMethods().size(); i = i + 2) {
            inv.setItem(p, UtilsGUI.createGuiItem(Material.LEVER, challenge.getMethods().get(i), challenge.getMethods().get(i + 1)));
            p++;
            if (p > 34) {
                Bukkit.broadcast(Component.text("Bruder was hast du gemacht"));
                break;
            }
        }


    }

    public void createInv(Player player, Challenge challenge) {
        inv.clear();
        UtilsGUI.createOutLine(inv, mainGUI.getCat(), mainGUI.getMode(), mainGUI.isSpec(), challengeManagerGUI.timer, false);
        createInvItems(challenge);

        openInventory(player);
    }


    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

}
