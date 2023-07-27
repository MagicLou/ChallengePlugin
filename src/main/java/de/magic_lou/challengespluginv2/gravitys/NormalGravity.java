package de.magic_lou.challengespluginv2.gravitys;

import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class NormalGravity extends Gravity {
    public NormalGravity(PlayerManager playerManager) {
        super(playerManager);
    }

    @Override
    public void onStart() {
        for (Player player : playerManager.getPlayers()) {
            player.removePotionEffect(PotionEffectType.LEVITATION);
            player.removePotionEffect(PotionEffectType.JUMP);
            player.removePotionEffect(PotionEffectType.SLOW_FALLING);
        }
    }

    @Override
    public void onPause() {
        //Handled by Effects
    }
}
