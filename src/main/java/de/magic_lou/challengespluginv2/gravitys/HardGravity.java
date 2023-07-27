package de.magic_lou.challengespluginv2.gravitys;

import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class HardGravity extends Gravity implements Listener {


    public HardGravity(PlayerManager playerManager) {
        super(playerManager);
    }

    @Override
    public void onStart() {
        //Handled by EventHandler
    }

    @Override
    public void onPause() {
        //Handled by EventHandler
    }

    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        if (!running) return;
        if (event.getFrom().getY() < event.getTo().getY()) event.setCancelled(true);
    }

    @EventHandler
    public void onEntityJump(EntityMoveEvent event) {
        if (!running) return;
        if (!effectEntity) return;
        if (event.getFrom().getY() < event.getTo().getY()) event.setCancelled(true);
    }


}
