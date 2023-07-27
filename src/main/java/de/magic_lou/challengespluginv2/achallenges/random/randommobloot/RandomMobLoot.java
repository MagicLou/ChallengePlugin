package de.magic_lou.challengespluginv2.achallenges.random.randommobloot;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

public class RandomMobLoot implements Listener {

    private static final Random random = new Random();
    private final EnumMap<EntityType, EntityType> entitys = new EnumMap<>(EntityType.class);
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private boolean running = false;
    private boolean print = true;

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void shuffle() {
        List<EntityType> typs = Utils.getEntityTypes(Utils.EntityTypeCats.ALL);
        List<EntityType> loot = Utils.getEntityTypes(Utils.EntityTypeCats.LIVING);
        Collections.shuffle(loot);
        for (EntityType typ : typs) {
            EntityType check;
            do {
                check = loot.get(random.nextInt(loot.size() - 1));
            } while (check.equals(EntityType.ENDER_DRAGON));

            entitys.put(typ, check);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!running) return;
        if (event.getEntity().getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.COMMAND)) {
            event.setDroppedExp(0);
            event.setShouldPlayDeathSound(false);
            return;
        }


        if (!diff.equals(Challenge.ChallengeDiff.EASY)) event.getDrops().clear();
        EntityType type;
        if (diff.equals(Challenge.ChallengeDiff.NORMAL)) type = entitys.get(event.getEntityType());
        else {

            type = entitys.get(Utils.getRandomEntityType(Utils.EntityTypeCats.LIVING));
        }

        Entity entity = event.getEntity().getLocation().getWorld().spawnEntity(event.getEntity().getLocation(), type, CreatureSpawnEvent.SpawnReason.COMMAND);
        ((LivingEntity) entity).setInvisible(true);
        entity.setSilent(true);
        entity.setLastDamageCause(event.getEntity().getLastDamageCause());
        if (entity instanceof Slime slime) {
            slime.setSize(1);
        }
        if (entity instanceof MagmaCube slime) {
            slime.setSize(1);
        }
        try {
            entity.setFireTicks(event.getEntity().getFireTicks());
            ((LivingEntity) entity).setKiller(event.getEntity().getKiller());
            ((Damageable) entity).damage(((Damageable) entity).getHealth());//here

        } catch (Exception ignored) {
            //Ignoring Exception
        }


        if (print) {

            Player player = event.getEntity().getKiller();
            @NotNull EntityType killed = event.getEntityType();
            EntityType got = entity.getType();
            if (player != null)
                player.sendMessage(ChatColor.GREEN + killed.name() + ChatColor.WHITE + " -> " + ChatColor.RED + got.name());

        }

    }


    public void print(Player player) {
        print = !print;
        if (print) {
            player.sendMessage("Es wird nun ausgegeben welches Mob sich hinter dem getöteten verbirgt");
        } else {
            player.sendMessage("Es wird nun nicht mehr ausgegeben welches Mob sich hinter dem getöteten verbirgt");
        }
    }
}
