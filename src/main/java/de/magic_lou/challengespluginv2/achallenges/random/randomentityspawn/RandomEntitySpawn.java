package de.magic_lou.challengespluginv2.achallenges.random.randomentityspawn;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.timer.Timer;
import de.magic_lou.challengespluginv2.utils.Utils;
import io.papermc.paper.event.entity.EntityMoveEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class RandomEntitySpawn implements Listener {

    private final de.magic_lou.challengespluginv2.timer.Timer timer;
    private final Set<Chunk> chunks = new HashSet<>();
    private final PlayerManager playerManager;
    private final List<EntityType> alive = Utils.getEntityTypes(Utils.EntityTypeCats.LIVING);
    private EnumMap<EntityType, EntityType> entityTypes = new EnumMap<>(EntityType.class);
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private boolean running = false;
    private Entity theEnderDragon = null;
    private BossBar bossBar;

    public RandomEntitySpawn(Timer timer, PlayerManager playerManager) {
        this.timer = timer;
        this.playerManager = playerManager;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
    }

    public void shuffle() {
        entityTypes = Utils.setEntityTypeHashMap();
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (!running||event.getEntity().getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)||!alive.contains(event.getEntity().getType())) return;
        if (!entityTypes.get(EntityType.ENDER_DRAGON).isAlive()) entityTypes.put(EntityType.ENDER_DRAGON, Utils.getRandomEntityType(Utils.EntityTypeCats.LIVING));

        if (chunks.contains(event.getEntity().getChunk())) {
            for (Entity entity : event.getEntity().getChunk().getEntities()) {
                if (entity.getType().equals(entityTypes.get(event.getEntity().getType()))) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
        if (event.getEntityType().equals(EntityType.ENDER_DRAGON)) {
            for (Entity entity : event.getLocation().getWorld().getEntities()) {
                if (entity.getType().equals(entityTypes.get(EntityType.ENDER_DRAGON))) {
                    return;
                }
            }
        }
        event.setCancelled(true);
        spawn(event.getEntity().getLocation(), event.getEntity().getType());
    }

    @EventHandler
    public void onEntityMove(EntityMoveEvent event) {
        if (!running) return;
        if (event.getEntity().getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) return;
        event.setTo(event.getTo().add(0, -400, 0));
        spawn(event.getFrom(), event.getEntity().getType());
    }

    private void spawn(Location loc, EntityType type) {
        Entity entity;
        if (diff.equals(Challenge.ChallengeDiff.NORMAL))
            entity = loc.getWorld().spawnEntity(loc, entityTypes.get(type), CreatureSpawnEvent.SpawnReason.CUSTOM);
        else
            entity = loc.getWorld().spawnEntity(loc, Utils.getRandomEntityType(Utils.EntityTypeCats.SPAWNABLE), CreatureSpawnEvent.SpawnReason.CUSTOM);
        chunks.add(loc.getChunk());
        if (type.equals(EntityType.ENDER_DRAGON)) {
            theEnderDragon = entity;
            ((LivingEntity) theEnderDragon).addPotionEffect(Utils.potionEffect(PotionEffectType.SLOW_FALLING, 200, 1, false, false, false));
        }

    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!running) return;
        if (event.getEntity().getKiller() != null) {
            EntityType newType = event.getEntityType();
            EntityType oldType = EntityType.UNKNOWN;
            for (Map.Entry<EntityType, EntityType> entry : entityTypes.entrySet()) {
                if (entityTypes.get(entry.getValue()).equals(newType)) oldType = entry.getValue();
            }
            event.getEntity().getKiller().sendMessage(Component.text(newType + " was " + oldType));

        }
        if (event.getEntity().equals(theEnderDragon)) {
            bossBar.setProgress(0);
            bossBar.removeAll();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                bossBar.removePlayer(onlinePlayer);
            }
            Player player;
            if (event.getEntity().getKiller() == null) player = playerManager.getPlayers().get(0);
            else player = event.getEntity().getKiller();
            boolean isOp = player.isOp();
            boolean sendCF = Boolean.TRUE.equals(player.getWorld().getGameRuleValue(GameRule.SEND_COMMAND_FEEDBACK));
            player.setOp(true);
            player.getWorld().setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false);
            player.performCommand("advancement grant @p only minecraft:end/kill_dragon");
            player.setOp(isOp);
            player.getWorld().setGameRule(GameRule.SEND_COMMAND_FEEDBACK, sendCF);
            timer.end();
        }

    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!running) return;
        if (event.getEntity().getType().equals(entityTypes.get(EntityType.ENDER_DRAGON))) {
                bossBar.setProgress(((LivingEntity) event.getEntity()).getHealth()
                        /
                        Objects.requireNonNull(((LivingEntity) event.getEntity()).
                                getAttribute(Attribute.GENERIC_MAX_HEALTH)).getDefaultValue());
                if ((event.getEntity()).isDead()) bossBar.setProgress(0);
        }
    }

    @EventHandler
    public void onEnderDragonBattle(PlayerPortalEvent event) {
        if (!running) return;
        if (event.getTo().getWorld().getEnderDragonBattle() != null) {
            DragonBattle ender = event.getTo().getWorld().getEnderDragonBattle();
            //theEnderDragon = event.getPlayer().getWorld().spawnEntity(new Location(event.getPlayer().getWorld(),0,50,0),entityTypes.get(EntityType.ENDER_DRAGON), CreatureSpawnEvent.SpawnReason.COMMAND)
            bossBar = ender.getBossBar();
            bossBar.setTitle(Utils.formateName(entityTypes.get(EntityType.ENDER_DRAGON).name(),false));

        }
    }


}
