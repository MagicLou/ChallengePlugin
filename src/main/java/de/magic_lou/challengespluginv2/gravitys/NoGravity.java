package de.magic_lou.challengespluginv2.gravitys;

import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.utils.Utils;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class NoGravity extends Gravity implements Listener {

    private static final Random random = new Random();

    private final HashMap<Player, Boolean> sneaking = new HashMap<>();
    private final PotionEffect lev = Utils.createPotionEffect(PotionEffectType.LEVITATION, Integer.MAX_VALUE, 2, false, false, false);
    private final PotionEffect sf = Utils.createPotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 2, false, false, false);

    private final Set<LivingEntity> entity = new HashSet<>();

    public NoGravity(PlayerManager playerManager) {
        super(playerManager);
    }


    @Override
    public void onStart() {
        for (Player player : playerManager.getPlayers()) {
            if (!sneaking.containsKey(player)) sneaking.put(player, false);
        }
    }

    @Override
    public void onPause() {
        for (Player player : playerManager.getPlayers()) {
            if (!sneaking.containsKey(player)) sneaking.put(player, false);
            player.removePotionEffect(PotionEffectType.LEVITATION);
            player.removePotionEffect(PotionEffectType.SLOW_FALLING);
        }


        for (LivingEntity entity1 : entity) {
            entity1.removePotionEffect(PotionEffectType.LEVITATION);
            entity1.removePotionEffect(PotionEffectType.SLOW_FALLING);
        }
    }


    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        if (!running) return;
        Player player = event.getPlayer();
        sneaking.replace(player, event.isSneaking());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!running) return;
        Player player = event.getPlayer();
        if (sneaking.containsKey(player)) {
            player.removePotionEffect(PotionEffectType.LEVITATION);
            player.removePotionEffect(PotionEffectType.SLOW_FALLING);
            if (Boolean.TRUE.equals(sneaking.get(player))) player.addPotionEffect(sf);
            else player.addPotionEffect(lev);
        }
    }

    @EventHandler
    public void onEntityMove(EntityMoveEvent event) {
        if (!running) return;
        if (!effectEntity) return;
        boolean active = false;

        if (event.getEntity().getLocation().add(0, -1, 0).getBlock().getType().isAir()) {

            for (PotionEffect effect : event.getEntity().getActivePotionEffects()) {
                if (effect.getType().equals(PotionEffectType.LEVITATION) || effect.getType().equals(PotionEffectType.SLOW_FALLING)) {
                    active = true;
                    break;
                }
            }
        }

        if (!active) {
            event.getEntity().removePotionEffect(PotionEffectType.LEVITATION);
            event.getEntity().removePotionEffect(PotionEffectType.SLOW_FALLING);
            if (random.nextBoolean()) {
                event.getEntity().addPotionEffect(Utils.potionEffect(PotionEffectType.LEVITATION, random.nextInt(15) + 35, 3, false, false, false));
            } else {
                event.getEntity().addPotionEffect(Utils.potionEffect(PotionEffectType.SLOW_FALLING, random.nextInt(35) + 15, 3, false, false, false));
            }
            entity.add(event.getEntity());

        }

    }

}
