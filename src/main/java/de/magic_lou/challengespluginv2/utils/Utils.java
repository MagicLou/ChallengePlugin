package de.magic_lou.challengespluginv2.utils;

import de.magic_lou.challengespluginv2.Challenge;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Utils {


    private static final Random random = new Random();

    private Utils() {
        throw new IllegalArgumentException("Utility Class");
    }

    public static Material getRandomMaterial(MaterialType type) {
        List<Material> materials = getMaterialList(type);
        return materials.get(random.nextInt(materials.size()));
    }

    public static PotionEffectType getRandomEffectType(PotionEffectTypes type) {
        List<PotionEffectType> effect = getPotionEffectTypes(type);
        return effect.get(random.nextInt(effect.size()));
    }

    public static EnumMap<Material, Material> getMaterialMap(MaterialType type, MaterialType type2) {
        EnumMap<Material, Material> mats = new EnumMap<>(Material.class);
        List<Material> materials = getMaterialList(type);
        List<Material> materials2 = getMaterialList(type2);

        Collections.shuffle(materials2);
        for (Material material : materials) {
            mats.put(material, materials2.get(random.nextInt(materials2.size() - 1)));
        }


        return mats;
    }


    public static List<Biome> getBiomList(BiomType type) {
        List<Biome> ret;
        List<Biome> nether = new LinkedList<>(Arrays.asList(Biome.BASALT_DELTAS, Biome.CRIMSON_FOREST, Biome.NETHER_WASTES, Biome.SOUL_SAND_VALLEY, Biome.WARPED_FOREST));
        List<Biome> end = new LinkedList<>(Arrays.asList(Biome.END_BARRENS, Biome.END_HIGHLANDS, Biome.END_MIDLANDS, Biome.THE_END, Biome.THE_VOID, Biome.SMALL_END_ISLANDS));
        switch (type) {
            case OVERWORLD -> {
                ret = new LinkedList<>(Arrays.asList(Biome.values()));
                ret.removeAll(nether);
                ret.removeAll(end);
            }
            case NETHER -> ret = nether;
            case END -> ret = end;
            default -> ret = new LinkedList<>(Arrays.asList(Biome.values()));
        }


        ret.remove(Biome.CUSTOM);
        return ret;
    }

    public static Biome getRandomBiome(BiomType type) {
        List<Biome> biomes = getBiomList(type);
        return biomes.get(random.nextInt(biomes.size()));
    }

    private static List<Material> materialListFilter(Predicate<Material> filter){
        return Arrays.stream(Material.values()).filter(filter).collect(Collectors.toList());
    }

    public static List<Material> getMaterialList(MaterialType type) {
        return switch (type) {
            case AIR -> materialListFilter(Material::isAir);
            case FUEL -> materialListFilter(Material::isFuel);
            case OCCLUDING -> materialListFilter(Material::isOccluding);
            case BLOCK -> materialListFilter(Material::isBlock);
            case SOLID -> materialListFilter(Material::isSolid);
            case ITEM -> materialListFilter(Material::isItem);
            default -> Arrays.stream(Material.values()).collect(Collectors.toList());
        };
    }

    public static List<PotionEffectType> getPotionEffectTypes(PotionEffectTypes types) {
        List<PotionEffectType> effectTypes;
        if (types.equals(PotionEffectTypes.POSITIV)) {
            effectTypes = new LinkedList<>(Arrays.asList(
                    PotionEffectType.SPEED,
                    PotionEffectType.FAST_DIGGING,
                    PotionEffectType.INCREASE_DAMAGE,
                    PotionEffectType.HEAL,
                    PotionEffectType.JUMP,
                    PotionEffectType.REGENERATION,
                    PotionEffectType.FIRE_RESISTANCE,
                    PotionEffectType.DAMAGE_RESISTANCE,
                    PotionEffectType.WATER_BREATHING,
                    PotionEffectType.INVISIBILITY,
                    PotionEffectType.NIGHT_VISION,
                    PotionEffectType.HEALTH_BOOST,
                    PotionEffectType.ABSORPTION,
                    PotionEffectType.SATURATION,
                    PotionEffectType.GLOWING,
                    PotionEffectType.LUCK,
                    PotionEffectType.SLOW_FALLING,
                    PotionEffectType.CONDUIT_POWER,
                    PotionEffectType.DOLPHINS_GRACE,
                    PotionEffectType.HERO_OF_THE_VILLAGE
            ));
        } else effectTypes = new LinkedList<>(Arrays.asList(PotionEffectType.values()));

        if (types.equals(PotionEffectTypes.SURVIVE)) {
            effectTypes.remove(PotionEffectType.WITHER);
            effectTypes.remove(PotionEffectType.HARM);
            effectTypes.remove(PotionEffectType.LEVITATION);
        }

        if (types.equals(PotionEffectTypes.ALLE)) effectTypes.remove(PotionEffectType.HARM);

        return effectTypes;
    }


    public static EntityType getRandomEntityType(EntityTypeCats type) {
        List<EntityType> types = getEntityTypes(type);
        return types.get(random.nextInt(types.size()));
    }

    public static Location setOnGround(Location loc) {
        while (loc.getBlock().getType().isSolid()) {
            loc.setY(loc.getY() + 1);
        }
        while (loc.getBlock().getType().isAir()) {
            loc.setY(loc.getY() - 1);
        }

        loc.setY(loc.getY() + 1);

        return loc;
    }

    public static EnumMap<EntityType, EntityType> setEntityTypeHashMap() {
        EnumMap<EntityType, EntityType> entityTypes = new EnumMap<>(EntityType.class);
        List<EntityType> killed = getEntityTypes(EntityTypeCats.ALL);
        List<EntityType> spawn = getEntityTypes(EntityTypeCats.SPAWNABLE);
        Collections.shuffle(spawn);
        for (EntityType entityType : killed) {
            entityTypes.put(entityType, spawn.get(random.nextInt(spawn.size())));
        }
        return entityTypes;
    }

    public static List<EntityType> getEntityTypes(EntityTypeCats type) {
        List<EntityType> types;
        if (Objects.requireNonNull(type).equals(EntityTypeCats.LIVING)) {
            types = Arrays.stream(EntityType.values()).filter(EntityType::isAlive).collect(Collectors.toList());
        } else if (Objects.requireNonNull(type).equals(EntityTypeCats.SPAWNABLE)) {
            types = Arrays.stream(EntityType.values()).filter(EntityType::isSpawnable).collect(Collectors.toList());
        } else {
            types = Arrays.stream(EntityType.values()).collect(Collectors.toList());
        }

        types.remove(EntityType.CAMEL); // JaJa ist nur vorübergehend
        types.remove(EntityType.PLAYER);
        return types;
    }

    public static String enumerate(List<String> values) {
        StringBuilder result = new StringBuilder();
        for (String value : values) result.append(value).append(", ");
        result.delete(result.length() - 2, result.length() - 1);
        return result.toString();
    }

    public static StringBuilder formatTime(int second, ChatColor color) {
        StringBuilder builder = new StringBuilder(color.toString());
        int days = second / (3600 * 24);
        int hours = (second % (3600 * 24)) / 3600;
        int minutes = (second % 3600) / 60;
        int seconds = second % 60;

        if (days > 0) builder.append(String.format("%02d Tag%s", days, ((days > 1) ? "e " : " ")));
        if (hours > 0) builder.append(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        else if (minutes > 0) builder.append(String.format("%02d:%02d", minutes, seconds));
        else if (seconds > 0) builder.append(String.format("%02d sec", seconds));
        if ((seconds + minutes + hours + days) == 0) builder.append("0 sec");
        return builder;
    }

    public static String formateLocation(Location loc, ChatColor labelColor, ChatColor valueColor) {
        return labelColor + " X: " + valueColor + loc.getBlockX() +
                labelColor + ", Y: " + valueColor + loc.getBlockY() +
                labelColor + ", Z: " + valueColor + loc.getBlockZ() +
                labelColor + " Dimension: " + valueColor + loc.getWorld().getEnvironment();
    }

    public static String formatLocationName(String name, ChatColor nameColor, Location loc, ChatColor labelColor, ChatColor valueColor) {
        return nameColor + name + labelColor + ":" + formateLocation(loc, labelColor, valueColor);
    }

    public static String formateName(String name,boolean tag){
        name = name.toLowerCase();
        StringBuilder builder = new StringBuilder();
        String[] list = name.split("_");
        if(tag) builder.append("'");
        for (String s : list) {
            char c = Character.toUpperCase(s.charAt(0));
            builder.append(c).append(s.substring(1)).append("_");
        }
        builder.replace(builder.length()-1,builder.length(),"");
        if(tag) builder.append("'");
        return builder.toString();
    }

    public static PotionEffect potionEffect(PotionEffectType effect, int duration, int amplify, boolean ambient, boolean particles, boolean icon) {
        return new PotionEffect(effect, duration, amplify, ambient, particles, icon);
    }

    public static List<Challenge.ChallengeDiff> getChallengeDiffs() {
        return new ArrayList<>(Arrays.asList(Challenge.ChallengeDiff.values()));
    }


    public static void effectPlayer(Player player, PotionEffectType type, int amplify) {
        player.addPotionEffect(getEffect(type, amplify));
    }

    public static void effectAddPlayer(Player player, PotionEffectType type) {
        int amplify = 1;
        for (PotionEffect activePotionEffect : player.getActivePotionEffects()) {
            if (activePotionEffect.getType().equals(type)) {
                amplify = amplify + Objects.requireNonNull(player.getPotionEffect(type)).getAmplifier();
                break;
            }
        }

        effectPlayer(player, type, amplify);
    }

    public static PotionEffect getEffect(PotionEffectType type, int amplify) {
        return new PotionEffect(type, 2000000000, amplify, false, false, true);
    }

    public static PotionEffect createPotionEffect(PotionEffectType type, int duration, int amplify, boolean ambient, boolean particles, boolean icon) {
        return new PotionEffect(type, duration, amplify, ambient, particles, icon);
    }

    public enum MaterialType {
        BLOCK, AIR, FUEL, ALL, OCCLUDING, SOLID, ITEM
    }

    public enum EntityTypeCats {
        LIVING, SPAWNABLE, ALL
    }

    public enum PotionEffectTypes {
        POSITIV, SURVIVE, ALL, ALLE
    }

    public enum BiomType {
        OVERWORLD, NETHER, END, ALL
    }

}
