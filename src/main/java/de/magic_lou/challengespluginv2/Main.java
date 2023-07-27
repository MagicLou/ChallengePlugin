package de.magic_lou.challengespluginv2;

import de.magic_lou.challengespluginv2.challengemanagment.ChallengeCommand;
import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import de.magic_lou.challengespluginv2.challengemanagment.ChallengeRegister;
import de.magic_lou.challengespluginv2.challengemanagment.GUI.GUICommand;
import de.magic_lou.challengespluginv2.challengemanagment.GUI.InventoryClickListener;
import de.magic_lou.challengespluginv2.challengemanagment.GUI.guis.ChallengeGui;
import de.magic_lou.challengespluginv2.challengemanagment.GUI.guis.MainGui;
import de.magic_lou.challengespluginv2.challengemanagment.GUI.guis.SelectedEnchantment;
import de.magic_lou.challengespluginv2.challengemanagment.GUI.guis.SettingsGUI;
import de.magic_lou.challengespluginv2.challengemanagment.GUI.managment.ChallengeManagerGUI;
import de.magic_lou.challengespluginv2.challengemanagment.GUI.managment.GUIManager;
import de.magic_lou.challengespluginv2.commands.ResetCommand;
import de.magic_lou.challengespluginv2.commands.challengecomands.*;
import de.magic_lou.challengespluginv2.commands.settings.BackPack;
import de.magic_lou.challengespluginv2.commands.settings.InvSeeCommand;
import de.magic_lou.challengespluginv2.commands.settings.SharedHearts;
import de.magic_lou.challengespluginv2.commands.settings.SharedInv;
import de.magic_lou.challengespluginv2.gravitys.*;
import de.magic_lou.challengespluginv2.hardcoremanagment.HardCoreCommand;
import de.magic_lou.challengespluginv2.hardcoremanagment.HardCoreManager;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.playermanagment.SessionListener;
import de.magic_lou.challengespluginv2.playermanagment.SpectateCommand;
import de.magic_lou.challengespluginv2.position.PositionCommand;
import de.magic_lou.challengespluginv2.position.PositionManager;
import de.magic_lou.challengespluginv2.timer.EndListener;
import de.magic_lou.challengespluginv2.timer.PauseStopListener;
import de.magic_lou.challengespluginv2.timer.Timer;
import de.magic_lou.challengespluginv2.timer.TimerCommand;
import de.magic_lou.challengespluginv2.utils.UtilsForce;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;

public final class Main extends JavaPlugin {


    public static Main instance;
    private ChallengeManager challengeManager;

    @Override
    public void onLoad() {


        saveConfig();
        if (!getConfig().contains("isReset")) {
            getConfig().set("isReset", false);
            saveConfig();
            return;
        }


        if (getConfig().getBoolean("isReset")) {
            try {
                File world = new File(Bukkit.getWorldContainer(), "world");
                File emptyWorld = new File(Bukkit.getWorldContainer(), "world_rand_item");
                File nether = new File(Bukkit.getWorldContainer(), "world_nether");
                File the_end = new File(Bukkit.getWorldContainer(), "world_the_end");

                FileUtils.deleteDirectory(world);
                FileUtils.deleteDirectory(emptyWorld);
                FileUtils.deleteDirectory(nether);
                FileUtils.deleteDirectory(the_end);

                world.mkdirs();
                nether.mkdirs();
                the_end.mkdirs();

                new File(world, "data").mkdirs();
                new File(world, "datapacks").mkdirs();
                new File(world, "playerdata").mkdirs();
                new File(world, "poi").mkdirs();
                new File(world, "region").mkdirs();

                new File(nether, "data").mkdirs();
                new File(nether, "datapacks").mkdirs();
                new File(nether, "playerdata").mkdirs();
                new File(nether, "poi").mkdirs();
                new File(nether, "region").mkdirs();

                new File(the_end, "data").mkdirs();
                new File(the_end, "datapacks").mkdirs();
                new File(the_end, "playerdata").mkdirs();
                new File(the_end, "poi").mkdirs();
                new File(the_end, "region").mkdirs();


            } catch (IOException e) {
                e.printStackTrace();
            }

            getConfig().set("isReset", false);
            saveConfig();
        }
    }

