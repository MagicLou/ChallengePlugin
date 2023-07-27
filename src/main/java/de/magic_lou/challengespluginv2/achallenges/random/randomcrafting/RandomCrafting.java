package de.magic_lou.challengespluginv2.achallenges.random.randomcrafting;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RandomCrafting implements Listener {

    //KP wie ich das ohne deprecation lösen soll

    private List<Material> items = Utils.getMaterialList(Utils.MaterialType.ITEM);
    private boolean running = false;
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        if (!diff.equals(Challenge.ChallengeDiff.NORMAL) && !diff.equals(Challenge.ChallengeDiff.HARD))
            diff = Challenge.ChallengeDiff.NORMAL;
        this.diff = diff;
    }

    private Recipe replaceItem(Recipe recipe, ItemStack item) {

        while (item.getType().isAir()) {
            item = new ItemStack(Utils.getRandomMaterial(Utils.MaterialType.ITEM));
        }

        if (recipe instanceof ShapelessRecipe sRecipe) {
            ShapelessRecipe returnRecipe = new ShapelessRecipe(sRecipe.getKey(), item);
            for (RecipeChoice choice : sRecipe.getChoiceList()) { //here
                returnRecipe.addIngredient(choice);
            }
            returnRecipe.setGroup(sRecipe.getGroup());
            return returnRecipe;
        } else if (recipe instanceof ShapedRecipe shapedRecipe) {
            ShapedRecipe returnRecipe = new ShapedRecipe(shapedRecipe.getKey(), item);
            returnRecipe.shape(shapedRecipe.getShape());
            returnRecipe.setGroup(shapedRecipe.getGroup());
            shapedRecipe.getChoiceMap().forEach((key, value) -> { //here
                if (value == null) return;
                returnRecipe.setIngredient(key, value);
            });
            return returnRecipe;
        } else if (recipe instanceof BlastingRecipe blastingRecipe) {
            BlastingRecipe returnRecipe = new BlastingRecipe(blastingRecipe.getKey(), item, blastingRecipe.getInputChoice(), blastingRecipe.getExperience(), blastingRecipe.getCookingTime());//here
            returnRecipe.setGroup(blastingRecipe.getGroup());
            returnRecipe.setInputChoice(returnRecipe.getInputChoice());//here
            return returnRecipe;
        } else if (recipe instanceof CampfireRecipe campfireRecipe) {
            CampfireRecipe returnRecipe = new CampfireRecipe(campfireRecipe.getKey(), item, campfireRecipe.getInputChoice(), campfireRecipe.getExperience(), campfireRecipe.getCookingTime());//here
            returnRecipe.setGroup(campfireRecipe.getGroup());
            returnRecipe.setInputChoice(campfireRecipe.getInputChoice());//here
            return returnRecipe;
        } else if (recipe instanceof FurnaceRecipe furnaceRecipe) {
            FurnaceRecipe returnRecipe = new FurnaceRecipe(furnaceRecipe.getKey(), item, furnaceRecipe.getInputChoice(), furnaceRecipe.getExperience(), furnaceRecipe.getCookingTime());//here
            returnRecipe.setGroup(furnaceRecipe.getGroup());
            returnRecipe.setInputChoice(furnaceRecipe.getInputChoice());//here
            return returnRecipe;
        } else if (recipe instanceof MerchantRecipe merchantRecipe) {
            MerchantRecipe returnRecipe = new MerchantRecipe(item, merchantRecipe.getUses(), merchantRecipe.getMaxUses(), merchantRecipe.hasExperienceReward(), merchantRecipe.getVillagerExperience(), merchantRecipe.getPriceMultiplier(), merchantRecipe.getDemand(), merchantRecipe.getSpecialPrice());
            returnRecipe.setIngredients(merchantRecipe.getIngredients());
            return returnRecipe;
        } else if (recipe instanceof SmithingRecipe smithingRecipe) {
            return new SmithingRecipe(smithingRecipe.getKey(), item, smithingRecipe.getBase(), smithingRecipe.getAddition());
        } else if (recipe instanceof SmokingRecipe smokingRecipe) {
            SmokingRecipe returnRecipe = new SmokingRecipe(smokingRecipe.getKey(), item, smokingRecipe.getInputChoice(), smokingRecipe.getExperience(), smokingRecipe.getCookingTime());//here
            returnRecipe.setGroup(smokingRecipe.getGroup());
            return returnRecipe;
        } else if (recipe instanceof StonecuttingRecipe stonecuttingRecipe) {
            StonecuttingRecipe returnRecipe = new StonecuttingRecipe(stonecuttingRecipe.getKey(), item, stonecuttingRecipe.getInputChoice());//here
            returnRecipe.setGroup(stonecuttingRecipe.getGroup());
            returnRecipe.setInputChoice(stonecuttingRecipe.getInputChoice());//here
            return returnRecipe;
        }

        return null;
    }

    public void shuffle() {
        Collections.shuffle(items);
        for (Iterator<Recipe> it = Bukkit.getServer().recipeIterator(); it.hasNext(); ) {
            Recipe recipe = it.next();
            if (items.isEmpty()) {
                items = Utils.getMaterialList(Utils.MaterialType.ITEM);
            }
            Bukkit.removeRecipe(((Keyed) recipe).getKey());
            recipe = replaceItem(recipe, new ItemStack(items.get(0)));
            Bukkit.addRecipe(recipe);
            items.remove(0);
        }
    }

    public void start() {
        running = true;
        shuffle();
    }

    public void pause() {
        running = false;
    }

    public void resume() {
        running = true;
    }

    public void reset() {
        stop();
        start();
    }

    public void stop() {
        running = false;
        Bukkit.getServer().resetRecipes();
    }


    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (!running) return;
        if (diff.equals(Challenge.ChallengeDiff.HARD)) shuffle();
    }


}
