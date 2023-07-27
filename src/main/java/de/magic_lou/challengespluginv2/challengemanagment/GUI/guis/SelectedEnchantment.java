package de.magic_lou.challengespluginv2.challengemanagment.GUI.guis;

import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class SelectedEnchantment extends Enchantment {

    public SelectedEnchantment() {
        super(NamespacedKey.randomKey());
    }

    @Override
    public boolean canEnchantItem(ItemStack arg0) {
        return true;
    }

    @Override
    public @NotNull Component displayName(int i) {
        return null;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return null;
    }

    @Override
    public float getDamageIncrease(int i, @NotNull EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public @NotNull Set<EquipmentSlot> getActiveSlots() {
        return null;
    }

    @Override
    public boolean conflictsWith(Enchantment arg0) {
        return false;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }


    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 10;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public @NotNull String translationKey() {
        return null;
    }
}
