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

public class LowGravity extends Gravity implements Listener {

    private final PotionEffect jump = Utils.createPotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 5, false, false, false);
    private final PotionEffect sf = Utils.createPotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 2, false, false, false);

    private final Set<LivingEntity> entity = new HashSet<>();

    public LowGravity(PlayerManager playerManager) {
        super(playerManager);
    }

    @Override
    public void onStart() {
        for (Player player : playerManager.getPlayers()) {
            player.addPotionEffect(jump);
            player.addPotionEffect(sf);
        }
    }

    @Override
    public void onPause() {
        for (Player player : playerManager.getPlayers()) {
            player.removePotionEffect(PotionEffectType.JUMP);
            player.removePotionEffect(PotionEffectType.SLOW_FALLING);
        }
        for (LivingEntity entity1 : entity) {
            entity1.removePotionEffect(PotionEffectType.JUMP);
            entity1.removePotionEffect(PotionEffectType.SLOW_FALLING);
        }
    }


    @EventHandler
    public void onEntityMove(EntityMoveEvent event) {
        if (!running) return;
        if (!effectEntity) return;
        event.getEntity().addPotionEffect(jump);
        event.getEntity().addPotionEffect(sf);
        entity.add(event.getEntity());
    }

}
