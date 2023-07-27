package de.magic_lou.challengespluginv2.challengemanagment;

import de.magic_lou.challengespluginv2.Main;
import de.magic_lou.challengespluginv2.achallenges.force.forcebiom.ForceBiom;
import de.magic_lou.challengespluginv2.achallenges.force.forcebiom.ForceBiomChallenge;
import de.magic_lou.challengespluginv2.achallenges.force.forceblock.ForceBlock;
import de.magic_lou.challengespluginv2.achallenges.force.forceblock.ForceBlockChallenge;
import de.magic_lou.challengespluginv2.achallenges.force.forceheight.ForceHeight;
import de.magic_lou.challengespluginv2.achallenges.force.forceheight.ForceHeightChallenge;
import de.magic_lou.challengespluginv2.achallenges.force.forceitem.ForceItem;
import de.magic_lou.challengespluginv2.achallenges.force.forceitem.ForceItemChallenge;
import de.magic_lou.challengespluginv2.achallenges.force.forcemob.ForceMob;
import de.magic_lou.challengespluginv2.achallenges.force.forcemob.ForceMobChallenge;
import de.magic_lou.challengespluginv2.achallenges.other.backport.BackPort;
import de.magic_lou.challengespluginv2.achallenges.other.backport.BackPortChallenge;
import de.magic_lou.challengespluginv2.achallenges.other.chorousfruit.ChorusFruit;
import de.magic_lou.challengespluginv2.achallenges.other.chorousfruit.ChorusFruitChallenge;
import de.magic_lou.challengespluginv2.achallenges.other.chunkbreaking.ChunkBreaking;
import de.magic_lou.challengespluginv2.achallenges.other.chunkbreaking.ChunkBreakingChallenge;
import de.magic_lou.challengespluginv2.achallenges.other.chunkwalking.ChunkWalking;
import de.magic_lou.challengespluginv2.achallenges.other.chunkwalking.ChunkWalkingChallenge;
import de.magic_lou.challengespluginv2.achallenges.other.running.Running;
import de.magic_lou.challengespluginv2.achallenges.other.running.RunningChallenge;
import de.magic_lou.challengespluginv2.achallenges.other.yeet.Yeet;
import de.magic_lou.challengespluginv2.achallenges.other.yeet.YeetChallenge;
import de.magic_lou.challengespluginv2.achallenges.random.randomblocklook.RandomBlockLook;
import de.magic_lou.challengespluginv2.achallenges.random.randomblocklook.RandomBlockLookChallenge;
import de.magic_lou.challengespluginv2.achallenges.random.randomblockplace.RandomBlockPlace;
import de.magic_lou.challengespluginv2.achallenges.random.randomblockplace.RandomBlockPlaceChallenge;
import de.magic_lou.challengespluginv2.achallenges.random.randomblockzumob.RandomBlockZuMob;
import de.magic_lou.challengespluginv2.achallenges.random.randomblockzumob.RandomBlockZuMobChallenge;
import de.magic_lou.challengespluginv2.achallenges.random.randomchestloot.RandomChestLoot;
import de.magic_lou.challengespluginv2.achallenges.random.randomchestloot.RandomChestLootChallenge;
import de.magic_lou.challengespluginv2.achallenges.random.randomcrafting.RandomCrafting;
import de.magic_lou.challengespluginv2.achallenges.random.randomcrafting.RandomCraftingChallenge;
import de.magic_lou.challengespluginv2.achallenges.random.randomeffectonblock.RandomEffectOnBlock;
import de.magic_lou.challengespluginv2.achallenges.random.randomeffectonblock.RandomEffectOnBlockChallenge;
import de.magic_lou.challengespluginv2.achallenges.random.randomeffectondamage.RandomEffectOnDamage;
import de.magic_lou.challengespluginv2.achallenges.random.randomeffectondamage.RandomEffectOnDamageChallenge;
import de.magic_lou.challengespluginv2.achallenges.random.randomentityspawn.RandomEntitySpawn;
import de.magic_lou.challengespluginv2.achallenges.random.randomentityspawn.RandomEntitySpawnChallenge;
import de.magic_lou.challengespluginv2.achallenges.random.randomentityspawnonkill.RandomEntitySpawnOnKill;
import de.magic_lou.challengespluginv2.achallenges.random.randomentityspawnonkill.RandomEntitySpawnOnKillChallenge;
import de.magic_lou.challengespluginv2.achallenges.random.randomgravity.RandomGravity;
import de.magic_lou.challengespluginv2.achallenges.random.randomgravity.RandomGravityChallenge;
import de.magic_lou.challengespluginv2.achallenges.random.randominventory.RandomInventory;
import de.magic_lou.challengespluginv2.achallenges.random.randominventory.RandomInventoryChallenge;
import de.magic_lou.challengespluginv2.achallenges.random.randomitem.RandomItem;
import de.magic_lou.challengespluginv2.achallenges.random.randomitem.RandomItemChallenge;
import de.magic_lou.challengespluginv2.achallenges.random.randomizer.Randomizer;
import de.magic_lou.challengespluginv2.achallenges.random.randomizer.RandomizerChallenge;
import de.magic_lou.challengespluginv2.achallenges.random.randommobdrop.RandomMobDrop;
import de.magic_lou.challengespluginv2.achallenges.random.randommobdrop.RandomMobDropChallenge;
import de.magic_lou.challengespluginv2.achallenges.random.randommobloot.RandomMobLoot;
import de.magic_lou.challengespluginv2.achallenges.random.randommobloot.RandomMobLootChallenge;
import de.magic_lou.challengespluginv2.gravitys.GravityManager;
import de.magic_lou.challengespluginv2.playermanagment.PlayerManager;
import de.magic_lou.challengespluginv2.timer.Timer;
import de.magic_lou.challengespluginv2.utils.UtilsForce;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.checkerframework.checker.units.qual.C;

