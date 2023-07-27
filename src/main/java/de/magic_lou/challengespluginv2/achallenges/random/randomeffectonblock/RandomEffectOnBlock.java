package de.magic_lou.challengespluginv2.achallenges.random.randomeffectonblock;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class RandomEffectOnBlock implements Listener {

    private final EnumMap<Material, PotionEffectType> effects = new EnumMap<>(Material.class);
    private final EnumMap<Material, Integer> amplify = new EnumMap<>(Material.class);
    private final Random random = new Random();
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private boolean running = false;

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
        saveShuffle();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private void saveShuffle() {
        List<PotionEffectType> posEffects = Utils.getPotionEffectTypes(Utils.PotionEffectTypes.POSITIV);
        for (Map.Entry<Material, PotionEffectType> entry : effects.entrySet()) {
            if (!posEffects.contains(entry.getValue()))
                effects.put(entry.getKey(), posEffects.get(random.nextInt(posEffects.size())));
        }
    }


    public void shuffle() {
        List<Material> mats = Utils.getMaterialList(Utils.MaterialType.BLOCK);
        List<PotionEffectType> eff = Utils.getPotionEffectTypes(Utils.PotionEffectTypes.ALL);

        for (Material mat : mats) {
            effects.put(mat, eff.get(random.nextInt(eff.size())));
            amplify.put(mat, random.nextInt(5) + 1);
        }

        if (diff.equals(Challenge.ChallengeDiff.EASY)) saveShuffle();

    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!running) return;
        if (event.getFrom().getBlock().equals(event.getTo().getBlock())) return;
        Player player = event.getPlayer();
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
        if (diff.equals(Challenge.ChallengeDiff.HARD)) {
            Utils.effectPlayer(player, Utils.getRandomEffectType(Utils.PotionEffectTypes.ALLE), random.nextInt(5) + 1);
        } else {
            Material mat = player.getLocation().add(0, -1, 0).getBlock().getType();
            if (mat.isAir()) return;

            Utils.effectPlayer(player, effects.get(mat), amplify.get(mat));


        }
    }
}
