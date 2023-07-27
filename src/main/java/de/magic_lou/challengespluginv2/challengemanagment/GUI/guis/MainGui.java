package de.magic_lou.challengespluginv2.challengemanagment.GUI.guis;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import de.magic_lou.challengespluginv2.challengemanagment.GUI.managment.ChallengeManagerGUI;
import de.magic_lou.challengespluginv2.utils.UtilsGUI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MainGui {

    private final Inventory inv;
    private final SelectedEnchantment selectedEnchantment;
    private final ChallengeManagerGUI challengeMangerGUI;
    private int cat = 52;

    public int getCat() {
        return cat;
    }

    public int getMode() {
        return mode;
    }

    public boolean isSpec() {
        return spec;
    }



    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setSpec(boolean spec) {
        this.spec = spec;
    }

    private int mode = 9;
    private boolean spec = false;
    private String reBuilder = "ov";

    private final List<List<ItemStack>> pages = new ArrayList<>();
    private int page = 0;

    public MainGui(ChallengeManagerGUI challengeManger) {
        this.challengeMangerGUI = challengeManger;

        inv = Bukkit.createInventory(null, 54, Component.text("Challenge-Inventory"));
        UtilsGUI.createOutLine(inv, cat, mode, spec, challengeMangerGUI.timer, false);
        selectedEnchantment = new SelectedEnchantment();
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



    public void setPage(int page) {
        this.page = page;
    }

    public void createInvItems(List<ItemStack> stacks) {
        UtilsGUI.createInvItems(stacks, pages);

    }

    public void createInv(Player player) {
        int i = 10;
        inv.clear();
        UtilsGUI.createOutLine(inv, cat, mode, spec, challengeMangerGUI.timer, false);

        for (ItemStack itemStack : pages.get(page)) {
            inv.setItem(i, itemStack);
            i++;
            if (i % 9 == 8) i = i + 2;
            if (i > 43) break;
        }

        openInventory(player);
    }

    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }


    public void reBuild(Player player, ChallengeManager challengeManager) {
        switch (reBuilder) {
            case "act" -> createActive(player, challengeManager);
            case "oth" -> createOther(player, challengeManager);
            case "frb" -> createForceBattles(player, challengeManager);
            case "for" -> createForce(player, challengeManager);
            case "ran" -> createRandom(player, challengeManager);
            case "gen" -> createGen(player, challengeManager);
            default -> creatOverview(player, challengeManager);

        }
    }


    public void createActive(Player player, ChallengeManager challengeManager) {
        List<ItemStack> all = new ArrayList<>();
        cat = 46;
        reBuilder = "act";
        for (Challenge challenge : challengeManager.aktiveChallenges) {
            all.add(UtilsGUI.createGuiItem(challenge.getItem(), challenge.getClass().getSimpleName(), ""));
            if (challengeManager.aktiveChallenges.contains(challenge))
                all.get(all.size() - 1).addEnchantment(selectedEnchantment, 1);
        }
        createInvItems(all);
        createInv(player);
    }

    public void createRandom(Player player, ChallengeManager challengeManager) {
        List<ItemStack> random = new ArrayList<>();
        cat = 47;
        reBuilder = "ran";
        for (Challenge challenge : challengeManager.cRandom.values()) {
            random.add(UtilsGUI.createGuiItem(challenge.getItem(), challenge.getClass().getSimpleName(), ""));
            if (challengeManager.aktiveChallenges.contains(challenge))
                random.get(random.size() - 1).addEnchantment(selectedEnchantment, 1);
        }
        createInvItems(random);
        createInv(player);
    }

    public void createForce(Player player, ChallengeManager challengeManager) {
        List<ItemStack> force = new ArrayList<>();
        cat = 48;
        reBuilder = "for";
        for (Challenge challenge : challengeManager.cForce.values()) {
            force.add(UtilsGUI.createGuiItem(challenge.getItem(), challenge.getClass().getSimpleName(), ""));
            if (challengeManager.aktiveChallenges.contains(challenge)) {
                force.get(force.size() - 1).addEnchantment(selectedEnchantment, 1);
            }
        }
        createInvItems(force);
        createInv(player);
    }

    public void createForceBattles(Player player, ChallengeManager challengeManager) {
        List<ItemStack> stacks = new ArrayList<>();
        cat = 49;
        reBuilder = "frb";
        for (Challenge challenge : challengeManager.cBattle.values()) {
            stacks.add(UtilsGUI.createGuiItem(challenge.getItem(), challenge.getClass().getSimpleName(), ""));
            if (challengeManager.aktiveChallenges.contains(challenge))
                stacks.get(stacks.size() - 1).addEnchantment(selectedEnchantment, 1);
        }
        createInvItems(stacks);
        createInv(player);
    }

    public void createGen(Player player, ChallengeManager challengeManager) {
        List<ItemStack> gens = new ArrayList<>();
        cat = 50;
        reBuilder = "gen";
        for (Challenge challenge : challengeManager.cGen.values()) {
            gens.add(UtilsGUI.createGuiItem(challenge.getItem(), challenge.getClass().getSimpleName(), ""));
            if (challengeManager.aktiveChallenges.contains(challenge)) {
                gens.get(gens.size() - 1).addEnchantment(selectedEnchantment, 1);
            }
        }
        createInvItems(gens);
        createInv(player);
    }

    public void createOther(Player player, ChallengeManager challengeManager) {
        List<ItemStack> other = new ArrayList<>();
        cat = 51;
        reBuilder = "oth";
        for (Challenge challenge : challengeManager.cOther.values()) {
            other.add(UtilsGUI.createGuiItem(challenge.getItem(), challenge.getClass().getSimpleName(), ""));
            if (challengeManager.aktiveChallenges.contains(challenge)) {
                other.get(other.size() - 1).addEnchantment(selectedEnchantment, 1);
            }
        }
        createInvItems(other);
        createInv(player);
    }

    public void creatOverview(Player player, ChallengeManager challengeManager) {
        List<ItemStack> all = new ArrayList<>();
        cat = 52;
        reBuilder = "ov";
        for (Challenge challenge : challengeManager.challenges.values()) {
            all.add(UtilsGUI.createGuiItem(challenge.getItem(), challenge.getClass().getSimpleName(), ""));
            if (challengeManager.aktiveChallenges.contains(challenge))
                all.get(all.size() - 1).addEnchantment(selectedEnchantment, 1);
        }
        createInvItems(all);
        createInv(player);
    }


}
