package ladysnake.illuminations.client.config;

import com.google.common.collect.ImmutableMap;
import ladysnake.illuminations.client.data.AuraSettings;
import ladysnake.illuminations.client.data.BiomeSettings;
import ladysnake.illuminations.client.enums.EyesInTheDarkSpawnRate;
import ladysnake.illuminations.client.enums.FireflySpawnRate;
import ladysnake.illuminations.client.enums.GlowwormSpawnRate;
import ladysnake.illuminations.client.enums.HalloweenFeatures;
import ladysnake.illuminations.client.enums.PlanktonSpawnRate;
import ladysnake.illuminations.client.enums.WillOWispsSpawnRate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public final class DefaultConfig {
    public static final HalloweenFeatures HALLOWEEN_FEATURES;
    public static final EyesInTheDarkSpawnRate EYES_IN_THE_DARK_SPAWN_RATE;
    public static final WillOWispsSpawnRate WILL_O_WISPS_SPAWN_RATE;
    public static final int CHORUS_PETALS_SPAWN_MULTIPLIER = 1;
    public static final int DENSITY = 100;
    public static final boolean FIREFLY_SPAWN_ALWAYS = false;
    public static final boolean FIREFLY_SPAWN_UNDERGROUND = false;
    public static final int FIREFLY_WHITE_ALPHA = 100;
    public static final boolean FIREFLY_RAINBOW = false;
    public static final boolean DISPLAY_COSMETICS = true;
    public static final boolean DEBUG_MODE = false;
    public static final boolean VIEW_AURAS_FP = false;
    public static final boolean DISPLAY_DONATION_TOAST = false;
    public static final ImmutableMap<ResourceLocation, BiomeSettings> BIOME_SETTINGS;
    public static final ImmutableMap<String, AuraSettings> AURA_SETTINGS;

    private DefaultConfig() {
        throw new UnsupportedOperationException();
    }

    public static BiomeSettings getBiomeSettings(ResourceKey<Biome> biome) {
        return (BiomeSettings)BIOME_SETTINGS.get(biome);
    }

    public static AuraSettings getAuraSettings(String aura) {
        return (AuraSettings)AURA_SETTINGS.get(aura);
    }

    static {
        HALLOWEEN_FEATURES = HalloweenFeatures.ENABLE;
        EYES_IN_THE_DARK_SPAWN_RATE = EyesInTheDarkSpawnRate.MEDIUM;
        WILL_O_WISPS_SPAWN_RATE = WillOWispsSpawnRate.MEDIUM;
        BIOME_SETTINGS = ImmutableMap.<ResourceLocation, BiomeSettings>builder()
                .put(Biomes.THE_VOID.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 16777215, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.PLAINS.location(), new BiomeSettings(FireflySpawnRate.LOW, 9551193, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.SUNFLOWER_PLAINS.location(), new BiomeSettings(FireflySpawnRate.LOW, 9551193, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.SNOWY_PLAINS.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 49151, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.ICE_SPIKES.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 49151, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.DESERT.location(), new BiomeSettings(FireflySpawnRate.MEDIUM, 16754517, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.SWAMP.location(), new BiomeSettings(FireflySpawnRate.HIGH, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.MANGROVE_SWAMP.location(), new BiomeSettings(FireflySpawnRate.HIGH, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.FOREST.location(), new BiomeSettings(FireflySpawnRate.MEDIUM, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.FLOWER_FOREST.location(), new BiomeSettings(FireflySpawnRate.MEDIUM, 16744429, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.BIRCH_FOREST.location(), new BiomeSettings(FireflySpawnRate.MEDIUM, 15007488, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.DARK_FOREST.location(), new BiomeSettings(FireflySpawnRate.MEDIUM, 26880, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.OLD_GROWTH_BIRCH_FOREST.location(), new BiomeSettings(FireflySpawnRate.MEDIUM, 15007488, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.OLD_GROWTH_PINE_TAIGA.location(), new BiomeSettings(FireflySpawnRate.LOW, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.OLD_GROWTH_SPRUCE_TAIGA.location(), new BiomeSettings(FireflySpawnRate.LOW, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.TAIGA.location(), new BiomeSettings(FireflySpawnRate.LOW, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.SNOWY_TAIGA.location(), new BiomeSettings(FireflySpawnRate.LOW, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.SAVANNA.location(), new BiomeSettings(FireflySpawnRate.LOW, 8950528, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.SAVANNA_PLATEAU.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 8950528, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.WINDSWEPT_HILLS.location(), new BiomeSettings(FireflySpawnRate.LOW, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.WINDSWEPT_GRAVELLY_HILLS.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.WINDSWEPT_FOREST.location(), new BiomeSettings(FireflySpawnRate.LOW, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.WINDSWEPT_SAVANNA.location(), new BiomeSettings(FireflySpawnRate.LOW, 8950528, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.JUNGLE.location(), new BiomeSettings(FireflySpawnRate.MEDIUM, 65313, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.SPARSE_JUNGLE.location(), new BiomeSettings(FireflySpawnRate.MEDIUM, 65313, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.BAMBOO_JUNGLE.location(), new BiomeSettings(FireflySpawnRate.MEDIUM, 65313, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.BADLANDS.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 8950528, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.ERODED_BADLANDS.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 8950528, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.WOODED_BADLANDS.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 8950528, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.MEADOW.location(), new BiomeSettings(FireflySpawnRate.LOW, 5025416, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.GROVE.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 49151, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.SNOWY_SLOPES.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 49151, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.FROZEN_PEAKS.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 49151, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.JAGGED_PEAKS.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 49151, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.STONY_PEAKS.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.RIVER.location(), new BiomeSettings(FireflySpawnRate.MEDIUM, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.FROZEN_RIVER.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 49151, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.BEACH.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.SNOWY_BEACH.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 49151, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.STONY_SHORE.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.WARM_OCEAN.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.LUKEWARM_OCEAN.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.DEEP_LUKEWARM_OCEAN.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.OCEAN.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.DEEP_OCEAN.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.COLD_OCEAN.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.DEEP_COLD_OCEAN.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.FROZEN_OCEAN.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.DEEP_FROZEN_OCEAN.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.MUSHROOM_FIELDS.location(), new BiomeSettings(FireflySpawnRate.LOW, 16744335, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.DRIPSTONE_CAVES.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 12582656, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.LUSH_CAVES.location(), new BiomeSettings(FireflySpawnRate.MEDIUM, 15906374, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.DEEP_DARK.location(), new BiomeSettings(FireflySpawnRate.LOW, 2743787, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.NETHER_WASTES.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 16744448, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.WARPED_FOREST.location(), new BiomeSettings(FireflySpawnRate.LOW, 32896, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.CRIMSON_FOREST.location(), new BiomeSettings(FireflySpawnRate.LOW, 16744448, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.SOUL_SAND_VALLEY.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 65535, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.BASALT_DELTAS.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 16744448, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.THE_END.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 8388863, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.END_HIGHLANDS.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 8388863, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.END_MIDLANDS.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 8388863, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.SMALL_END_ISLANDS.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 8388863, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE))
                .put(Biomes.END_BARRENS.location(), new BiomeSettings(FireflySpawnRate.DISABLE, 8388863, GlowwormSpawnRate.DISABLE, PlanktonSpawnRate.DISABLE)).build();
        AURA_SETTINGS = ImmutableMap.<String, AuraSettings>builder()
                .put("twilight", new AuraSettings(0.1F, 1))
                .put("ghostly", new AuraSettings(0.1F, 1))
                .put("chorus", new AuraSettings(0.1F, 1))
.put("autumn_leaves", new AuraSettings(0.3F, 1))
.put("sculk_tendrils", new AuraSettings(0.1F, 1))
.put("shadowbringer_soul", new AuraSettings(0.1F, 1))
.put("goldenrod", new AuraSettings(0.4F, 1))
.put("confetti", new AuraSettings(0.1F, 1))
.put("prismatic_confetti", new AuraSettings(0.1F, 1))
.put("prismarine", new AuraSettings(0.1F, 1)).build();
    }
}