    @Override
    public void onEnable() {

        World emptyWorld = null;
        if (!Bukkit.getWorlds().contains(Bukkit.getWorld("world_rand_item")) && Bukkit.getWorld("world_rand_item") == null) {
            emptyWorld = Bukkit.getWorld("world_rand_item");
        }


        instance = this;
        PlayerManager playerManager = new PlayerManager();
        PauseStopListener pauseStopListener = new PauseStopListener(playerManager);

        HardCoreManager hardCoreManager = new HardCoreManager(playerManager);
        HardCoreCommand hardCoreCommand = new HardCoreCommand(hardCoreManager);


        //Gravity
        HardGravity hardGravity = new HardGravity(playerManager);
        Bukkit.getPluginManager().registerEvents(hardGravity, this);

        NoGravity noGravity = new NoGravity(playerManager);
        Bukkit.getPluginManager().registerEvents(noGravity, this);

        AntiGravity antiGravity = new AntiGravity(playerManager);
        Bukkit.getPluginManager().registerEvents(antiGravity, this);

        LowGravity lowGravity = new LowGravity(playerManager);
        Bukkit.getPluginManager().registerEvents(lowGravity, this);

        NormalGravity normalGravity = new NormalGravity(playerManager);

        GravityManager gravityManager = new GravityManager(antiGravity, hardGravity, lowGravity, noGravity, normalGravity);
        //Gravity Ende

        challengeManager = new ChallengeManager(pauseStopListener, playerManager, hardCoreManager, gravityManager);

        Timer timer = new Timer(playerManager, challengeManager);
        SpectateCommand spectateCommand = new SpectateCommand(timer, challengeManager, playerManager);

        SessionListener sessionListener = new SessionListener(playerManager, challengeManager, pauseStopListener);

        PositionManager positionManager = new PositionManager();
        PositionCommand positionCommand = new PositionCommand(playerManager, positionManager);

        TimerCommand timerCommand = new TimerCommand(challengeManager, timer);
        ChallengeCommand challengeCommand = new ChallengeCommand(timer, challengeManager);
        ResetCommand resetCommand = new ResetCommand();

        EndListener endListener = new EndListener(timer, playerManager, hardCoreManager, resetCommand);
        ShuffleCommand shuffleCommand = new ShuffleCommand(challengeManager);
        GetChallengeCommand getChallengeCommand = new GetChallengeCommand(challengeManager);
        OnExplodeCommand explodeCommand = new OnExplodeCommand(challengeManager);
        PickUpItemCommand pickUpItemCommand = new PickUpItemCommand(challengeManager);
        PrintCommand printCommand = new PrintCommand(challengeManager);


        SkipCommand skipCommand = new SkipCommand(challengeManager);
        TryCommand tryCommand = new TryCommand(challengeManager);
        WayPointKillCommand wayPointKillCommand = new WayPointKillCommand(challengeManager);

        Objects.requireNonNull(Bukkit.getPluginCommand("spectate")).setExecutor(spectateCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("challenge")).setExecutor(challengeCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("challenge")).setTabCompleter(challengeCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("timer")).setExecutor(timerCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("timer")).setTabCompleter(timerCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("position")).setExecutor(positionCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("position")).setTabCompleter(positionCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("hardcore")).setExecutor(hardCoreCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("hardcore")).setTabCompleter(hardCoreCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("shuffle")).setExecutor(shuffleCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("skip")).setExecutor(skipCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("try")).setExecutor(tryCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("waypointkill")).setExecutor(wayPointKillCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("getChallenge")).setExecutor(getChallengeCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("explosion")).setExecutor(explodeCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("pickup")).setExecutor(pickUpItemCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("print")).setExecutor(printCommand);
        Bukkit.getPluginManager().registerEvents(pauseStopListener, this);
        Bukkit.getPluginManager().registerEvents(sessionListener, this);
        Bukkit.getPluginManager().registerEvents(endListener, this);
        Bukkit.getPluginManager().registerEvents(hardCoreManager, this);

        Objects.requireNonNull(Bukkit.getPluginCommand("reset")).setExecutor(resetCommand);


        InvSeeCommand invSeeCommand = new InvSeeCommand();
        Objects.requireNonNull(Bukkit.getPluginCommand("invsee")).setExecutor(invSeeCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("invsee")).setTabCompleter(invSeeCommand);

        SharedHearts sharedHearts = new SharedHearts(playerManager);
        Bukkit.getPluginManager().registerEvents(sharedHearts, this);


        SharedInv sharedInv = new SharedInv(playerManager);
        Bukkit.getPluginManager().registerEvents(sharedInv, this);

        BackPack backPack = new BackPack(playerManager);
        Bukkit.getPluginManager().registerEvents(backPack, this);

        //GUI
        ChallengeManagerGUI challengeManagerGUI = new ChallengeManagerGUI(challengeManager, timer);
        MainGui mainGUI = new MainGui(challengeManagerGUI);
        ChallengeGui challengeGUI = new ChallengeGui(mainGUI, challengeManagerGUI);
        SettingsGUI settingsGUI = new SettingsGUI(mainGUI, invSeeCommand, gravityManager, sharedHearts, backPack, challengeManagerGUI);

        GUIManager guiManager = new GUIManager(mainGUI, challengeManagerGUI);

        GUICommand guiCommand = new GUICommand(guiManager);

        InventoryClickListener inventoryClickListener = new InventoryClickListener(mainGUI, challengeGUI, challengeManagerGUI, guiManager, settingsGUI);

        Objects.requireNonNull(Bukkit.getPluginCommand("gui")).setExecutor(guiCommand);
        Bukkit.getPluginManager().registerEvents(inventoryClickListener, this);
        //GUI Ende

        UtilsForce utilsForce = new UtilsForce(playerManager, timer);

        ChallengeRegister.registerChallenges(instance, challengeManager, playerManager, gravityManager, timer, emptyWorld, utilsForce);

        registerGlow();
    }


    @Override
    public void onDisable() {
        challengeManager.stop();
    }


    public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            SelectedEnchantment selectedEnchantment = new SelectedEnchantment();
            Enchantment.registerEnchantment(selectedEnchantment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