public class ChallengeRegister {

    private ChallengeRegister() {
        throw new IllegalArgumentException("UtilityClass");
    }

    public static void registerChallenges(Main instance, ChallengeManager challengeManager, PlayerManager playerManager, GravityManager gravityManager, Timer timer, World emptyWorld, UtilsForce utilsForce) {


        //ForceBiom
        ForceBiom forceBiom = new ForceBiom(playerManager, timer, utilsForce);
        ForceBiomChallenge forceBiomChallenge = new ForceBiomChallenge(forceBiom);
        challengeManager.registerChallenge(forceBiomChallenge);

        //ForceBlock
        ForceBlock forceBlock = new ForceBlock(playerManager, timer, utilsForce);
        ForceBlockChallenge forceBlockChallenge = new ForceBlockChallenge(forceBlock);
        challengeManager.registerChallenge(forceBlockChallenge);

        //ForceHeight
        ForceHeight forceHeight = new ForceHeight(playerManager, timer, utilsForce);
        ForceHeightChallenge forceHeightChallenge = new ForceHeightChallenge(forceHeight);
        challengeManager.registerChallenge(forceHeightChallenge);

        //ForceItem
        ForceItem forceItem = new ForceItem(playerManager, timer, utilsForce);
        ForceItemChallenge forceItemChallenge = new ForceItemChallenge(forceItem);
        challengeManager.registerChallenge(forceItemChallenge);

        //ForceMob
        ForceMob forceMob = new ForceMob(playerManager, timer, utilsForce);
        Bukkit.getPluginManager().registerEvents(forceMob, instance);
        ForceMobChallenge forceMobChallenge = new ForceMobChallenge(forceMob);
        challengeManager.registerChallenge(forceMobChallenge);

//        challengeManager.registerChallenge(Main.forceItemBattle)
//        challengeManager.registerChallenge(new ForceStructures())


        //RandomBlockLook
        RandomBlockLook randomBlockLook = new RandomBlockLook(playerManager);
        RandomBlockLookChallenge randomBlockLookChallenge = new RandomBlockLookChallenge(randomBlockLook);
        challengeManager.registerChallenge(randomBlockLookChallenge);

        //RandomBlockPlace
        RandomBlockPlace randomBlockPlace = new RandomBlockPlace();
        Bukkit.getPluginManager().registerEvents(randomBlockPlace, instance);
        RandomBlockPlaceChallenge randomBlockPlaceChallenge = new RandomBlockPlaceChallenge(randomBlockPlace);
        challengeManager.registerChallenge(randomBlockPlaceChallenge);

        //RandomBlockZuMob
        RandomBlockZuMob randomBlockZuMob = new RandomBlockZuMob();
        Bukkit.getPluginManager().registerEvents(randomBlockZuMob, instance);
        RandomBlockZuMobChallenge randomBlockZuMobChallenge = new RandomBlockZuMobChallenge(randomBlockZuMob);
        challengeManager.registerChallenge(randomBlockZuMobChallenge);

//        RandomChallenge randomChallenge = new RandomChallenge(challengeManager)
//        challengeManager.registerChallenge(randomChallenge)

        //RandomCrafting
        RandomCrafting randomCrafting = new RandomCrafting();
        Bukkit.getPluginManager().registerEvents(randomCrafting, instance);
        RandomCraftingChallenge randomCraftingChallenge = new RandomCraftingChallenge(randomCrafting);
        challengeManager.registerChallenge(randomCraftingChallenge);

        //RandomEffectOnDamage
        RandomEffectOnDamage randomEffectOnDamage = new RandomEffectOnDamage();
        Bukkit.getPluginManager().registerEvents(randomEffectOnDamage, instance);
        RandomEffectOnDamageChallenge randomEffectOnDamageChallenge = new RandomEffectOnDamageChallenge(randomEffectOnDamage);
        challengeManager.registerChallenge(randomEffectOnDamageChallenge);

        //RandomEffectOnBlock
        RandomEffectOnBlock randomEffectOnBlock = new RandomEffectOnBlock();
        Bukkit.getPluginManager().registerEvents(randomEffectOnBlock, instance);
        RandomEffectOnBlockChallenge randomEffectOnBlockChallenge = new RandomEffectOnBlockChallenge(randomEffectOnBlock);
        challengeManager.registerChallenge(randomEffectOnBlockChallenge);

        //RandomGravity
        RandomGravity randomGravity = new RandomGravity(gravityManager, timer);
        RandomGravityChallenge randomGravityChallenge = new RandomGravityChallenge(randomGravity);
        challengeManager.registerChallenge(randomGravityChallenge);

        //RandomItem
        RandomItem randomItem = new RandomItem(playerManager, emptyWorld, timer);
        Bukkit.getPluginManager().registerEvents(randomItem, instance);
        RandomItemChallenge randomItemChallenge = new RandomItemChallenge(randomItem);
        challengeManager.registerChallenge(randomItemChallenge);

        //Randomizer
        Randomizer randomizer = new Randomizer();
        Bukkit.getPluginManager().registerEvents(randomizer, instance);
        RandomizerChallenge randomizerChallenge = new RandomizerChallenge(randomizer);
        challengeManager.registerChallenge(randomizerChallenge);

        //RandomInventory
        RandomInventory randomInventory = new RandomInventory(timer, playerManager);
        RandomInventoryChallenge randomInventoryChallenge = new RandomInventoryChallenge(randomInventory);
        challengeManager.registerChallenge(randomInventoryChallenge);

        //RandomEntitySpawnOnKill
        RandomEntitySpawnOnKill randomEntitySpawnOnKill = new RandomEntitySpawnOnKill();
        Bukkit.getPluginManager().registerEvents(randomEntitySpawnOnKill, instance);
        RandomEntitySpawnOnKillChallenge randomEntitySpawnOnKillChallenge = new RandomEntitySpawnOnKillChallenge(randomEntitySpawnOnKill);
        challengeManager.registerChallenge(randomEntitySpawnOnKillChallenge);

        //RandomEntitySpawn
        RandomEntitySpawn randomEntitySpawn = new RandomEntitySpawn(timer, playerManager);
        Bukkit.getPluginManager().registerEvents(randomEntitySpawn, instance);
        RandomEntitySpawnChallenge randomEntitySpawnChallenge = new RandomEntitySpawnChallenge(randomEntitySpawn);
        challengeManager.registerChallenge(randomEntitySpawnChallenge);

        //RandomChestLoot
        RandomChestLoot randomChestLoot = new RandomChestLoot();
        Bukkit.getPluginManager().registerEvents(randomChestLoot, instance);
        RandomChestLootChallenge randomChestLootChallenge = new RandomChestLootChallenge(randomChestLoot);
        challengeManager.registerChallenge(randomChestLootChallenge);

        //RandomMobLoot
        RandomMobLoot randomMobLoot = new RandomMobLoot();
        Bukkit.getPluginManager().registerEvents(randomMobLoot, instance);
        RandomMobLootChallenge randomMobLootChallenge = new RandomMobLootChallenge(randomMobLoot);
        challengeManager.registerChallenge(randomMobLootChallenge);

        //RandomMobDrop
        RandomMobDrop randomMobDrop = new RandomMobDrop();
        Bukkit.getPluginManager().registerEvents(randomMobDrop, instance);
        RandomMobDropChallenge randomMobDropChallenge = new RandomMobDropChallenge(randomMobDrop);
        challengeManager.registerChallenge(randomMobDropChallenge);

        //YEET
        Yeet yeet = new Yeet(timer, playerManager);
        YeetChallenge yeetChallenge = new YeetChallenge(yeet);
        challengeManager.registerChallenge(yeetChallenge);

        //BackPort
        BackPort backPort = new BackPort(playerManager);
        Bukkit.getPluginManager().registerEvents(backPort, instance);
        BackPortChallenge backPortChallenge = new BackPortChallenge(backPort);
        challengeManager.registerChallenge(backPortChallenge);

        //Running
        Running running = new Running(playerManager);
        RunningChallenge runningChallenge = new RunningChallenge(running);
        challengeManager.registerChallenge(runningChallenge);

        //ChorusFruit
        ChorusFruit chorusFruit = new ChorusFruit(playerManager);
        Bukkit.getPluginManager().registerEvents(chorusFruit,instance);
        ChorusFruitChallenge chorusFruitChallenge = new ChorusFruitChallenge(chorusFruit);
        challengeManager.registerChallenge(chorusFruitChallenge);

        //ChunkBreaking
        ChunkBreaking chunkBreaking = new ChunkBreaking(playerManager);
        Bukkit.getPluginManager().registerEvents(chunkBreaking,instance);
        ChunkBreakingChallenge chunkBreakingChallenge = new ChunkBreakingChallenge(chunkBreaking);
        challengeManager.registerChallenge(chunkBreakingChallenge);


        //ChunkWalking
        ChunkWalking chunkWalking = new ChunkWalking(playerManager);
        Bukkit.getPluginManager().registerEvents(chunkWalking,instance);
        ChunkWalkingChallenge chunkWalkingChallenge = new ChunkWalkingChallenge(chunkWalking);
        challengeManager.registerChallenge(chunkWalkingChallenge);


//        challengeManager.registerChallenge(new ChunkWalking())
//
//        challengeManager.registerChallenge(new IceChallenge())
//        challengeManager.registerChallenge(new LevelBorder())

//        challengeManager.registerChallenge(new SpeedChallenge())
//        challengeManager.registerChallenge(new VampireChallenge())


//        challengeManager.registerChallenge(new TheUltraRandomizer())
//        challengeManager.registerChallenge(new TheBrainFRandomizer())
//        challengeManager.registerChallenge(new ChunkReplacing())
//        challengeManager.registerChallenge(new ChunkDouble())
//        challengeManager.registerChallenge(new EntityVerwandelChallenge())
//        challengeManager.registerChallenge(new NetherInOvw())
    }


}
