package de.magic_lou.challengespluginv2.achallenges.random.randomeffectondamage;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Random;

public class RandomEffectOnDamage implements Listener {

    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private static final Random random = new Random();
    private boolean running = false;

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }


    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!running) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getCause().equals(EntityDamageEvent.DamageCause.POISON) || event.getCause().equals(EntityDamageEvent.DamageCause.MAGIC))
            return;


        Player player = ((Player) event.getEntity()).getPlayer();
        List<PotionEffectType> types = switch (diff) {
            case EASY -> Utils.getPotionEffectTypes(Utils.PotionEffectTypes.POSITIV);
            case NORMAL -> Utils.getPotionEffectTypes(Utils.PotionEffectTypes.SURVIVE);
            case HARD -> Utils.getPotionEffectTypes(Utils.PotionEffectTypes.ALL);
        };
        assert player != null;
        Utils.effectAddPlayer(player, types.get(random.nextInt(types.size())));

    }


}
