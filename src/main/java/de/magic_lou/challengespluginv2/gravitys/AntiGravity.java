package de.magic_lou.challengespluginv2.gravitys;

import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.utils.Utils;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;

public class AntiGravity extends Gravity implements Listener {

    private final PotionEffect potionEffect = Utils.createPotionEffect(PotionEffectType.LEVITATION, Integer.MAX_VALUE, 3, false, false, false);
    private final Set<LivingEntity> entity = new HashSet<>();

    public AntiGravity(PlayerManager playerManager) {
        super(playerManager);
    }

    @Override
    public void onStart() {
        for (Player player : playerManager.getPlayers()) {
            player.addPotionEffect(potionEffect);
        }


    }

    @Override
    public void onPause() {
        for (Player player : playerManager.getPlayers()) {
            player.removePotionEffect(PotionEffectType.LEVITATION);
        }
        for (LivingEntity entity1 : entity) {
            entity1.removePotionEffect(PotionEffectType.LEVITATION);
        }
    }


    @EventHandler
    public void onEntityMove(EntityMoveEvent event) {
        if (!running) return;
        if (!effectEntity) return;
        event.getEntity().addPotionEffect(potionEffect);
        entity.add(event.getEntity());
    }


}
