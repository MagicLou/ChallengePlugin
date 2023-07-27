package de.magic_lou.challengespluginv2.achallenges.random.randomitem;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.Main;
import de.magic_lou.challengespluginv2.generators.EmptyChunkGenerator;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.timer.Timer;
import de.magic_lou.challengespluginv2.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Random;

public class RandomItem implements Listener {


    private static final Random random = new Random();
    private final List<Material> materials = Utils.getMaterialList(Utils.MaterialType.ITEM);
    private final PlayerManager playerManager;
    private final Timer timer;
    private World world;
    private Challenge.ChallengeDiff diff = Challenge.ChallengeDiff.NORMAL;
    private int time = 60;
    private boolean running = false;
    private BukkitTask task = null;
    private Location strong;

    public RandomItem(PlayerManager playerManager, World world, Timer timer) {
        this.playerManager = playerManager;
        this.world = world;
        this.timer = timer;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Challenge.ChallengeDiff getDiff() {
        return diff;
    }

    public void setDiff(Challenge.ChallengeDiff diff) {
        this.diff = diff;
        switch (diff) {
            case EASY -> time = 10;
            case NORMAL -> time = 60;
            case HARD -> time = 300;

        }
    }

    private World createWorld() {
        WorldCreator wc = new WorldCreator("world_rand_item");
        wc.generator(new EmptyChunkGenerator());
        World ret = wc.createWorld();
        Bukkit.getWorlds().add(ret);
        return ret;
    }

    public void create() {
        world = createWorld();
    }

    public void sendPlayersWorld(World world) {


        if (world == null) {
            world = createWorld();
            this.world = world;
        }

        world.getBlockAt(0, 100, 0).setType(Material.BEDROCK);


        int x = random.nextInt(1000) + 2000 * (random.nextInt(1) - 1);
        int y = random.nextInt(100) - 50;
        int z = random.nextInt(1000) + 2000 * (random.nextInt(1) - 1);

        strong = new Location(world, x, y, z);


        Location loc = new Location(world, 0, 101, 0);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(loc);
            player.sendMessage(Component.text(Utils.formateLocation(strong, ChatColor.WHITE, ChatColor.WHITE)));


            World finalWorld = world;
            task = Bukkit.getScheduler().runTaskTimer(Main.instance, () -> {
                if (playerManager.containsAny(strong.getNearbyEntities(20, 350, 20))) {
                    boolean op = player.isOp();
                    boolean send = Boolean.TRUE.equals(finalWorld.getGameRuleValue(GameRule.SEND_COMMAND_FEEDBACK));
                    finalWorld.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false);
                    player.setOp(true);
                    player.performCommand("place structure stronghold " + x + " " + y + " " + z);
                    finalWorld.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, send);
                    player.setOp(op);
                    task.cancel();
                }
            }, 20, 20);
        }


    }


    public void getRandomItem() {
        for (Player player : playerManager.getPlayers()) {
            player.getInventory().addItem(new ItemStack(materials.get(random.nextInt(materials.size()))));
            player.updateInventory();
        }
    }


    public void start() {
        if (task != null) task.cancel();
        create();
        sendPlayersWorld(world);
        getRandomItem();
        timer.addExecuteOn(time, this::newItem);
    }

    public void stop() {
        if (task != null) task.cancel();
    }

    private void newItem() {
        timer.addExecuteIn(time, this::newItem);
        getRandomItem();
    }


    @EventHandler
    public void onDimSwitch(PlayerPortalEvent event) {
        if (!running) return;
        if (event.getTo().getWorld().isPiglinSafe()) event.setCancelled(true);
    }

    @EventHandler
    public void onEnderEye(PlayerInteractEvent event) {
        if (!running) return;
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();

            if (player.getActiveItem().getType().equals(Material.ENDER_EYE)) { //here
                Location loc = player.getLocation();
                EnderSignal entity = (EnderSignal) player.getWorld().spawnEntity(loc, EntityType.ENDER_SIGNAL);
                entity.setTargetLocation(strong);
                ItemStack inHand = player.getActiveItem(); //here
                player.getActiveItem().setAmount(inHand.getAmount()-1); //here
            }
        }
    }

}
