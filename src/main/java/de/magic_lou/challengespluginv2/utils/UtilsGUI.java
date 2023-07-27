package de.magic_lou.challengespluginv2.utils;

import de.magic_lou.challengespluginv2.challengemanagment.GUI.guis.SelectedEnchantment;
import de.magic_lou.challengespluginv2.timer.Timer;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UtilsGUI {


    public static final ItemStack emptyOut = createGuiItem(Material.WHITE_STAINED_GLASS_PANE, "", "");
    public static final ItemStack empty = createGuiItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "", "");
    private UtilsGUI() {
        throw new IllegalStateException("Utility class");
    }

    public static ItemStack createGuiItem(Material material, String name, String lore) {


        List<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append(ChatColor.GRAY);
        int i = 0;

        for (String s : lore.split(" ")) {
            builder.append(ChatColor.GRAY).append(s).append(" ");
            i++;
            if (i > 4) {
                list.add(ChatColor.GRAY + builder.toString());
                builder.delete(0, builder.length());
                i = 0;
            }

        }
        list.add(ChatColor.GRAY + builder.toString());

        if (material == null) material = Material.GREEN_STAINED_GLASS_PANE;
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name));
        meta.setLore(list);
        item.setItemMeta(meta);
        return item;
    }

    public static void createOutLine(Inventory inv, int cat, int mode, boolean spec, Timer timer, boolean sett) {
        SelectedEnchantment selectedEnchantment = new SelectedEnchantment();
        int i = 0;
        inv.clear();
        for (ItemStack stack : creatTimerLine(timer)) {
            inv.setItem(i, stack);
            i++;
        }
        for (ItemStack stack : createMode()) {
            inv.setItem(i, stack);
            if (i == mode) inv.getItem(i).setType(Material.ZOMBIE_HEAD);
            i = i + 9;
        }
        for (ItemStack stack : createCats()) {
            inv.setItem(i, stack);
            if (i == cat) inv.getItem(i).addEnchantment(selectedEnchantment, 1);
            i++;
        }
        i--;
        i -= 9;
        for (ItemStack stack : createRight()) {
            inv.setItem(i, stack);
            if (spec && ChatColor.stripColor(stack.getItemMeta().getDisplayName()).contains("Spectate"))
                inv.getItem(i).addEnchantment(selectedEnchantment, 1);
            if (sett && ChatColor.stripColor(stack.getItemMeta().getDisplayName()).contains("Settings"))
                inv.getItem(i).addEnchantment(selectedEnchantment, 1);
            i = i - 9;
        }
    }

    public static List<ItemStack> creatTimerLine(Timer timer) {

        SelectedEnchantment glowing = new SelectedEnchantment();

        ItemStack backPage = createGuiItem(Material.ARROW, ChatColor.WHITE + "Page Back", "");
        ItemStack nextPage = createGuiItem(Material.ARROW, ChatColor.WHITE + "Next Page", "");

        ItemStack setTimer = createGuiItem(Material.REDSTONE, ChatColor.RED + "Set Timer", "Set Timer to certain Value");
        ItemStack startTimer = createGuiItem(Material.LIME_CONCRETE, ChatColor.GREEN + "Start Timer", "Start or resume the Challenge");
        ItemStack pauseTimer = createGuiItem(Material.YELLOW_CONCRETE, ChatColor.GOLD + "Pause Timer", "Pause the Challenge");
        ItemStack stopTimer = createGuiItem(Material.RED_CONCRETE, ChatColor.DARK_RED + "Stop Timer", "Stop Challenge");
        ItemStack reversTimer = createGuiItem(Material.COMPARATOR, ChatColor.DARK_AQUA + "Revers Timer", "Timer counting down when enabled");
        if (timer.isReverse()) reversTimer.addEnchantment(glowing, 1);

        return getItemStacks(emptyOut, nextPage, setTimer, startTimer, pauseTimer, stopTimer, reversTimer, backPage, emptyOut);
    }


    public static List<ItemStack> createMode() {
        List<ItemStack> itemStacks = new ArrayList<>();


        ItemStack normal = createGuiItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Normal", "Normal playing");
        ItemStack hardcore = createGuiItem(Material.SKELETON_SKULL, ChatColor.GOLD + "Hardcore", "Resetting World after death");
        ItemStack uHardcore = createGuiItem(Material.WITHER_SKELETON_SKULL, ChatColor.RED + "Ultra Hardcore", "Only regeneration from potions and golden apples");
        ItemStack uUHardcore = createGuiItem(Material.DRAGON_HEAD, ChatColor.DARK_RED + "Ultra Ultra Hardcore", "No regeneration");

        itemStacks.add(normal);
        itemStacks.add(hardcore);
        itemStacks.add(uHardcore);
        itemStacks.add(uUHardcore);

        return itemStacks;
    }

    public static List<ItemStack> createCats() {

        ItemStack aktivChallenges = createGuiItem(Material.SHROOMLIGHT, ChatColor.GOLD + "Active", "Active Challenges");
        ItemStack random = createGuiItem(Material.WOODEN_PICKAXE, ChatColor.GOLD + "Random", "Random Challenges");
        ItemStack force = createGuiItem(Material.COMPASS, ChatColor.GOLD + "Force", "Force Challenges");
        ItemStack battles = createGuiItem(Material.RECOVERY_COMPASS, ChatColor.GOLD + "Force Battles", "Battles");
        ItemStack generator = createGuiItem(Material.GRASS_BLOCK, ChatColor.GOLD + "World Generators", "Different WorldGeneration");
        ItemStack other = createGuiItem(Material.SHEARS, ChatColor.GOLD + "Other", "Other Challenges");
        ItemStack all = createGuiItem(Material.ENDER_PEARL, ChatColor.GOLD + "Overview", "All Challenges");

        return getItemStacks(emptyOut, aktivChallenges, random, force, battles, generator, other, all, emptyOut);
    }

    public static List<ItemStack> createRight() {
        List<ItemStack> itemStacks = new ArrayList<>();

        ItemStack reset = createGuiItem(Material.BARRIER, ChatColor.DARK_RED + "Reset", "Worldreset");
        ItemStack back = createGuiItem(Material.RABBIT_FOOT, ChatColor.RED + "Back", "Go back to menu");
        ItemStack spectate = createGuiItem(Material.ENDER_EYE, ChatColor.GOLD + "Spectate", "Toggle spectating");
        ItemStack settings = createGuiItem(Material.REPEATER, ChatColor.DARK_PURPLE + "Settings", "Go to advanced Settings");

        itemStacks.add(back);
        itemStacks.add(spectate);
        itemStacks.add(reset);
        itemStacks.add(emptyOut);//help
        itemStacks.add(settings);


        return itemStacks;
    }

    @NotNull
    private static List<ItemStack> getItemStacks(ItemStack slot1, ItemStack slot2, ItemStack slot3, ItemStack slot4, ItemStack slot5, ItemStack slot6, ItemStack slot7, ItemStack slot8, ItemStack slot9) {
        List<ItemStack> itemStacks = new ArrayList<>();
        itemStacks.add(slot1);
        itemStacks.add(slot2);
        itemStacks.add(slot3);
        itemStacks.add(slot4);
        itemStacks.add(slot5);
        itemStacks.add(slot6);
        itemStacks.add(slot7);
        itemStacks.add(slot8);
        itemStacks.add(slot9);

        return itemStacks;
    }


    public static void createInvItems(List<ItemStack> stacks, List<List<ItemStack>> pages) {
        pages.clear();
        int anz = 0;
        List<ItemStack> list = new ArrayList<>();
        for (ItemStack stack : stacks) {
            list.add(stack);
            anz++;
            if (anz > 28) {
                anz = 0;
                pages.add(new ArrayList<>(list));
                list.clear();
            }
        }
        pages.add(new ArrayList<>(list));
        list.clear();
    }

}
